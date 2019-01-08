package com.worksap.morphling.raptor.dump.thread.util.parser.version;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum;

@Component
public class LineTypeAppDynamic implements LineTypeKeyable {

    Map<LineTypeEnum, String> enumStringHashMap = new HashMap<>();

    @PostConstruct
    public void initMap() {
        enumStringHashMap.put(LineTypeEnum.UNKNOWN, "unknown");
        enumStringHashMap.put(LineTypeEnum.TITLE, "\" Id=");
        enumStringHashMap.put(LineTypeEnum.STACK_TRACE, "at ");
        enumStringHashMap.put(LineTypeEnum.WAITING_LOCK, "WAITING on lock=");
        enumStringHashMap.put(LineTypeEnum.LOCKING, "    - ");
    }

    public String getKey(LineTypeEnum lineTypeEnum) {
        return enumStringHashMap.get(lineTypeEnum);
    }
}
