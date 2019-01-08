package com.worksap.morphling.raptor.dump.thread.util.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.enums.DumpVersionEnum;
import com.worksap.morphling.raptor.dump.thread.util.parser.thread.ThreadParserAbstract;
import com.worksap.morphling.raptor.dump.thread.util.parser.thread.ThreadParserAppDynamic;
import com.worksap.morphling.raptor.dump.thread.util.parser.thread.ThreadParserHotSpot;
import com.worksap.morphling.raptor.dump.thread.util.parser.thread.ThreadParserOpenJdk;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ParserFactory {
    @Autowired
    ThreadParserAppDynamic threadParserAppDynamic;

    @Autowired
    ThreadParserHotSpot threadParserHotSpot;

    @Autowired
    ThreadParserOpenJdk threadParserOpenJdk;

    public ThreadParserAbstract getThreadParser(DumpVersionEnum theVersion) {
        switch (theVersion) {
            case HOTSPOT:
                return threadParserHotSpot;
            case OPEN_JDK:
                return threadParserOpenJdk;
            case APP_DYNAMIC:
                return threadParserAppDynamic;
            default:
                return threadParserHotSpot;
        }
    }
}
