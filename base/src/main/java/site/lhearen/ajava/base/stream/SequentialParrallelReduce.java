package site.lhearen.ajava.base.stream;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class SequentialParrallelReduce {
    public static void main(String... args) {
        for (int i = 0; i < 10; ++i) {
            testPerformance();
        }
    }

    private static void testPerformance() {
        int N = 10_000 + new Random().nextInt(100);
        Long start = System.nanoTime();
        long a = Stream.iterate(0, i -> i + 1).limit(N).mapToLong(Long::valueOf)
                .map(i -> i * 100)
                .reduce(50, (acc, next) -> acc + next);
        System.out.println("Sequential time cost: " + (System.nanoTime() - start));
        start = System.nanoTime();
        Object t = new Object();
        AtomicBoolean addedOnce = new AtomicBoolean(false);
        long b = Stream.iterate(0, i -> i + 1).limit(N).mapToLong(Long::valueOf)
                .parallel().map(i -> i * 100)
                .reduce(50, (acc, next) -> {
                    long ret;
                    synchronized (t) {
                        if (addedOnce.getAndSet(true) && acc == 50) {
                            ret = next;
                        } else {
                            ret = acc + next;
                        }
                    }
                    return ret;
                });
        System.out.println("Parallel   time cost: " + (System.nanoTime() - start));
        System.out.println(a);
        System.out.println(b);
    }
}
