package com.worksap.morphling.raptor.dump.thread.util.parser.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.util.parser.line.LineParserAbstract;
import com.worksap.morphling.raptor.dump.thread.util.parser.line.LineParserOpenJdk;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeOpenJdk;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ThreadParserOpenJdk extends ThreadParserAbstract {
    @Autowired
    LineParserOpenJdk lineParserOpenJdk;
    @Autowired
    LineTypeOpenJdk lineTypeOpenJdk;

    @Override
    protected LineParserAbstract getLineParser() {
        return lineParserOpenJdk;
    }

    @Override
    protected LineTypeKeyable getLineTypeMapper() {
        return lineTypeOpenJdk;
    }

}
