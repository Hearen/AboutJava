package com.worksap.morphling.raptor.dump.thread.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpDo;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;
import com.worksap.morphling.raptor.dump.thread.vo.BlockingVo;
import com.worksap.morphling.raptor.dump.thread.vo.CallStateSizeVo;
import com.worksap.morphling.raptor.dump.thread.vo.GroupStateVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ThreadUtils {
    public static final String CALL_STACK_SEPARATOR = "|";
    public static final String GC_LABEL = "GC";
    public static final String FINALIZER_LABEL = "Finalizer";
    public static final String LOCKING_WAITING_LABEL = "waiters";
    public static final String LOCKING_HOLDING_LABEL = "holders";
    private static final Pattern GENERIC_POOL_NAME_PATTERN_0 = Pattern.compile("^(.+)-.+$");
    private static final Pattern GENERIC_POOL_NAME_PATTERN_1 = Pattern.compile("^(.+)\\d+$");
    private static final Pattern GC_POOL_NAME_PATTERN = Pattern.compile("^(GC task thread).+$");
    private static final Pattern FINALIZER_POOL_NAME_PATTERN = Pattern.compile("^(Finalizer).*$");

    private ThreadUtils() {
    }

    public static String getPoolName(String threadName) {
        String poolName = threadName;
        Matcher matcher = GC_POOL_NAME_PATTERN.matcher(threadName);
        if (matcher.find()) {
            poolName = matcher.group(1);
        }
        matcher = FINALIZER_POOL_NAME_PATTERN.matcher(threadName);
        if (matcher.find()) {
            poolName = matcher.group(1);
        }
        if (poolName.equalsIgnoreCase(threadName)) {
            matcher = GENERIC_POOL_NAME_PATTERN_0.matcher(threadName);
            if (matcher.find()) {
                poolName = matcher.group(1);
            }
            if (poolName.equalsIgnoreCase(threadName)) {
                matcher = GENERIC_POOL_NAME_PATTERN_1.matcher(threadName);
                if (matcher.find()) {
                    poolName = matcher.group(1);
                }
            }
        }
        return poolName;
    }

    public static String getStackTraceString(List<String> callStacks) {
        return String.join(CALL_STACK_SEPARATOR, callStacks);
    }

    public static List<ThreadDo> getFinalizerGroup(ThreadDumpDo threadDumpDo) {
        return threadDumpDo.getThreadDoList().stream()
                .filter(threadDo -> threadDo.isBelongsToFinalizer())
                .collect(Collectors.toList());
    }

    public static List<ThreadDo> getGcGroup(ThreadDumpDo threadDumpDo) {
        return threadDumpDo.getThreadDoList().stream()
                .filter(threadDo -> threadDo.isBelongsToGc())
                .collect(Collectors.toList());
    }

    public static Map<String, List<BlockingVo>> getBlockingThreads(ThreadDumpDo threadDumpDo) {

        Map<String, List<ThreadDo>> lockWaiterListMap = DeadLockUtils.getLockStateWaitingListMap(
                threadDumpDo.getThreadDoList(), null);
        Map<String, List<ThreadDo>> lockHelderListMap = DeadLockUtils.getLockStateHoldingListMap(
                threadDumpDo.getThreadDoList(), null);
        Map<String, List<BlockingVo>> blockingListMap = new HashMap<>();
        lockWaiterListMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 0
                        || lockHelderListMap.getOrDefault(entry.getKey(), new ArrayList<>()).size() > 0)
                .forEach(entry -> {
                    blockingListMap.put(entry.getKey(), Arrays.asList(
                            BlockingVo.builder().type(LOCKING_WAITING_LABEL).threadIdList(entry.getValue().stream()
                                    .map(threadDo -> threadDo.getThreadId())
                                    .collect(Collectors.toList())).build(),
                            BlockingVo.builder().type(LOCKING_HOLDING_LABEL).threadIdList(lockHelderListMap
                                    .getOrDefault(entry.getKey(), new ArrayList<>()).stream()
                                    .map(threadDo -> threadDo.getThreadId()).collect(Collectors.toList())).build()
                    ));
                });
        return blockingListMap;
    }

    private static Map<String, List<ThreadDo>> getStateThreadsWithBottomCall(ThreadDumpDo threadDumpDo,
                                                                             StateEnum stateEnum) {
        List<ThreadDo> doListMap = getStateGroup(threadDumpDo).get(stateEnum);
        if (CollectionUtils.isNotEmpty(doListMap)) {
            return doListMap.stream()
                    .filter(threadDo -> getLast(threadDo.getCallStack()) != null)
                    .collect(Collectors.groupingBy(threadDo -> getLast(threadDo.getCallStack())));
        }
        return new HashMap<>();
    }

    public static Map<String, List<ThreadDo>> getCpuConsumingThreads(ThreadDumpDo threadDumpDo) {
        //FIXME: NOT IDEAL
        return getStateThreadsWithBottomCall(threadDumpDo, StateEnum.RUNNABLE);
    }

    public static Map<Boolean, List<ThreadDo>> getDaemonGroup(ThreadDumpDo threadDumpDo) {
        Map<Boolean, List<ThreadDo>> daemonGroup = threadDumpDo.getThreadDoList().stream().collect(Collectors
                .groupingBy(ThreadDo::isDaemon));
        return sortHashMapByValueSize(daemonGroup);
    }

    public static Map<String, List<ThreadDo>> getCallStackGroup(ThreadDumpDo threadDumpDo) {
        List<ThreadDo> threadDoList = threadDumpDo.getThreadDoList();
        Map<String, List<ThreadDo>> callStackGroupMap = new HashMap<>();
        threadDoList.stream()
                .forEach(threadDo -> {
                    String callStackLabel = getStackTraceString(threadDo.getCallStack());
                    if (!StringUtils.isEmpty(callStackLabel)) {
                        callStackGroupMap.putIfAbsent(callStackLabel, new ArrayList<>());
                        callStackGroupMap.get(callStackLabel).add(threadDo);
                    }
                });
        return sortHashMapByValueSize(callStackGroupMap);
    }

    public static Map<String, Map<StateEnum, Integer>> getCallWithStateSizeGroup(ThreadDumpDo threadDumpDo) {
        Map<String, Map<StateEnum, Integer>> callWithStateSizeSortedGroup = new LinkedHashMap<>();
        Map<String, Map<StateEnum, List<ThreadDo>>> callWithStateGroup = getCallStackWithStateGroup(threadDumpDo);
        Queue<CallStateSizeVo> callStateSizeVoQueue = new PriorityQueue<>(threadDumpDo.getThreadDoList().size(),
                Comparator.comparing(CallStateSizeVo::getSize, Comparator.reverseOrder()));
        for (Map.Entry<String, Map<StateEnum, List<ThreadDo>>> callEntry : callWithStateGroup.entrySet()) {
            for (Map.Entry<StateEnum, List<ThreadDo>> listEntry : callEntry.getValue().entrySet()) {
                callStateSizeVoQueue.add(CallStateSizeVo.builder()
                        .call(callEntry.getKey())
                        .state(listEntry.getKey())
                        .size(listEntry.getValue().size()).build());
            }
        }
        CallStateSizeVo callStateSizeVo = callStateSizeVoQueue.poll();
        while (callStateSizeVo != null) {
            callWithStateSizeSortedGroup.computeIfAbsent(callStateSizeVo.getCall(), (unused) -> new LinkedHashMap<>())
                    .put(callStateSizeVo.getState(), callStateSizeVo.getSize());
            callStateSizeVo = callStateSizeVoQueue.poll();
        }
        return callWithStateSizeSortedGroup;
    }

    public static Map<String, Map<StateEnum, List<ThreadDo>>> getCallStackWithStateGroup(ThreadDumpDo threadDumpDo) {
        List<ThreadDo> threadDoList = threadDumpDo.getThreadDoList();
        return threadDoList.stream()
                .filter(threadDo -> CollectionUtils.isNotEmpty(threadDo.getCallStack()))
                .collect(Collectors.groupingBy(threadDo -> getStackTraceString(threadDo.getCallStack()),
                        Collectors.groupingBy(threadDo -> threadDo.getStateEnum())));
    }

    public static List<GroupStateVo> getPoolGroupWithState(ThreadDumpDo threadDumpDo) {
        List<ThreadDo> threadDoList = threadDumpDo.getThreadDoList();
        Map<String, List<ThreadDo>> poolGroupMap = new HashMap<>();
        threadDoList.stream()
                .forEach(threadDo -> {
                    String threadName = threadDo.getName();
                    String poolName = ThreadUtils.getPoolName(threadName);
                    poolGroupMap.putIfAbsent(poolName, new ArrayList<>());
                    poolGroupMap.get(poolName).add(threadDo);
                });
        Iterator<String> iterator = poolGroupMap.keySet().iterator();
        while (iterator.hasNext()) {
            if (poolGroupMap.get(iterator.next()).size() < 2) {
                iterator.remove();
            }
        }
        List<GroupStateVo> groupStateVos = new ArrayList<>();
        sortHashMapByValueSize(poolGroupMap).entrySet().stream()
                .forEach(entry -> groupStateVos.add(getVo(entry)));
        return groupStateVos;
    }

    private static GroupStateVo getVo(Map.Entry<String, List<ThreadDo>> entry) {
        int runnableCount = 0;
        int timedWaitingCount = 0;
        int waitingCount = 0;
        int blockingCount = 0;
        for (ThreadDo threadDo : entry.getValue()) {
            switch (threadDo.getStateEnum()) {
                case RUNNABLE:
                    runnableCount++;
                    break;
                case TIMED_WAITING:
                    timedWaitingCount++;
                    break;
                case WAITING:
                    waitingCount++;
                    break;
                case BLOCKED:
                    blockingCount++;
                    break;
                default:
                    break;
            }
        }
        return GroupStateVo.builder().name(entry.getKey())
                .totalCount(entry.getValue().size())
                .runnableCount(runnableCount).timedWaitingCount(timedWaitingCount)
                .waitingCount(waitingCount).blockingCount(blockingCount).build();
    }

    public static Map<String, List<ThreadDo>> getMostUsedMethodGroup(ThreadDumpDo threadDumpDo) {
        List<ThreadDo> threadDoList = threadDumpDo.getThreadDoList();
        Map<String, List<ThreadDo>> mostUsedMethodMap = new HashMap<>();
        threadDoList.stream()
                .forEach(threadDo -> {
                    threadDo.getCallStack().stream()
                            .forEach(methodCall -> {
                                mostUsedMethodMap.putIfAbsent(methodCall, new ArrayList<>());
                                mostUsedMethodMap.get(methodCall).add(threadDo);
                            });
                });
        return sortHashMapByValueSize(mostUsedMethodMap);
    }

    public static Map<StateEnum, List<ThreadDo>> getStateGroup(ThreadDumpDo threadDumpDo) {
        List<ThreadDo> threadDoList = threadDumpDo.getThreadDoList();
        Map<StateEnum, List<ThreadDo>> stateEnumListMap = new HashMap<>();
        threadDoList.stream()
                .forEach(threadDo -> {
                    StateEnum theState = threadDo.getStateEnum();
                    stateEnumListMap.putIfAbsent(theState, new ArrayList<>());
                    stateEnumListMap.get(theState).add(threadDo);
                });
        return sortHashMapByValueSize(stateEnumListMap);
    }

    public static Map<String, Integer> convertListMapToSizeSortedMap(Map<String, List<ThreadDo>> listMap) {
        return sortHashMapByValue(convertListToSize(listMap));
    }

    public static <T, U> Map<T, List<U>> sortHashMapByValueSize(Map<T, List<U>> mapWithList) {
        return mapWithList.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(List::size, Comparator.reverseOrder())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldKey, newKey) -> oldKey,
                        LinkedHashMap::new));
    }

    public static <T> Map<T, Integer> sortHashMapByValue(Map<T, Integer> mapWithList) {
        if (mapWithList == null) {
            return null;
        }
        return mapWithList.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldKey, newKey) -> oldKey,
                        LinkedHashMap::new));
    }

    public static <T, U> Map<T, Integer> convertListToSize(Map<T, List<U>> listMap) {
        if (listMap == null) {
            return null;
        }
        return listMap.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue().size()));
    }

    public static <T> T getLast(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static <T> String convertListToString(List<T> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static <T> List<T> getListFromString(String s) {
        Gson gson = new Gson();
        return gson.fromJson(s, new TypeToken<List<T>>() {
        }.getType());
    }

    public static <T, U> Map<T, U> getMapFromString(String s) {
        Gson gson = new Gson();
        Map<T, U> map = new HashMap<>();
        try {
            map = gson.fromJson(s, HashMap.class);
        } catch (Exception ignored) {
            // old versions might result in parsing exception;
        }
        return map;
    }

    public static String toJsonString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}

