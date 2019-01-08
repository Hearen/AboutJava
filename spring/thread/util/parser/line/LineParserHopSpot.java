package com.worksap.morphling.raptor.dump.thread.util.parser.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeHotSpot;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;

@Component
public class LineParserHopSpot extends LineParserAbstract {
    @Autowired
    LineTypeHotSpot lineTypeHotSpot;

    @Override
    protected LineTypeKeyable getLineType() {
        return lineTypeHotSpot;
    }
}
