package site.lhearen.ajava.base.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NoSevenAndTwo {
    public static void main(String... args) {
        System.out.println(getTopK(10));
    }

    private static List<Integer> getTopK(int k) {
        return IntStream.range(0, Integer.MAX_VALUE)
                .filter(i -> i % 7 != 0 && i % 2 != 0)
                .limit(k).boxed()
                .collect(Collectors.toList());
    }
}
