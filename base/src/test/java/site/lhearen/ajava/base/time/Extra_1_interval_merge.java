package site.lhearen.ajava.base.time;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static java.util.stream.Collectors.toList;

public class Extra_1_interval_merge {
    @Test
    public void testAvailablePeriod() {
        List<MyPeriod> absentPeriods0 = new ArrayList<>();
        absentPeriods0.add(makePeriod(LocalDate.now(), LocalDate.now().plusDays(1)));
        absentPeriods0.add(makePeriod(LocalDate.now().plusDays(4), LocalDate.now().plusDays(6)));
        absentPeriods0.add(makePeriod(LocalDate.now().plusDays(2), LocalDate.now().plusDays(3)));


        List<MyPeriod> absentPeriods1 = new ArrayList<>();
        absentPeriods1.add(makePeriod(LocalDate.now(), LocalDate.now().plusDays(2)));
        absentPeriods1.add(makePeriod(LocalDate.now().plusDays(5), LocalDate.now().plusDays(7)));

        List<List<MyPeriod>> absentListList = new ArrayList<>();
        absentListList.add(absentPeriods0);
        absentListList.add(absentPeriods1);
        System.out.println(getAvailablePeriods(absentListList));
    }

    private List<MyPeriod> getAvailablePeriods(List<List<MyPeriod>> absentListList) {
        // Step - 1: Collect all periods;
        List<MyPeriod> tempList = new ArrayList<>();
        absentListList.stream().forEach(list -> tempList.addAll(list));

        // Step - 2: Sort the periods based on the startDate and then endDate;
        List<MyPeriod> absentList = tempList.stream().sorted((period1, period2) -> {
            if (!period1.startDate.isEqual(period2.startDate)) {
                return period1.startDate.compareTo(period2.startDate);
            } else {
                return period1.endDate.compareTo(period2.endDate);
            }
        }).collect(toList());

        // Step - 3: Merge all overlapped periods to form an one-dimension occupied period list;
        List<MyPeriod> mergedPeriods = new ArrayList<>();
        for (MyPeriod period : absentList) {
            if (mergedPeriods.isEmpty()) {
                mergedPeriods.add(period);
            } else {
                MyPeriod lastPeriod = mergedPeriods.get(mergedPeriods.size() - 1);
                if (!lastPeriod.endDate.isBefore(period.startDate)) {
                    if (lastPeriod.endDate.isBefore(period.endDate)) {
                        lastPeriod.endDate = period.endDate;
                    }
                } else {
                    mergedPeriods.add(period);
                }
            }
        }

        // Step - 4: Pick the periods from the occupied period list;
        List<MyPeriod> availablePeriods = new ArrayList<>();
        for (int i = 0, len = mergedPeriods.size(); i < len - 1; i++) {
            availablePeriods.add(makePeriod(mergedPeriods.get(i).endDate, mergedPeriods.get(i+1).startDate));
        }
        return availablePeriods;
    }

    private MyPeriod makePeriod(LocalDate startDate, LocalDate endDate) {
        MyPeriod thePeriod = new MyPeriod();
        thePeriod.startDate = startDate;
        thePeriod.endDate = endDate;
        return thePeriod;
    }

    class MyPeriod {
        LocalDate startDate;
        LocalDate endDate;
        @Override
        public String toString() {
            return String.format("Start: %s, End: %s", startDate, endDate);
        }
    }

}
