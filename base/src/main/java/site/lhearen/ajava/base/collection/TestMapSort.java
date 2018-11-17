package site.lhearen.ajava.base.collection;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class TestMapSort {
    public static void main(String... args) {
//        TestMapSort testMap = new TestMapSort();
//        testMap.testBasicSort();

        testAbsent();
    }

    public static void testAbsent() {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        map.computeIfAbsent("a", (unused) -> new HashMap<>()).put("a", 1);

        map.putIfAbsent("a", new HashMap<>());
        map.get("a").put("a", 1);

        map.entrySet().stream().forEach(entry -> System.out.println(entry));
    }

    public void testBasicSort() {
        Map<String, Integer> unsortedMap = new HashMap<>();
        unsortedMap.put("z", 10);
        unsortedMap.put("b", 5);
        unsortedMap.put("a", 6);
        unsortedMap.put("c", 20);
        unsortedMap.put("d", 1);
        unsortedMap.put("e", 7);
        unsortedMap.put("y", 8);
        unsortedMap.put("n", 99);
        unsortedMap.put("g", 50);
        unsortedMap.put("m", 2);
        unsortedMap.put("f", 9);
        out.println(unsortedMap);

        Map<String, Integer> keySortedMap = new TreeMap<>(unsortedMap);
        out.println(keySortedMap);

        Map<String, Integer> keyReverseSortedMap = new TreeMap<>(Comparator.reverseOrder()); // key compared;
        keyReverseSortedMap.putAll(unsortedMap);
        out.println(keyReverseSortedMap);

        Map<String, Integer> valueSortedMap = unsortedMap
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldEntry, newEntry) -> oldEntry, LinkedHashMap::new));
        out.println(valueSortedMap);
    }


}
