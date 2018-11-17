package site.lhearen.ajava.base.stream;

import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_5 {
    @Test
    public void testGenertor() {
        generateRandomInteger(3).limit(10)
                .forEachOrdered(out::println);

        generateFib().limit(20).forEachOrdered(out::println);
    }

    private Stream<Long> generateRandomInteger(long seed) {
        return Stream.iterate(seed, t -> (25_214_903_917L * t + 11) % Double.valueOf(Math.pow(2, 48)).longValue())
                .skip(1);
    }

    private Stream<Long> generateFib() {
        return Stream.iterate(new long[]{0, 1}, t -> new long[]{t[1], t[0] + t[1]})
                .map(t -> t[0]);
    }
}
