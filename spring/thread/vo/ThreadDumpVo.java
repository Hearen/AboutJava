package com.worksap.morphling.raptor.dump.thread.vo;

import java.util.List;
import java.util.Map;

import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;
import com.worksap.morphling.raptor.dump.thread.util.DeadLockCounter;
import com.worksap.morphling.raptor.dump.thread.util.TreeNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDumpVo {
    boolean isStoredInS3;
    String fileName;
    Long dumpedTime;
    Long parsedTime;
    int totalCount;
    int blockedCount;
    int runnableCount;
    int waitingCount;
    int timedWaitingCount;
    int daemonCount;
    List<GroupStateVo> groupWithState;
    Map<String, Integer> stackTraceGroup;
    Map<String, Map<StateEnum, Integer>> stackTraceWithStateSizeGroup;
    Map<String, Integer> mostUsedMethodGroup;
    Map<String, Integer> cpuConsumingGroup;
    Map<String, List<BlockingVo>> blockingGroup;
    List<Map<String, Integer>> callStackFlatTree;
    TreeNode callStackTree;
    TreeNode reversedStackTree;
    boolean hasDeadlock;
    List<List<String>> deadLockSimpleList;
    List<List<DeadLockCounter>> deadLockComplexList;
    int gcThreadCount;
    int finalizerThreadCount;
}
