package site.lhearen.ajava.base.time;

import static java.lang.System.out;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    public static void main(String... args) {
        String dateStr = "08/22/2018";
        LocalDate theDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("MM/d/yyyy"));
        out.println("year: " + theDate.getYear() + " month: " + theDate.getMonthValue() + " day: " + theDate.getDayOfMonth());
        long timestamp = 60 * 1000L;
        out.println("" + LocalTime.ofSecondOfDay(timestamp / 1000));
        out.println(LocalDate.now().toString());
        out.println("abc".compareToIgnoreCase("2018-12-06"));
        out.println("ABC".compareToIgnoreCase("2018-12-06"));
    }
}
