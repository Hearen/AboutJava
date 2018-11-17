package site.lhearen.ajava.base.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GroupTest {

    public static void main(String[] args) {
        List<Integer> data = Arrays.asList(1, 3, 4, 1, 10, 29, 23);
        List<Integer> result =
                data.stream()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.groupingBy(i -> i / 10))
                        .values().stream().flatMap(g -> g.size() > 3 ? g.subList(0, 3).stream() : g.stream())
                        .collect(Collectors.toList());

                result = data.stream()
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.groupingBy(i -> i / 10))
                        .values().stream()
                .flatMap(entry -> entry.stream().limit(3))
                .collect(Collectors.toList());
        System.out.println();
        result.stream().forEach(d -> System.out.print(d + "\t"));
        System.out.println();
    }
}

