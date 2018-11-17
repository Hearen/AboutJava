package site.lhearen.ajava.mytools.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Timer {
    private Timer() {

    }

    public static void timer(Runnable runnable, int times, String title) {
        Long start = 0L;
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < times; ++i) {
            start = System.nanoTime();
            runnable.run();
            list.add(System.nanoTime() - start);
        }
        System.out.println(title + ": " + list.stream().collect(Collectors.summarizingLong(Long::intValue)));
    }
}
