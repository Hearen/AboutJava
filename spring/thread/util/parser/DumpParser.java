package com.worksap.morphling.raptor.dump.thread.util.parser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpDo;
import com.worksap.morphling.raptor.dump.thread.enums.DumpVersionEnum;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DumpParser {
    @Autowired
    private ParserFactory parserFactory;

    public ThreadDumpDo parse(List<String> lines, boolean isParallel) {
        return parserFactory.getThreadParser(getVersion(lines)).parse(lines, isParallel);
    }

    private DumpVersionEnum getVersion(List<String> lines) {
        DumpVersionEnum theDumpVersion = DumpVersionEnum.HOTSPOT;
        for (DumpVersionEnum versionEnum : DumpVersionEnum.values()) {
            if (lines.get(1).contains(versionEnum.toKey())) {
                return versionEnum;
            }
        }
        return theDumpVersion;
    }
}
