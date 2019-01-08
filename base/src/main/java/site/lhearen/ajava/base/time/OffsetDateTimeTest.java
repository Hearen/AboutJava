package site.lhearen.ajava.base.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeTest {
    public static void main(String... args) {
        System.out.println(OffsetDateTime.parse("2018-08-21T10:12:06.872722+00:00",
                DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDate());

        System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        String timeStamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME).toString();
        timeStamp = timeStamp.replaceAll("[:|\\-|T|+|\\.]", "");
        System.out.println(timeStamp);
        LocalDate today = LocalDate.now();
        System.out.println(today.atTime(LocalTime.of(0, 0, 0)));
        System.out.println(today.atTime(LocalTime.of(23, 59, 59)));
        System.out.println(today.atTime(LocalTime.of(0, 0, 0)).toEpochSecond(ZoneOffset.UTC));
        System.out.println(today.atTime(LocalTime.of(23, 59, 59)).toEpochSecond(ZoneOffset.UTC));
    }
}
