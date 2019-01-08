package com.worksap.morphling.raptor.dump.thread.util.parser.line;

import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.FINALIZER_LABEL;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.GC_LABEL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public abstract class LineParserAbstract {
    public static final String DAEMON = "daemon";
    public static final int HEX_UNIT = 16;
    private static final Pattern TITLE_WITHOUT_PRIO_PATTERN = Pattern
            .compile(".*os_prio=(\\d+)\\s+tid=(\\w+)\\s+nid=(\\w+)\\s+.*");
    private static final Pattern TITLE_PATTERN = Pattern
            .compile(".*prio=(\\d+)\\s+os_prio=(\\d+)\\s+tid=(\\w+)\\s+nid=(\\w+)\\s+.*");


    public void parseTitle(ThreadDo threadDo, String line) {
        String threadName = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
        threadDo.setName(threadName);
        if (line.substring(line.lastIndexOf("\"")).contains(DAEMON)) {
            threadDo.setDaemon(true);
        }
        threadDo.setStateEnum(getTitleState(line));
        if (threadName.contains(GC_LABEL)) {
            threadDo.setBelongsToGc(true);
        }
        if (threadName.contains(FINALIZER_LABEL)) {
            threadDo.setBelongsToFinalizer(true);
        }
        int index = 1;
        Matcher matcher = TITLE_PATTERN.matcher(line);
        if (matcher.find()) {
            threadDo.setPriority(Integer.parseInt(matcher.group(index++)));
            threadDo.setOsPriority(Integer.parseInt(matcher.group(index++)));
            threadDo.setThreadId(Long.parseLong(removeHexPrefix(matcher.group(index++)), HEX_UNIT));
            threadDo.setNativeThreadId(Long.parseLong(removeHexPrefix(matcher.group(index++)), HEX_UNIT));
        } else {
            matcher = TITLE_WITHOUT_PRIO_PATTERN.matcher(line);
            if (matcher.find()) {
                threadDo.setOsPriority(Integer.parseInt(matcher.group(index++)));
                threadDo.setThreadId(Long.parseLong(removeHexPrefix(matcher.group(index++)), HEX_UNIT));
                threadDo.setNativeThreadId(Long.parseLong(removeHexPrefix(matcher.group(index++)), HEX_UNIT));
            }
        }
    }

    protected StateEnum getTitleState(String line) {
        return getStateEnum(line);
    }

    private StateEnum getStateEnum(String line) {
        StateEnum theState = StateEnum.CREATED;
        for (StateEnum stateEnum : StateEnum.values()) {
            if (line.contains(stateEnum.toKey()) || line.contains(stateEnum.toKey().toLowerCase())) {
                theState = stateEnum;
            }
        }
        return theState;
    }

    public void parseState(ThreadDo threadDo, String line) {
        threadDo.setStateEnum(getStateEnum(line));
    }

    public void parseWaitingLock(ThreadDo threadDo, String line) {
        threadDo.getLocksWaiting().add(getHexStrFromAngleBracket(line));
    }

    public void parseLockHeld(ThreadDo threadDo, String line) {
        threadDo.getLocksHeld().add(getHexStrFromAngleBracket(line));
    }

    public void parseCallStack(ThreadDo threadDo, String line) {
        String mark = getLineType().getKey(LineTypeEnum.STACK_TRACE);
        String callStack = line.substring(line.indexOf(mark) + mark.length());
        threadDo.getCallStack().add(callStack);
    }

    private String removeHexPrefix(String hexStr) {
        return hexStr.substring(hexStr.indexOf("x") + 1);
    }

    private String getHexStrFromAngleBracket(String line) {
        String ret = line;
        try {
            ret = removeHexPrefix(
                    line.substring(line.indexOf("<") + 1, line.lastIndexOf(">")));
        } catch (Exception e) {
            log.info("The error line: {}", line);
            e.printStackTrace();
        }
        return ret;
    }

    protected abstract LineTypeKeyable getLineType();
}
