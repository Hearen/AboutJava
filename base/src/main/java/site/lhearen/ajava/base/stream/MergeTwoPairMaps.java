package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.util.Pair;

public class MergeTwoPairMaps {
    public static void main(String... args) {
        Map<Pair<Long,String>, List<String>>  stringValues = new HashMap<>();
        Map<Pair<Long,String>, List<Boolean>> booleanValues = new HashMap<>();

        stringValues.put(new Pair<>(1L, "1"), new ArrayList<>(Arrays.asList("1")));
        booleanValues.put(new Pair<>(1L, "1"), new ArrayList<>(Arrays.asList(true)));

        Map<Pair<Long, String>, Pair<List<String>, List<Boolean>>> stringBoolValues = stringValues.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new Pair(entry.getValue(), booleanValues.get(entry.getKey()))));
        System.out.println(stringBoolValues.values());


        System.out.println(MergeTwoPairMaps.class.getPackage().getName());
    }
}
