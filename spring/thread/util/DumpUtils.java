package com.worksap.morphling.raptor.dump.thread.util;

import static com.worksap.morphling.raptor.dump.thread.util.DeadLockUtils.getDeadLockLoops;
import static com.worksap.morphling.raptor.dump.thread.util.DeadLockUtils.getLockStateHoldingListMap;
import static com.worksap.morphling.raptor.dump.thread.util.DeadLockUtils.getLockStateWaitingListMap;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.CALL_STACK_SEPARATOR;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.convertListMapToSizeSortedMap;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getBlockingThreads;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getCallStackGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getCallWithStateSizeGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getCpuConsumingThreads;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getDaemonGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getFinalizerGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getGcGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getMapFromString;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getMostUsedMethodGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getPoolGroupWithState;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getStackTraceString;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getStateGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.sortHashMapByValueSize;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpDo;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;
import com.worksap.morphling.raptor.dump.thread.vo.ThreadDumpVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DumpUtils {
    public static final String SOURCE_NAME = "source_name";
    public static final String SOURCE_VERSION = "source_version";
    public static final String CUR_SOURCE_VERSION = "1.2";

    private DumpUtils() {
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "PATH_TRAVERSAL_IN",
            justification = "Only Parsing the File, No Execution Might Occur")
    public static File convertMultiPartToFile(MultipartFile multipartFile) {
        File newFile = new File(System.getProperty("java.io.tmpdir") + "/"
                + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Converting uploaded MultiPartFile to File Failed");
        }
        return newFile;
    }


    public static String getCheckSum(File file) {
        try {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                return org.apache.commons.codec.digest.DigestUtils.sha256Hex(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Get Check Sum failed with file {}", file.getName());
        }
        return null;
    }

    public static List<String> convertFileToStringList(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            lines.addAll(br.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            log.error("Reading file content failed!");
        }
        return lines;
    }

    public static ThreadDumpVo prepareDumpVo(ThreadDumpDo threadDumpDo) {
        ThreadDumpVo threadDumpVo = ThreadDumpVo.builder().fileName(threadDumpDo.getFileName()).build();
        threadDumpVo.setStoredInS3(isStoredInS3(threadDumpDo.getDetails()));
        threadDumpVo.setDumpedTime(threadDumpDo.getDumpedTime().getTime());
        threadDumpVo.setParsedTime(threadDumpDo.getParsedTime().getTime());
        Map<StateEnum, List<ThreadDo>> stateEnumListMap = getStateGroup(threadDumpDo);
        threadDumpVo.setTotalCount(threadDumpDo.getThreadDoList().size());
        threadDumpVo.setBlockedCount(stateEnumListMap.getOrDefault(StateEnum.BLOCKED, new ArrayList<>()).size());
        threadDumpVo.setRunnableCount(stateEnumListMap.getOrDefault(StateEnum.RUNNABLE, new ArrayList<>()).size());
        int waitingCount = stateEnumListMap.getOrDefault(StateEnum.WAITING, new ArrayList<>()).size();
        int timedWaitingCount = stateEnumListMap.getOrDefault(StateEnum.TIMED_WAITING, new ArrayList<>()).size();
        threadDumpVo.setWaitingCount(waitingCount);
        threadDumpVo.setTimedWaitingCount(timedWaitingCount);
        threadDumpVo.setDaemonCount(getDaemonGroup(threadDumpDo).getOrDefault(true, new ArrayList<>()).size());
        threadDumpVo.setGroupWithState(getPoolGroupWithState(threadDumpDo));
        threadDumpVo.setStackTraceGroup(convertListMapToSizeSortedMap(getCallStackGroup(threadDumpDo)));
        threadDumpVo.setStackTraceWithStateSizeGroup(getCallWithStateSizeGroup(threadDumpDo));
        threadDumpVo.setMostUsedMethodGroup(convertListMapToSizeSortedMap(getMostUsedMethodGroup(threadDumpDo)));
        threadDumpVo.setCpuConsumingGroup(convertListMapToSizeSortedMap(getCpuConsumingThreads(threadDumpDo)));
        threadDumpVo.setBlockingGroup(getBlockingThreads(threadDumpDo));
        updateDeadLockInfo(threadDumpDo, threadDumpVo);
        threadDumpVo.setGcThreadCount(getGcGroup(threadDumpDo).size());
        threadDumpVo.setFinalizerThreadCount(getFinalizerGroup(threadDumpDo).size());
        threadDumpVo.setCallStackFlatTree(getCallStackLevelMethodCountingGroup(threadDumpDo, false));
        threadDumpVo.setCallStackTree(buildUpCallStackTree(threadDumpDo, false));
        threadDumpVo.setReversedStackTree(buildUpCallStackTree(threadDumpDo, true));
        log.debug("The Vo for Home Page: \n{}", threadDumpVo);
        return threadDumpVo;
    }

    public static boolean isStoredInS3(String details) {
        Map<String, String> detailMap = getMapFromString(details);
        if (detailMap.get(SOURCE_NAME) != null) {
            return true;
        }
        return false;
    }

    private static void updateDeadLockInfo(ThreadDumpDo threadDumpDo, ThreadDumpVo threadDumpVo) {
        List<List<String>> deadLockLoops = getDeadLockLoops(threadDumpDo);
        threadDumpVo.setDeadLockSimpleList(deadLockLoops);
        threadDumpVo.setHasDeadlock(deadLockLoops.size() > 0);
        threadDumpVo.setDeadLockComplexList(getDeadLockComplexList(deadLockLoops, threadDumpDo.getThreadDoList()));
    }

    private static List<List<DeadLockCounter>> getDeadLockComplexList(List<List<String>> deadLockLoops,
                                                                      List<ThreadDo> threadDoList) {
        Map<String, List<ThreadDo>> holderListMap = getLockStateHoldingListMap(threadDoList, null);
        Map<String, List<ThreadDo>> waiterListMap = getLockStateWaitingListMap(threadDoList, null);
        List<List<DeadLockCounter>> deadLockComplexList = new ArrayList<>();
        deadLockLoops.stream().forEach(loop -> {
            List<DeadLockCounter> lockCounters = new ArrayList<>();
            loop.stream().forEach(lock -> {
                lockCounters.add(DeadLockCounter.builder()
                        .lockAddress(lock)
                        .holderCount(holderListMap.get(lock).size())
                        .waiterCount(waiterListMap.get(lock).size()).build());
            });
            lockCounters.sort(comparing(DeadLockCounter::getHolderCount).reversed()
                    .thenComparing(comparing(DeadLockCounter::getWaiterCount).reversed()));
            deadLockComplexList.add(lockCounters);
        });
        return deadLockComplexList;
    }

    private static TreeNode buildUpCallStackTree(ThreadDumpDo threadDumpDo, boolean isReversed) {
        List<ThreadDo> threadDoList = threadDumpDo.getThreadDoList().stream()
                .filter(threadDo -> threadDo.getCallStack().size() > 0).collect(toList());
        TreeNode root = TreeNode.builder().callTrace("ROOT").build();
        if (isReversed) {
            threadDoList.forEach(threadDo -> Collections.reverse(threadDo.getCallStack()));
        }
        root.setCount(threadDoList.size());
        root.setChildren(getIthLevelTreeNode(threadDoList, 0));
        if (isReversed) {
            threadDoList.forEach(threadDo -> Collections.reverse(threadDo.getCallStack()));
        }
        return root;
    }

    private static List<TreeNode> getIthLevelTreeNode(List<ThreadDo> threadDos, int level) {
        List<TreeNode> children = new LinkedList<>();
        Map<String, List<ThreadDo>> listMap = getLevelGroup(threadDos, level);
        for (Map.Entry<String, List<ThreadDo>> entry : listMap.entrySet()) {
            TreeNode treeNode = TreeNode.builder().callTrace(entry.getKey()).count(entry.getValue().size()).build();
            treeNode.setChildren(getIthLevelTreeNode(entry.getValue(), level + 1));
            children.add(treeNode);
        }
        return children.stream().sorted(comparing(TreeNode::getCount).reversed()).collect(toList());
    }

    private static List<Map<String, Integer>> getCallStackLevelMethodCountingGroup(ThreadDumpDo threadDumpDo,
                                                                                   boolean isReversed) {
        List<Map<String, Integer>> countingGroup = new ArrayList<>();
        if (isReversed) {
            threadDumpDo.getThreadDoList().forEach(threadDo -> threadDo.getCallStack().sort(reverseOrder()));
        }
        for (int index = 0; true; ++index) {
            if (!levelMethodCountingHelper(countingGroup, index++, threadDumpDo.getThreadDoList())) {
                break;
            }
        }
        if (isReversed) {
            threadDumpDo.getThreadDoList().forEach(threadDo -> threadDo.getCallStack().sort(reverseOrder()));
        }
        return countingGroup;
    }

    private static boolean levelMethodCountingHelper(List<Map<String, Integer>> countingGroup, int index,
                                                     List<ThreadDo> threadDos) {
        Map<String, Integer> sizeMap = convertListMapToSizeSortedMap(getCompleteLevelGroup(threadDos, index));
        if (CollectionUtils.isNotEmpty(sizeMap.entrySet()) && sizeMap.entrySet().size() > 0) {
            countingGroup.add(sizeMap);
            return true;
        }
        return false;
    }

    private static Map<String, List<ThreadDo>> getCompleteLevelGroup(List<ThreadDo> threadDos, int index) {
        return threadDos.stream()
                .filter(threadDo -> threadDo.getCallStack().size() > index)
                .collect(groupingBy(threadDo ->
                        StringUtils.join(threadDo.getCallStack().subList(0, index + 1), CALL_STACK_SEPARATOR)));

    }

    private static Map<String, List<ThreadDo>> getLevelGroup(List<ThreadDo> threadDos, int index) {
        return threadDos.stream()
                .filter(threadDo -> threadDo.getCallStack().size() > index)
                .collect(groupingBy(threadDo -> threadDo.getCallStack().get(index)));
    }

    public static Map<StateEnum, Map<String, List<Long>>> getSharedStateStackTraceListMap(
            List<ThreadDumpDo> dumpDoList) {
        if (CollectionUtils.isEmpty(dumpDoList)) {
            return Collections.emptyMap();
        }
        Map<StateEnum, List<List<ThreadDo>>> stateDumpListMap = dumpDoList.stream()
                .flatMap(dumpDo -> getStateGroup(dumpDo).entrySet().stream())
                .collect(groupingBy(entry -> entry.getKey(), mapping(entry -> entry.getValue(), toList())));
        return stateDumpListMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> getSharedListStackMap(e.getValue())));
    }


    private static Map<String, List<Long>> getSharedListStackMap(List<List<ThreadDo>> dumpsDoList) {
        if (dumpsDoList.stream().anyMatch(doList -> doList == null || doList.size() == 0)) {
            return Collections.emptyMap();
        }
        List<Map<Long, ThreadDo>> doMapList = dumpsDoList.stream()
                .map(doList -> doList.stream()
                        .collect(Collectors.toMap(ThreadDo::getThreadId, threadDo -> threadDo)))
                .collect(toList());
        List<ThreadDo> sharedDoList = new ArrayList<>();
        List<ThreadDo> theFirstDoList = dumpsDoList.get(0);
        for (ThreadDo threadDo : theFirstDoList) {
            if (doMapList.stream().allMatch(doMap -> doMap.get(threadDo.getThreadId()) != null)) {
                sharedDoList.add(threadDo);
            }
        }
        return sortHashMapByValueSize(sharedDoList.stream().collect(Collectors.groupingBy(
                threadDo -> getStackTraceString(threadDo.getCallStack()), mapping(ThreadDo::getThreadId, toList()))));
    }
}
