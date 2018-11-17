package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ToMapTest {
    public static void main(String... args) {
        List<String> list = new ArrayList<>(Arrays.asList("12absc", "2bbds", "abc"));
        Map<String, String> stringStringMap = list.stream().collect(Collectors.toMap(s -> s, s -> s));
        System.out.println(stringStringMap);
        list.add("12absc");
        stringStringMap = list.stream()
                .collect(Collectors.toMap(s -> s, s -> s, (oldValue, newValue) -> oldValue));
        System.out.println(stringStringMap);
    }
}
