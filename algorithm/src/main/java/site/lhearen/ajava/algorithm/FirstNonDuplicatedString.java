package site.lhearen.ajava.algorithm;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FirstNonDuplicatedString {
    public static void main(String... args) {
        String[] arr = {"Dog", "Cat", "Dog", "Wolf", "lion"};
        Map<String, Long> stringCountMap = Arrays.stream(arr)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        for (String s : arr) {
            if (stringCountMap.get(s) == 1) {
                System.out.println("The first non-duplicate string: " + s);
                break;
            }
        }

        another(arr);
    }

    private static void another(String[] arr) {
        Map<String, Long> stringCountMap = Arrays.stream(arr)
                .collect(Collectors.groupingBy(s -> s, LinkedHashMap::new, Collectors.counting()));
        for (String s : stringCountMap.keySet()) {
            if (stringCountMap.get(s) == 1) {
                System.out.println("The first non-duplicate string: " + s);
                break;
            }
        }
    }
}
