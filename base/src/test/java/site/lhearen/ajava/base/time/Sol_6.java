package site.lhearen.ajava.base.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Measure.getRunningTime;

public class Sol_6 {
    @Test
    public void testFridays() {
        List<LocalDate> loopList = listAllFridaysByPlusDay(LocalDate.of(2000, 1, 1),
                LocalDate.of(2018, 6, 29), DayOfWeek.FRIDAY);
        List<LocalDate> jumpList = listAllFridaysByPlusWeeks(LocalDate.of(2000, 1, 1),
                LocalDate.of(2018, 6, 29), DayOfWeek.FRIDAY);

        if (jumpList.containsAll(loopList) && loopList.containsAll(jumpList)) {
            out.println("The same RESULT!");
        } else {
            out.println("Not the same");
            List<LocalDate> tmpList = new ArrayList<>(jumpList);
            jumpList.removeAll(loopList);
            out.println("JumpList: ");
            out.println(jumpList);
            loopList.removeAll(tmpList);
            out.println("LoopList: ");
            out.println(loopList);
        }

        //  LongSummaryStatistics{count=50, sum=77, min=1, average=1.540000, max=6}
        getRunningTime(() -> listAllFridaysByPlusWeeks(LocalDate.of(1000, 1, 1),
                LocalDate.of(2018, 6, 29), DayOfWeek.FRIDAY), 50, "ByWeek");
        // LongSummaryStatistics{count=50, sum=849, min=12, average=16.980000, max=44}
        getRunningTime(() -> listAllFridaysByPlusDay(LocalDate.of(1000, 1, 1),
                LocalDate.of(2018, 6, 29), DayOfWeek.FRIDAY), 50, "ByDay");
    }

    private List<LocalDate> listAllFridaysByPlusDay(LocalDate startDay, LocalDate endDay, DayOfWeek dayOfWeek) {
        LocalDate theDay = startDay;
        List<LocalDate> loopList = new ArrayList<>();

        while (endDay.until(theDay).isNegative() || theDay.isEqual(endDay)) {
            if (theDay.getDayOfWeek() == dayOfWeek) {
//                out.println(theDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd E")));
                loopList.add(theDay);
            }
            theDay = theDay.plusDays(1);
        }
        return loopList;
    }

    private List<LocalDate> listAllFridaysByPlusWeeks(LocalDate startDay, LocalDate endDay, DayOfWeek dayOfWeek) {
        List<LocalDate> jumpList = new ArrayList<>();
        LocalDate anotherDay = startDay;
        while(anotherDay.getDayOfWeek() != dayOfWeek) {
            anotherDay = anotherDay.plusDays(1);
        }
        jumpList.add(anotherDay);
        while (anotherDay.isBefore(endDay)) {
            anotherDay = anotherDay.plusWeeks(1);
            if (endDay.until(anotherDay).isNegative() || anotherDay.isEqual(endDay)) {
                jumpList.add(anotherDay);
            }
        }

        return jumpList;
    }
}
