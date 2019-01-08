package com.worksap.morphling.raptor.dump.thread.util.parser.thread;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpDo;
import com.worksap.morphling.raptor.dump.thread.util.parser.line.LineParserAbstract;
import com.worksap.morphling.raptor.dump.thread.util.parser.line.LineParserAppDynamic;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeAppDynamic;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ThreadParserAppDynamic extends ThreadParserAbstract {
    @Autowired
    LineParserAppDynamic lineParserAppDynamic;
    @Autowired
    private LineTypeAppDynamic lineTypeAppDynamic;

    @Override
    protected LineParserAbstract getLineParser() {
        return lineParserAppDynamic;
    }

    @Override
    protected LineTypeKeyable getLineTypeMapper() {
        return lineTypeAppDynamic;
    }

    @Override
    protected void parseHeader(ThreadDumpDo threadDumpDo, List<String> lines) {
        threadDumpDo.setDumpedTime(new Date());
        log.info("No Header parse for AppDynamics");
    }

    @Override
    protected List<String> removeInvalidFooter(List<String> lines) {
        return lines;
    }
}
