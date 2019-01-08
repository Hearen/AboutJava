package com.worksap.morphling.raptor.dump.thread.util;

import com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;

public final class LineTypeChecker {

    private LineTypeChecker() {
    }

    public static LineTypeEnum getLineType(String line, LineTypeKeyable typeKeyable) {
        LineTypeEnum typeEnum = LineTypeEnum.UNKNOWN;
        for (LineTypeEnum lineTypeEnum : LineTypeEnum.values()) {
            if (typeKeyable.getKey(lineTypeEnum) != null && line.contains(typeKeyable.getKey(lineTypeEnum))) {
                return lineTypeEnum;
            }
        }
        if (typeEnum == LineTypeEnum.WAITING_LOCK || typeEnum == LineTypeEnum.LOCKING) {
            boolean isValidLockLine = line.contains("<") && line.contains(">");
            if (!isValidLockLine) {
                typeEnum = LineTypeEnum.UNKNOWN;
            }
        }
        return typeEnum;
    }
}
