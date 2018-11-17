package site.lhearen.ajava.base.collection;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.*;

import static java.lang.System.out;

public class TestCollectors {
    public static void main(String... args) {
        testJoin();
    }

    private static void testJoin() {
        List<String> arr = Arrays.asList("like");
        String s = arr.stream().collect(joining(" ", "I", "U"));
        out.println(s);

    }
}
