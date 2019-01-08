package com.worksap.morphling.raptor.dump.thread.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupStateVo {
    String name;
    int totalCount;
    int runnableCount;
    int timedWaitingCount;
    int waitingCount;
    int blockingCount;
}
