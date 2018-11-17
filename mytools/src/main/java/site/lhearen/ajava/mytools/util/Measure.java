package site.lhearen.ajava.mytools.util;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Measure {
    public static LongSummaryStatistics getRunningTime(Runnable runnable, int timesCount, String message) {
        List<Long> timeCosts = new ArrayList<>();
        Long start;
        int sum = 0;
        for (int i = 0; i < timesCount; ++i) {
            start = System.nanoTime();
            runnable.run();
            timeCosts.add((System.nanoTime() - start) / 1_000_000);
        }
        LongSummaryStatistics timeStatistics = timeCosts.stream().collect(Collectors.summarizingLong(Long::longValue));
//        timeCosts.forEach(timeCost -> out.println(String.format("TimeCost: %s", timeCost)));
        out.println(String.format("%s - sum: %d - time cost summary (ms): %s", message, sum, timeStatistics));
        return timeStatistics;
    }
}
