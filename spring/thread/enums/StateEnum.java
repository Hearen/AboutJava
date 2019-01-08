package com.worksap.morphling.raptor.dump.thread.enums;

import java.util.Arrays;

public enum StateEnum {
    CREATED("NEW"),
    RUNNABLE("RUNNABLE"),
    WAITING("WAITING"),
    TIMED_WAITING("TIMED_WAITING"),
    BLOCKED("BLOCKED");

    private String keyword;

    StateEnum(String theKey) {
        this.keyword = theKey;
    }

    public static StateEnum getEnum(String value) {
        return Arrays.stream(StateEnum.values())
                .filter(stateEnum -> stateEnum.toKey().equalsIgnoreCase(value))
                .findFirst()
                .orElse(CREATED);
    }

    public String toKey() {
        return keyword;
    }
}
