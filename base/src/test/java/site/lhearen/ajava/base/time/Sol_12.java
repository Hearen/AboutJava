package site.lhearen.ajava.base.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public class Sol_12 {
    @Test
    public void testSchedule() {
        ZonedDateTime meetingOne = LocalDateTime.now().minusHours(7).atZone(ZoneId.of("Africa/Cairo"));
        ZonedDateTime meetingTwo = LocalDateTime.now().minusHours(6).minusMinutes(30).atZone(ZoneId.of("Europe/London"));
        ZonedDateTime meetingThree = LocalDateTime.now().minusHours(10).minusMinutes(30).atZone(ZoneId.of("America/Indiana/Petersburg"));
        List<ZonedDateTime> meetingSchedule = new ArrayList<>();
        meetingSchedule.add(meetingOne);
        meetingSchedule.add(meetingTwo);
        meetingSchedule.add(meetingThree);
        out.println("The original meetings: ");
        meetingSchedule.forEach(out::println);
        out.println("****************************************************");
        getMeetingsWithinOneHour(meetingSchedule).forEach(out::println);
    }

    private List<ZonedDateTime> getMeetingsWithinOneHour(List<ZonedDateTime> meetings) {
        ZonedDateTime localTime = ZonedDateTime.now();
        Instant now = localTime.toInstant().plus(1, ChronoUnit.HOURS);
        out.println("Transform to the current zone: ");
        meetings.forEach(meeting -> out.println(meeting.toInstant().atZone(localTime.getZone())));
        return meetings.stream().filter(meeting -> meeting.toInstant().isBefore(now)).collect(toList());
    }
}
