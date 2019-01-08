package com.worksap.morphling.raptor.dump.thread.vo;

import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CallStateSizeVo {
    String call;
    StateEnum state;
    Integer size;
}
