package site.lhearen.ajava.base.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_5 {
    @Test
    public void testDaysGap() {
        LocalDate birthday = LocalDate.of(1992, 10, 4);
        out.println(birthday.until(LocalDate.now(), ChronoUnit.DAYS));

        LocalDate firstMetDay = LocalDate.of(2017, 7, 21);
        out.println(firstMetDay.until(LocalDate.now(), ChronoUnit.DAYS));

        LocalDate babeDay = LocalDate.of(2017, 9, 5);
        out.println(babeDay.until(LocalDate.now(), ChronoUnit.DAYS));

        LocalDate meetDay = LocalDate.of(2017, 9, 16);
        out.println(ChronoUnit.DAYS.between(meetDay, LocalDate.now()));

        LocalDate secondMeetDay = LocalDate.of(2017, 9, 29);
        out.println(secondMeetDay.getDayOfWeek());
        out.println(ChronoUnit.DAYS.between(secondMeetDay, LocalDate.now()));

        LocalDate loveDay = LocalDate.of(2017, 9, 30);
        out.println(ChronoUnit.DAYS.between(loveDay, LocalDate.now()));
    }

}


