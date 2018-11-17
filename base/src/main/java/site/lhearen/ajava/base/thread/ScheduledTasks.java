package site.lhearen.ajava.base.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

//import static Util.delay;

public class ScheduledTasks {
    private static long interval = 1000L;
    private static long newInterval = 2 * 1000L;
    // http://tutorials.jenkov.com/java-util-concurrent/executorservice.html#executorservice-shutdown
    public static void main(String... args) {
        out.println("Test restart a task promptly");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(ScheduledTasks::sayHi, 0, interval, TimeUnit.MILLISECONDS);
//        delay(4000L);
        scheduledExecutorService.shutdown();
        interval = newInterval;
        scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(ScheduledTasks::sayHi, 0, interval, TimeUnit.MILLISECONDS);
    }

    private static void sayHi() {
        out.println("Say Hi with interval: " + interval);
    }
}
