package com.worksap.morphling.raptor.dump.thread.util.parser.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeOpenJdk;

@Component
public class LineParserOpenJdk extends LineParserAbstract {
    @Autowired
    private LineTypeOpenJdk lineTypeOpenJdk;

    @Override
    protected LineTypeKeyable getLineType() {
        return lineTypeOpenJdk;
    }
}
