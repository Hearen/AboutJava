package site.lhearen.ajava.base.thread;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class TestScheduledExecutorService {
    private static final Long timeUnit = 50L;
    public static void main(String... args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            delay(timeUnit);
            out.println("AtFixedRate" + Thread.currentThread().getThreadGroup().getName() + " - "
                    + Thread.currentThread().getName() + ": " + LocalDateTime.now());

        }, 0, 2 * timeUnit, TimeUnit.MILLISECONDS);
        delay(6 * timeUnit);
        scheduledExecutorService.shutdown();
        scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            delay(timeUnit);
            out.println("WithFixedDelay - " + Thread.currentThread().getThreadGroup().getName() + " - "
                    + Thread.currentThread().getName() + ": " + LocalDateTime.now());

        }, 0, 2 * timeUnit, TimeUnit.MILLISECONDS);
        delay(3 * timeUnit);
        ((ScheduledThreadPoolExecutor)scheduledExecutorService).setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
        ((ScheduledThreadPoolExecutor)scheduledExecutorService).setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        scheduledExecutorService.shutdown();
        out.println("*********** already shut down *****************");
    }

    private static void delay(Long timeCost) {
        try {
            Thread.sleep(timeCost);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }
}
