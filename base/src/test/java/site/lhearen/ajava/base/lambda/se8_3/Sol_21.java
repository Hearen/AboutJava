package site.lhearen.ajava.base.lambda.se8_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

import org.junit.Test;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public class Sol_21 {
    private static <T, R> Future<R> map(Future<T> future, Function<T, R> mapper) {
        return new Future<R>() {

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return future.cancel(mayInterruptIfRunning);
            }

            @Override
            public boolean isCancelled() {
                return future.isCancelled();
            }

            @Override
            public boolean isDone() {
                return future.isDone();
            }

            @Override
            public R get() throws InterruptedException, ExecutionException {
                return mapper.apply(future.get());
            }

            @Override
            public R get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return mapper.apply(future.get(timeout, unit));
            }
        };
    }

    @Test
    public void testFuture() {
//        FutureTask<List<String>> namesFuture = new FutureTask<>(() -> {
//            Thread.currentThread().sleep(1000);
//            return new ArrayList<>(Arrays.asList("Hearen", "Katherine"));
//        });
//        namesFuture.run();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<String>> namesFuture = executorService.submit(() -> new ArrayList<>(Arrays.asList("Hearen", "Katherine")));
        try {
            map(namesFuture, (t) -> t.stream().map(s -> s.length()).collect(toList())).get().forEach(out::println);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
