package com.worksap.morphling.raptor.dump.thread.util.parser.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.util.parser.line.LineParserAbstract;
import com.worksap.morphling.raptor.dump.thread.util.parser.line.LineParserHopSpot;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeHotSpot;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ThreadParserHotSpot extends ThreadParserAbstract {
    @Autowired
    LineParserHopSpot lineParserHopSpot;
    @Autowired
    LineTypeHotSpot lineTypeHotSpot;

    @Override
    protected LineParserAbstract getLineParser() {
        return lineParserHopSpot;
    }

    @Override
    protected LineTypeKeyable getLineTypeMapper() {
        return lineTypeHotSpot;
    }
}
