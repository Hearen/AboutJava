package com.worksap.morphling.raptor.dump.thread.util.parser.thread;

import static com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum.TITLE;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getLast;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.toJsonString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpDo;
import com.worksap.morphling.raptor.dump.thread.enums.DumpVersionEnum;
import com.worksap.morphling.raptor.dump.thread.enums.LineTypeEnum;
import com.worksap.morphling.raptor.dump.thread.util.LineTypeChecker;
import com.worksap.morphling.raptor.dump.thread.util.parser.line.LineParserAbstract;
import com.worksap.morphling.raptor.dump.thread.util.parser.version.LineTypeKeyable;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public abstract class ThreadParserAbstract {
    public static final Pattern FIRST_LINE_PATTERN = Pattern.compile("^\\d+:$");

    public ThreadDumpDo parse(List<String> lines, boolean isParallel) {
        ThreadDumpDo threadDumpDo = ThreadDumpDo.builder()
                .parsedTime(new Date()).build();
        parseHeader(threadDumpDo, lines);
        lines = removeInvalidFooter(lines);
        if (isParallel) {
            threadDumpDo.setThreadDoList(parallelLinesParse(lines));
        } else {
            threadDumpDo.setThreadDoList(parseLines(lines));
        }
        List<ThreadDo> threadDoList = threadDumpDo.getThreadDoList();
        cleanInvalid(threadDoList);
        log.info("Parsed Thread Entities: \n{}", toJsonString(threadDoList));
        return threadDumpDo;
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "HB_PARALLEL_STREAM_USAGE",
            justification = "Testing Code Used for Performance Boost")
    private List<ThreadDo> parallelLinesParse(List<String> lines) {
        List<ThreadDo> completeList = new ArrayList<>();
        List<List<ThreadDo>> blockList = splitUpLinesByCoreCount(lines).parallelStream()
                .map(linesBlock -> parseLines(linesBlock)).collect(Collectors.toList());
        blockList.forEach(list -> completeList.addAll(list));
        return completeList;
    }

    private List<ThreadDo> parseLines(List<String> lines) {
        List<ThreadDo> threadDoList = new ArrayList<>();
        lines.stream()
                .forEach(line -> parseNewLine(threadDoList, line));
        threadDoList.stream().forEach(threadDo -> threadDo.setCallStack(getCallStack(threadDo)));
        return threadDoList;
    }

    private List<String> getCallStack(ThreadDo threadDo) {
        List<String> callStacks = new ArrayList<>();
        threadDo.getDetails().stream().forEach(line -> {
            if (LineTypeChecker.getLineType(line, getLineTypeMapper()) == LineTypeEnum.STACK_TRACE) {
                String mark = getLineTypeMapper().getKey(LineTypeEnum.STACK_TRACE);
                callStacks.add(line.substring(line.indexOf(mark) + mark.length()));
            }
        });
        return callStacks;
    }

    private List<List<String>> splitUpLinesByCoreCount(List<String> lines) {
        int coreCount = Runtime.getRuntime().availableProcessors();
        return splitUpLinesByCount(lines, coreCount);
    }

    private List<List<String>> splitUpLinesByCount(List<String> lines, int blockCount) {
        int blockSize = lines.size() / blockCount;
        List<List<String>> blockList = new ArrayList<>();
        int begin = 0;
        int end = blockSize;
        for (int i = 0; i < blockCount; ++i) {
            while (end < lines.size() && LineTypeChecker.getLineType(lines.get(end), getLineTypeMapper()) != TITLE) {
                end++;
            }
            blockList.add(lines.subList(begin, end));
            begin = end;
            end = blockSize + end;
            if (end > lines.size()) {
                break;
            }
        }
        return blockList;
    }

    private void cleanInvalid(List<ThreadDo> threadDoList) {
        Iterator<ThreadDo> iterator = threadDoList.iterator();
        while (iterator.hasNext()) {
            if (isThreadDoInvalid(iterator.next())) {
                iterator.remove();
            }
        }
    }

    private boolean isThreadDoInvalid(ThreadDo threadDo) {
        if (threadDo == null) {
            return true;
        }
        if (StringUtils.isEmpty(threadDo.getName()) || StringUtils.isEmpty(threadDo.getName().trim())) {
            return true;
        }
        if (threadDo.getThreadId() == 0) {
            return true;
        }
        return false;
    }

    private void parseNewLine(List<ThreadDo> threadDoList, String newLine) {
        ThreadDo theThreadDo = ThreadDo.builder().build();
        LineTypeEnum lineTypeEnum = LineTypeChecker.getLineType(newLine, getLineTypeMapper());
        if (lineTypeEnum != LineTypeEnum.UNKNOWN) {
            if (lineTypeEnum == TITLE) {
                theThreadDo = ThreadDo.builder()
                        .locksHeld(new ArrayList<>())
                        .locksWaiting(new ArrayList<>())
                        .callStack(new ArrayList<>())
                        .details(new ArrayList<>())
                        .build();
                threadDoList.add(theThreadDo);
            }
            getLast(threadDoList).getDetails().add(newLine);
        }
        switch (lineTypeEnum) {
            case TITLE:
                getLineParser().parseTitle(theThreadDo, newLine);
                break;
            case STATE:
                theThreadDo = getLast(threadDoList);
                getLineParser().parseState(theThreadDo, newLine);
                break;
            case WAITING_LOCK:
                theThreadDo = getLast(threadDoList);
                getLineParser().parseWaitingLock(theThreadDo, newLine);
                break;
            case LOCKING:
                theThreadDo = getLast(threadDoList);
                getLineParser().parseLockHeld(theThreadDo, newLine);
                break;
            case STACK_TRACE:
                theThreadDo = getLast(threadDoList);
                getLineParser().parseCallStack(theThreadDo, newLine);
                break;
            default:
                break;

        }
    }

    protected void parseHeader(ThreadDumpDo threadDumpDo, List<String> lines) {
        if (FIRST_LINE_PATTERN.matcher(lines.get(0)).matches()) {
            lines.remove(0);
        }
        threadDumpDo.setDumpedTime(new Date());
        //ToDo: this line might be lost - check it - it's PID - Hearen;
        try {
            Date dumpedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lines.get(0));
            lines.remove(0);
            threadDumpDo.setDumpedTime(dumpedTime);
        } catch (ParseException e) {
            e.printStackTrace();
            log.warn("Thread Parser parsing header time failed!");
        }
        threadDumpDo.setDetails(lines.get(0));
        DumpVersionEnum theDumpVersion = DumpVersionEnum.HOTSPOT;
        for (DumpVersionEnum versionEnum : DumpVersionEnum.values()) {
            if (threadDumpDo.getDetails().contains(versionEnum.toKey())) {
                theDumpVersion = versionEnum;
            }
        }
        threadDumpDo.setVersion(theDumpVersion);
        lines.remove(0);
    }

    protected List<String> removeInvalidFooter(List<String> lines) {
        int i = lines.size() - 1;
        while (i > -1 && LineTypeChecker.getLineType(lines.get(i), getLineTypeMapper())
                != LineTypeEnum.THREAD_BLOCK_END) {
            i--;
        }
        return lines.subList(0, i);
    }

    protected abstract LineParserAbstract getLineParser();

    protected abstract LineTypeKeyable getLineTypeMapper();
}
