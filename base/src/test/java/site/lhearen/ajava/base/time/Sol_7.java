package site.lhearen.ajava.base.time;

import java.time.LocalDateTime;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_7 {

    @Test
    public void testInterwined() {
        LocalDateTime point0 = LocalDateTime.now().minusDays(1);
        LocalDateTime point1 = LocalDateTime.now();
        LocalDateTime point2 = LocalDateTime.now().plusHours(2);
        LocalDateTime point3 = LocalDateTime.now().plusDays(1);
        LocalDateTime point4 = LocalDateTime.now().plusDays(1).plusHours(2);
        TimeInterval interval0 = new TimeInterval(point0, point1);
        TimeInterval interval1 = new TimeInterval(point1, point2);
        out.println(isInterwined(interval0, interval1));
        out.println(isInterwined(interval1, interval0));
        TimeInterval interval2= new TimeInterval(point0, point3);
        out.println(isInterwined(interval1, interval2));
        TimeInterval interval3 = new TimeInterval(point3, point4);
        out.println(isInterwined(interval1, interval3));
    }

    private boolean isInterwined(TimeInterval interval1, TimeInterval interval2) {
        return isWithin(interval1.startTime, interval2) || isWithin(interval1.endTime, interval2);
    }

    private boolean isWithin(LocalDateTime dateTime, TimeInterval interval) {
        return dateTime.isEqual(interval.startTime) || dateTime.isEqual(interval.endTime) ||
                (interval.startTime.isBefore(dateTime) && interval.endTime.isAfter(dateTime));
    }

    class TimeInterval {
        LocalDateTime startTime;
        LocalDateTime endTime;
        public TimeInterval(LocalDateTime start, LocalDateTime end) {
            startTime = start;
            endTime = end;
        }
    }
}
