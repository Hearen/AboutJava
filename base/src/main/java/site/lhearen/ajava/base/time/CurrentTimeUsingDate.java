package site.lhearen.ajava.base.time;

import java.util.Date;

public class CurrentTimeUsingDate {
    public static void main(String... args) {
        Date nowDate = new Date(System.currentTimeMillis());
        System.out.println(nowDate);
    }
}
