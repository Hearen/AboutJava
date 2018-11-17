package site.lhearen.ajava.base.future;

import site.lhearen.ajava.mytools.util.Measure;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_3 {
    private static final int PER_INCREMENT_TIMES = 100_000;
    private static final int THREAD_COUNT = 1_000;

    @Test
    public void testAdderAndLong() {
        Measure.getRunningTime(this::runLongAdder, 5, "Using LongAdder");
        Measure.getRunningTime(this::runAtomicLong, 5, "Using AtomicLong");
    }

    // Testing Result: Using LongAdder - Summary (ms): LongSummaryStatistics{count=5, sum=3448, min=626, average=689.600000, max=873}
    private void runLongAdder() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        LongAdder longAdder = new LongAdder();
        IntStream.range(0, THREAD_COUNT).forEach(i -> {
            executorService.submit(() -> IntStream.range(0, PER_INCREMENT_TIMES).forEach(j -> longAdder.increment()));
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
//        out.println(longAdder.longValue());
        out.println(longAdder.sum());
    }

    // Test Result: Using AtomicLong - Summary (ms): LongSummaryStatistics{count=5, sum=12362, min=2302, average=2472.400000, max=2546}
    private void runAtomicLong() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        AtomicLong atomicLong = new AtomicLong();
        IntStream.range(0, THREAD_COUNT).forEach(i -> {
            executorService.submit(() -> IntStream.range(0, PER_INCREMENT_TIMES).forEach(j -> atomicLong.getAndIncrement()));
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        out.println(atomicLong.get());
    }
}
