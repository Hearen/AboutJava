package site.lhearen.ajava.base.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PeriodicTask {
    public static void main(String... args) {
        // System.out.println(LocalTime.ofSecondOfDay(getDelayTo(15, 4)));
        // System.out.println(LocalTime.ofSecondOfDay(getDelayTo(16, 4)));
//        testTimer();
//        testScheduledExecutorService();
        System.out.println(TimeUnit.DAYS.toSeconds(1));
    }

    private static long getDelayTo(int hour, int minute) {
        LocalDateTime currentTime = LocalDateTime.now();
        long gapToSpecified = ChronoUnit.SECONDS.between(currentTime, LocalDate.now().atTime(hour, minute));
        if (gapToSpecified < 0) { // the time already passed => do it tomorrow;
            return ChronoUnit.SECONDS.between(currentTime, LocalDate.now().plusDays(1).atTime(hour, minute));
        }
        return gapToSpecified; // not passed, do it later today at the specified time;
    }

    public static void testTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(LocalDateTime.now());
                System.out.println("Hello world");
            }
        }, getDelayTo(13, 0), TimeUnit.HOURS.toSeconds(5));
    }

    public static void testScheduledExecutorService() {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
                    System.out.println("Hello World!");
                }, getDelayTo(13, 0), TimeUnit.HOURS.toSeconds(5), TimeUnit.SECONDS);
    }
}
