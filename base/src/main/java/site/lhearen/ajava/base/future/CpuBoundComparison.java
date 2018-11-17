package site.lhearen.ajava.base.future;

import site.lhearen.ajava.mytools.util.Util;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static site.lhearen.ajava.mytools.util.Util.getCpuBoundNumber;
import static site.lhearen.ajava.mytools.util.Util.test;

public class CpuBoundComparison {

    static final int CEILING = 10_000; // about 1_000ms;
    private static final int REQUEST_COUNT = 10;
    private static final int TEST_TIMES = 20;
    private static final int CORE_COUNT = Runtime.getRuntime().availableProcessors();

    public static void main(String... args) {
//        test(() -> getCpuBoundNumber(), TEST_TIMES, "CPU-bound test");
//        test(() -> sequentialGet(), TEST_TIMES, "Sequential Get");
        test(() -> parallelGet(), TEST_TIMES, "Parallel Get");
        test(() -> parallelGetOptimised(), TEST_TIMES, "Parallel Get Optimised");
//        test(() -> concurrencyGetBasic(), TEST_TIMES, "Concurrency Get Basic");
//        test(() -> concurrencyGetNew(), TEST_TIMES, "Concurrency Get ** New **");
        test(() -> concurrencyGetOptimized(), TEST_TIMES, "Concurrency Get ** Optimised **");
    }

    private static int concurrencyGetOptimized() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<CompletableFuture<Integer>> futureList = IntStream.rangeClosed(1, REQUEST_COUNT).boxed()
                .map(i -> CompletableFuture.supplyAsync(Util::getCpuBoundNumber, executorService))
                .collect(Collectors.toList());
        return futureList.stream().map(CompletableFuture::join).reduce(0, Integer::sum);
    }

    private static int concurrencyGetNew() {
        ExecutorService executorService = Executors.newFixedThreadPool(CORE_COUNT);
        List<CompletableFuture<Integer>> futureList = IntStream.rangeClosed(1, REQUEST_COUNT).boxed()
                .map(i -> CompletableFuture.supplyAsync(Util::getCpuBoundNumber, executorService))
                .collect(Collectors.toList());
        return futureList.stream().map(CompletableFuture::join).reduce(0, Integer::sum);
    }


    private static int concurrencyGetBasic() {
        List<CompletableFuture<Integer>> futureList = IntStream.rangeClosed(1, REQUEST_COUNT).boxed()
                .map(i -> CompletableFuture.supplyAsync(Util::getCpuBoundNumber))
                .collect(Collectors.toList());
        return futureList.stream().map(CompletableFuture::join).reduce(0, Integer::sum);
    }

    private static int parallelGet() {
        return IntStream.rangeClosed(1, REQUEST_COUNT)
                .parallel()
                .map(i -> getCpuBoundNumber()).sum();
    }

    private static int parallelGetOptimised() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(CORE_COUNT);
        return forkJoinPool.submit(() -> IntStream.rangeClosed(0, REQUEST_COUNT)
                .parallel()
                .map(i -> getCpuBoundNumber()).sum()).join();
    }

    private static int sequentialGet() {
        int sum = 0;
        for (int i = 1; i <= REQUEST_COUNT; ++i) {
            sum += getCpuBoundNumber();
        }
        return sum;
    }
}
