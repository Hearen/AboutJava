package com.worksap.morphling.raptor.dump.thread.enums;

public enum DumpVersionEnum {
    HOTSPOT("Java HotSpot(TM)"),
    OPEN_JDK("Full thread dump OpenJDK"),
    APP_DYNAMIC("Java stack traces");


    private String keyword;

    DumpVersionEnum(String key) {
        this.keyword = key;
    }

    public String toKey() {
        return keyword;
    }
}
