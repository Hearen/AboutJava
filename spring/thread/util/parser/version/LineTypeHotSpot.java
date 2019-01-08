package com.worksap.morphling.raptor.dump.thread.util.parser.version;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum;

@Component
public class LineTypeHotSpot implements LineTypeKeyable {
    Map<LineTypeEnum, String> enumStringHashMap = new HashMap<>();

    @PostConstruct
    protected void initMap() {
        enumStringHashMap.put(LineTypeEnum.UNKNOWN, "unknown");
        enumStringHashMap.put(LineTypeEnum.THREAD_BLOCK_END, "JNI global references:");
        enumStringHashMap.put(LineTypeEnum.TITLE, " tid=");
        enumStringHashMap.put(LineTypeEnum.STATE, "Thread.State");
        enumStringHashMap.put(LineTypeEnum.STACK_TRACE, "at ");
        enumStringHashMap.put(LineTypeEnum.WAITING_LOCK, "waiting to lock");
        enumStringHashMap.put(LineTypeEnum.LOCKING, "locked");
    }

    @Override
    public String getKey(LineTypeEnum lineTypeEnum) {
        return enumStringHashMap.get(lineTypeEnum);
    }
}
