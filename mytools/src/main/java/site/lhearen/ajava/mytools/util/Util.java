package site.lhearen.ajava.mytools.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

public class Util {
    private static final int CEILING = 1_000;

    public static void test(Supplier<Integer> supplier, int times, String message) {
        List<Long> timeCosts = new ArrayList<>();
        Long start;
        int sum = 0;
        for (int i = 0; i < times; ++i) {
            start = System.nanoTime();
            sum += supplier.get();
            timeCosts.add((System.nanoTime() - start) / 1_000_000);
        }
        timeCosts.forEach(timeCost -> out.println(String.format("TimeCost: %s", timeCost)));
        out.println(String.format("%s - sum: %d - time cost summary (ms): %s", message, sum,
                timeCosts.stream().collect(Collectors.summarizingLong(Long::longValue))));
    }

    public static int getCpuBoundNumber() {
        return IntStream.rangeClosed(1, CEILING)
                .flatMap(a -> IntStream.rangeClosed(1, CEILING)
                        .filter(b -> a <= b && Math.sqrt(a * a + b * b) % 1 == 0)
                        .map(b -> (int) Math.sqrt(a * a + b * b))).sum();
    }

    public static int getIoBoundNumber(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        return i * 2;
    }


    public static void delay(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void delay() {
        delay(1000);
    }

    public static Double formatDouble(Double d) {
        return Double.valueOf(String.format("%.2f", d));
    }
}
