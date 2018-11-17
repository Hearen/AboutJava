package site.lhearen.ajava.base.stream.basic;

import static java.lang.System.out;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TestParallelStream {
    private static Set<String> ThreadNameSet = new TreeSet<>();

    public static void main(String... args) {
        testSum();
        testForkJoinPool();
    }

    private static void testSum() {
        List<Long> aList = LongStream.rangeClosed(0, 10_000).boxed().collect(Collectors.toList());
        BlockingDeque blockingDeque = new LinkedBlockingDeque(1000);
        ThreadPoolExecutor fixedSizePool = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS,
                blockingDeque, new MyThreadFactory("my-thread"));
        MyAppThread.setDebug(true);
        Long result = 0L;
        try {
            // using a custom thread pool is actually not that useful since the parallelStream will in the end use
            // the commonThreadPool - but in this way you can add one more thread since it will be started in the new
            // thread instead of the main thread.
            result = fixedSizePool.submit(() -> aList.parallelStream()
                    .peek((i) -> out.println(Thread.currentThread().getName()))
                    .reduce(0L, Long::sum)).get();
        } catch (Exception e) {

        }
        out.println(MyAppThread.getDebug());
        out.println(MyAppThread.getThreadsAlive());
        out.println(MyAppThread.getThreadsCreated());
        out.println(result);

    }

    private static Callable<Long> getSum() {
        List<Long> aList = LongStream.rangeClosed(0, 10_000_000).boxed().collect(Collectors.toList());
        return () -> aList.parallelStream()
                .peek((i) -> {
                    String threadName = Thread.currentThread().getName();
                    ThreadNameSet.add(threadName);
                })
                .reduce(0L, Long::sum);
    }

    private static void testForkJoinPool() {
        final int parallelism = 10;

        ForkJoinPool forkJoinPool = null;
        Long result = 0L;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);
            result = forkJoinPool.submit(getSum()).get(); //this makes it an overall blocking call

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown(); //always remember to shutdown the pool
            }
        }
        out.println(result);
        out.println(ThreadNameSet);
        out.println(ForkJoinPool.commonPool().getPoolSize());
    }
}


class MyThreadFactory implements ThreadFactory {
    private final String poolName;

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
        MyAppThread.setDebug(true);
    }

    public Thread newThread(Runnable runnable) {
        return new MyAppThread(runnable, poolName);
    }
}

class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();
    private static volatile boolean debugLifecycle = false;

    public MyAppThread(Runnable r) {
        this(r, DEFAULT_NAME);
    }

    public MyAppThread(Runnable runnable, String name) {
        super(runnable, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void uncaughtException(Thread t,
                                          Throwable e) {
                log.log(Level.SEVERE,
                        "UNCAUGHT in thread " + t.getName(), e);
            }
        });
    }

    public static int getThreadsCreated() {
        return created.get();
    }

    public static int getThreadsAlive() {
        return alive.get();
    }

    public static boolean getDebug() {
        return debugLifecycle;
    }

    public static void setDebug(boolean b) {
        debugLifecycle = b;
    }

    public void run() {
        // Copy debug flag to ensure consistent value throughout.
        boolean debug = debugLifecycle;
        if (debug) log.log(Level.INFO, "Created " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) log.log(Level.INFO, "Exiting " + getName());
        }
    }
}
