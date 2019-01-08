package com.worksap.morphling.raptor.dump.thread.util.parser.version;

import com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum;

public interface LineTypeKeyable {
    String getKey(LineTypeEnum lineTypeEnum);
}
