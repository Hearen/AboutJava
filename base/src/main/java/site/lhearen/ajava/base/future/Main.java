package site.lhearen.ajava.base.future;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Main {
    private static final int LEN = 3;
    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(50);
    public static void main(String... args) {
        Stream.iterate(0, i -> i+1)
                .limit(LEN)
                .forEach(i -> {
                    testBasic();
//                    testAsync();
                });
//        testFilter();
    }

    private static void testBasic() {
        out.println("*****************************************");
        out.println("********** TESTING thenCompose **********");
        CompletableFuture[] futures = IntStream.rangeClosed(0, LEN).boxed()
                .map(i -> CompletableFuture.supplyAsync(() -> runStage1(i), EXECUTOR_SERVICE))
                .map(future -> future.thenCompose(i -> CompletableFuture.supplyAsync(() -> runStage2(i), EXECUTOR_SERVICE)))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
    }

    private static void testAsync() {
        out.println("*****************************************");
        out.println("******* TESTING thenComposeAsync ********");
        CompletableFuture[] futures = Stream.iterate(0, i -> i+1)
                .limit(LEN)
                .map(i -> CompletableFuture.supplyAsync(() -> runStage1(i), EXECUTOR_SERVICE))
                .map(future -> future.thenComposeAsync(i ->
                        CompletableFuture.supplyAsync(() -> runStage2(i), EXECUTOR_SERVICE)))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
    }

    private static void testFilter() {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        List<CompletableFuture<Integer>> intFutures = Stream.iterate(0, i -> i+1)
                .limit(5)
                .map(i -> CompletableFuture.supplyAsync(() -> {
                    int a = runStage1(i);
                    if (seen.add(a)) {
                        return a;
                    } else {
                        return -1;
                    }}))
                .map(future -> future.thenCompose(i -> CompletableFuture.supplyAsync(() -> {
                    if (i > 0) {
                        return runStage2(i);
                    } else {
                        return i;
                    }})))
                .collect(Collectors.toList());
        List<Integer> resultList = new ArrayList<>();
        try {
            for (CompletableFuture<Integer> future: intFutures) {
                resultList.add(future.join());
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
            out.println("Future failed!");
        }
        resultList.stream().forEach(out::println);
    }

    private static Integer runStage1(int a) {
        String s = String.format("Start: stage - 1 - value: %d - thread name: %s",
                a, Thread.currentThread().getName());
        out.println(s);
        Long start = System.currentTimeMillis();
        try {
            Thread.sleep(1500 + Math.abs(new Random().nextInt()) % 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s = String.format("Finish: stage - 1 - value: %d - thread name: %s - time cost: %d",
                a, Thread.currentThread().getName(), (System.currentTimeMillis() - start));
        out.println(s);
        return a;
    }

    private static Integer runStage2(int b) {
        String s = String.format("Start: stage - 2 - value: %d - thread name: %s",
                b, Thread.currentThread().getName());
        out.println(s);
        Long start = System.currentTimeMillis();
        try {
            Thread.sleep(200 + Math.abs(new Random().nextInt()) % 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s = String.format("Finish: stage - 2 - value: %d - thread name: %s - time cost: %d",
                b, Thread.currentThread().getName(), (System.currentTimeMillis() - start));
        out.println(s);
        return b;
    }
}
