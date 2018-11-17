package site.lhearen.ajava.base.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_11 {
    @Test
    public void testTimeTravel() {
        Long duration = getDuration( "Europe/Paris", LocalDateTime.now().withHour(14).withMinute(5),
                "America/Los_Angeles", LocalDateTime.now().withHour(16).withMinute(40));
        long hour = duration / 3600;
        long minute = duration % 3600 / 60;
        LocalTime gap = LocalTime.ofSecondOfDay(duration); // clever trick;
        out.println(hour + " Hours " + minute + " Minutes");
        out.println(gap);
    }

    private Long getDuration(String startZoneId, LocalDateTime startDateTime, String endZoneId, LocalDateTime endDateTime) {
        out.println(Instant.now().atZone(ZoneId.of(startZoneId)));
        out.println(Instant.now().atZone(ZoneId.of(endZoneId)));
        return startDateTime.atZone(ZoneId.of(startZoneId)).toInstant().until(endDateTime.atZone(ZoneId.of(endZoneId)).toInstant(), ChronoUnit.SECONDS);
    }
}
