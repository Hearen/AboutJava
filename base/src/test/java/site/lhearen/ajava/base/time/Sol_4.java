package site.lhearen.ajava.base.time;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_4 {
    @Test
    public void testCal() {
        /*
               Mar 2013
         Mo Tu We Th Fr Sa Su
                      1  2  3
          4  5  6  7  8  9 10
         11 12 13 14 15 16 17
         18 19 20 21 22 23 24
         25 26 27 28 29 30 31

               Dec 2014
         Mo Tu We Th Fr Sa Su
          1  2  3  4  5  6  7
          8  9 10 11 12 13 14
         15 16 17 18 19 20 21
         22 23 24 25 26 27 28
         29 30 31
         */
        printCalendar(2013, 3);
        printCalendar(2014, 12);
    }


    private void printCalendar(int year, int month) {
        LocalDate startDay = LocalDate.of(year, month, 1);
        LocalDate endDay = startDay.plusMonths(1);
        String calString = String.format("      %s %s     ", startDay.getMonth().getDisplayName(TextStyle.SHORT, Locale.US), year);
        calString += "\n Mo Tu We Th Fr Sa Su\n";
        LocalDate theDay = startDay;

        while (theDay.until(endDay, ChronoUnit.DAYS) > 0) {
            if (theDay.until(startDay).isZero()) {
                for (int i = 1; i < theDay.getDayOfWeek().getValue(); ++i) {
                    calString += "   ";
                }
            }
            calString += String.format("%3d", theDay.getDayOfMonth());
            if (theDay.getDayOfWeek().getValue() == 7) {
                calString += "\n";
            }
            theDay = theDay.plusDays(1);
        }
        calString = calString.replaceAll("\n\\s+", "\n");
        out.println(calString);
    }
}
