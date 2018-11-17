package site.lhearen.ajava.base.time;

import java.time.LocalDate;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_2 {
    @Test
    public void testPlusYear() {
        LocalDate theDate = LocalDate.of(2000, 2, 29);
        LocalDate plusOneYear = theDate.plusYears(1);
        out.println(plusOneYear);
        LocalDate plusFourYear = theDate.plusYears(4);
        out.println(plusFourYear); // 2004-02-29
        LocalDate plusOneYearFourTimes; // 2004-02-28
        for (int i = 0; i < 4; ++i) {
            plusOneYearFourTimes = theDate.plusYears(1);
            out.println(plusOneYearFourTimes);
            theDate = plusOneYearFourTimes;
        }
    }
}
