package site.lhearen.ajava.base.future;

import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.Random;
import java.util.stream.LongStream;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Measure.getRunningTime;

public class Sol_8 {
    @Test
    public void testSortSpeed() {
        int testCount = 5;
        int arraySize = 10_000;
        while (true) {
            long[] longArr = LongStream.generate(() -> new Random().nextLong()).limit(arraySize).toArray();
            LongSummaryStatistics summary = getRunningTime(() -> testSort(longArr, false), testCount, "Arrays::sort");
            LongSummaryStatistics parallelSummary = getRunningTime(() -> testSort(longArr, true), testCount, "Arrays::parallelSort");
            if (parallelSummary.getAverage() < summary.getAverage()) {
                out.println("ParallelSort Quicker than Sort at Size: " + arraySize);
                break;
            }
            arraySize *= 10;
        }
    }

    private void testSort(long[] arr, boolean isParrallel) {
        long[] arrCopy = Arrays.copyOf(arr, arr.length);
        if (isParrallel) {
            Arrays.parallelSort(arrCopy);
        } else {
            Arrays.sort(arrCopy);
        }
    }
}
