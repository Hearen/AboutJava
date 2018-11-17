package site.lhearen.ajava.base.time;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_1 {
    @Test
    public void testCoderDay() {
        out.println(getCoderDayByPlus(2018));
        out.println(getCoderDayByPeriod(2018));
        out.println(getCoderDayManually(2018));
    }

    public LocalDate getCoderDayByPlus(int year) {
        return LocalDate.of(year, 1, 1).plusDays(255);
    }

    public LocalDate getCoderDayByPeriod(int year) {
        return LocalDate.of(year, 1, 1).plus(Period.ofDays(255));
    }

    public LocalDate getCoderDayManually(int year) {
        LocalDate startDay = LocalDate.of(year, 1, 1);
        LocalDate theDay = startDay;
        // startDay.until(theDay).get(ChronoUnit.DAYS)
        // get dayOfMonth;
        while (startDay.until(theDay, ChronoUnit.DAYS) < 255) {
            theDay = theDay.plusDays(1);
        }
        return theDay;
    }
}
