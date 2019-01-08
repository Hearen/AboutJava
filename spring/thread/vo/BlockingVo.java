package com.worksap.morphling.raptor.dump.thread.vo;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockingVo {
    String type;
    List<Long> threadIdList;
}
