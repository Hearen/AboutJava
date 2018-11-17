package site.lhearen.ajava.base.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_4 {
    @Test
    public void testIntStream() {
        int[] values = {1, 4, 9, 16};
        Stream.of(values).forEachOrdered(out::println);
        Arrays.asList(values).stream().forEach(out::println);
        out.println("Two ways to convert to IntStream");
        Arrays.stream(values).forEach(out::println);
        IntStream.of(values).forEach(out::println);
    }
}
