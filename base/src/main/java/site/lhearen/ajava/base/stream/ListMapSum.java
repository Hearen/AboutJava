package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

public class ListMapSum {
    public static void main(String... args) {
        List<Map<String, Long>> mapList = new ArrayList();
        Map<String, Long> map1 = new HashMap<>();
        Map<String, Long> map2 = new HashMap<>();
        Map<String, Long> map3 = new HashMap<>();
        map1.put("col1", 90L);
        map1.put("col2", 50L);
        map1.put("col3", 10L);
        map2.put("col1", 90L);
        map2.put("col2", 50L);
        map2.put("col3", 10L);
        map3.put("col1", 90L);
        map3.put("col2", 50L);
        map3.put("col3", 10L);
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        Map<String, Long> sum = mapList.stream().flatMap(map -> map.entrySet().stream())
                .collect(groupingBy(Map.Entry::getKey, summingLong(Map.Entry::getValue)));
        Long sumVal1 = sum.get("col1"); // 270
        Long sumVal2 = sum.get("col2"); // 150
        Long sumVal3 = sum.get("col3"); // 30
    }
}
