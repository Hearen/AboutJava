package com.worksap.morphling.raptor.dump.thread.vo;

import java.util.List;
import java.util.Map;

import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DumpComparisonVo {
    List<Long> dumpIds;
    List<String> dumpFileNames;
    List<Map<StateEnum, Integer>> stateMapList;
    Map<StateEnum, Map<String, List<Long>>> sharedStateStackTraceMapList;
}
