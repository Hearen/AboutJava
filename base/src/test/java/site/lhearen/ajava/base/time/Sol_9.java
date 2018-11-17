package site.lhearen.ajava.base.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Output.printNotContained;

public class Sol_9 {
    @Test
    public void testZonedTime() {
        List<String> zoneIdList0 = getListUsingInstant();
        List<String> zoneIdList1 = getListUsingZoneOffset();
        if (zoneIdList0.containsAll(zoneIdList1) && zoneIdList1.containsAll(zoneIdList0)) {
            out.println("Completely Equal");
        } else {
            printNotContained(zoneIdList0, zoneIdList1);
        }
    }

    private List<String> getListUsingInstant() {
        Instant now = Instant.now();
        List<String> theList = new ArrayList<>();
        ZoneId.getAvailableZoneIds().stream().forEach(zoneId -> {
            ZonedDateTime zonedDateTime = now.atZone(ZoneId.of(zoneId));
            if (zonedDateTime.getOffset().getTotalSeconds() % 3600 == 0) {
                theList.add(zoneId);
            }
        });
        return theList;
    }

    private List<String> getListUsingZoneOffset() {
        List<String> theList = new ArrayList<>();
        ZoneId.getAvailableZoneIds().stream().forEach(id -> {
            ZoneId zoneId = ZoneId.of(id);
            int seconds = zoneId.getRules().getOffset(Instant.now()).getTotalSeconds();
            if (seconds % 3600 == 0) {
                theList.add(id);
            }
        });
        return theList;
    }
}
