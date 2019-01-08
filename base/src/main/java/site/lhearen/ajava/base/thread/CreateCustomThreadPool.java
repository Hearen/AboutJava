package site.lhearen.ajava.base.thread;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Util.delay;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class CreateCustomThreadPool {
    private static ExecutorService executorService = new ThreadPoolExecutor(4, Runtime
            .getRuntime().availableProcessors() * 50, 2, TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
            new ThreadFactoryBuilder().setNameFormat("lv-global-thread-%d").build());

    public static void main(String... args) {
        List<CompletableFuture<String>> futures = IntStream.range(0, 1000).boxed()
                .map(i -> CompletableFuture.supplyAsync(() -> {
                    out.println("i: " + i + " thread name: " + Thread.currentThread().getName());
                    delay(1000);
                    return String.valueOf(i * 2);
                }, executorService)).collect(Collectors.toList());
        futures.stream().forEach(CompletableFuture::join);
        executorService.shutdown();
    }
}
