package com.worksap.morphling.raptor.dump.thread.util.parser.line;

import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.FINALIZER_LABEL;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.GC_LABEL;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeAppDynamic;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LineParserAppDynamic extends LineParserAbstract {
    private static final Pattern TITLE_PATTERN = Pattern.compile("\"(.+)\" Id=(\\d+) in (\\w).*");
    private static final String INNER_LOCKED = "    - locked ";
    private static final String OUTER_LOCKED = "    - ";
    @Autowired
    LineTypeAppDynamic lineTypeAppDynamic;

    @Override
    protected LineTypeKeyable getLineType() {
        return lineTypeAppDynamic;
    }

    @Override
    public void parseTitle(ThreadDo threadDo, String line) {
        Matcher matcher = TITLE_PATTERN.matcher(line);
        if (matcher.find()) {
            threadDo.setName(matcher.group(1));
            threadDo.setThreadId(Integer.parseInt(matcher.group(2)));
        }
        threadDo.setStateEnum(super.getTitleState(line));
        String threadName = threadDo.getName();
        log.debug("Thread Name: {} in line: {}", threadName, line);
        if (threadName.contains(GC_LABEL)) {
            threadDo.setBelongsToGc(true);
        }
        if (threadName.contains(FINALIZER_LABEL)) {
            threadDo.setBelongsToFinalizer(true);
        }
        threadDo.getLocksWaiting().addAll(parseLockWaiting(line));
    }

    @Override
    public void parseLockHeld(ThreadDo threadDo, String line) {
        String lockHeldKey = INNER_LOCKED;
        if (line.contains(OUTER_LOCKED)) {
            lockHeldKey = OUTER_LOCKED;
        }
        threadDo.getLocksHeld().add(line.substring(line.indexOf(lockHeldKey) + lockHeldKey.length()));
    }

    private List<String> parseLockWaiting(String line) {
        String lockWaitingKey = getLineType().getKey(LineTypeEnum.WAITING_LOCK);
        String lock = line.substring(line.indexOf(lockWaitingKey) + lockWaitingKey.length());
        return Arrays.asList(lock);
    }
}
