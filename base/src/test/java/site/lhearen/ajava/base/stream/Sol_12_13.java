package site.lhearen.ajava.base.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Output.readFileByWords;

public class Sol_12_13 {
    @Test
    public void testCounter() {
        List<String> lines = readFileByWords();
        testCounterArr(lines);
        testCounterBasic(lines);
        testGroupingBy(lines);
    }

    private void testCounterBasic(List<String> lines) {
        int[] counters = new int[12];
        lines.stream().forEach(s -> {
            if (s.length() < 12) {
                counters[s.length()]++;
            }
        });
        Arrays.stream(counters).forEach(c -> out.print(c + ", "));
        out.println("************");
    }

    private void testCounterArr(List<String> lines) {
        AtomicIntegerArray counters = new AtomicIntegerArray(12);
        lines.stream().parallel().forEach(s -> {
            if (s.length() < 12) {
                counters.getAndIncrement(s.length());
            }
        });
        Stream.of(counters).forEach(out::println);
    }

    private void testGroupingBy(List<String> lines) {
        lines.stream()
                .filter(s -> s.length() < 12)
                .collect(Collectors.groupingBy(String::length, Collectors.counting()))
        .values().stream().forEach(c -> out.print(c + ", "));
        out.println("************");
    }
}
