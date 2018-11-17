package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListToListMap {
    public static void main(String[] args) {
        final String test = "name=john,age=28;name=paul,age=30;name=adam,age=50";

        final List<Map<String, String>> result1 = loop(test);
        final List<Map<String, String>> result2 = stream(test);
        final List<Map<String, String>> result3 = anotherStream(test);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    private static List<Map<String, String>> anotherStream(String str) {
        List<String> listOfStrings = Arrays.asList(str.split(";"));
        return listOfStrings.stream().map(s -> {
            String[] ss = s.split(",|=");
            return new HashMap<String, String>() {{
                put(ss[0], ss[1]);
                put(ss[2], ss[3]);
            }};
        }).collect(Collectors.toList());
    }


    private static List<Map<String, String>> loop(String str) {
        long start = System.nanoTime();

        List<Map<String, String>> result = new ArrayList<>();
        String[] persons = str.split(";");

        for (String person : persons) {
            String[] attributes = person.split(",");
            Map<String, String> attributeMapping = new HashMap<>();

            for (String attribute : attributes) {
                String[] attributeParts = attribute.split("=");

                attributeMapping.put(attributeParts[0], attributeParts[1]);
            }

            result.add(attributeMapping);
        }

        long end = System.nanoTime();
        System.out.printf("%d nano seconds\n", (end - start));

        return result;
    }

    private static List<Map<String, String>> stream(final String str) {
        long start = System.nanoTime();

        List<String> listOfStrings = Arrays.asList(str.split(";"));
        List<Map<String, String>> result = listOfStrings.stream()
                .map(record -> Arrays.asList(record.split(",")).stream().map(field -> field.split("="))
                        .collect(Collectors.toMap(keyValue -> keyValue[0].trim(), keyValue -> keyValue[1].trim())))
                .collect(Collectors.toList());

        long end = System.nanoTime();

        System.out.printf("%d nano seconds\n", (end - start));

        return result;
    }
}
