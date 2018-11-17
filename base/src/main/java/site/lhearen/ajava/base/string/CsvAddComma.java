package site.lhearen.ajava.base.string;

import java.util.ArrayList;
import java.util.List;

public class CsvAddComma {
    public static void main(String... args) {
        List<String> list = new ArrayList<>();
        list.add("quick");
        list.add("over,the,lazy,dog");
        list.add("Venture \"Extended Edition, Very Large\"");
        list.stream()
                .map(s -> convertToCsvFormat(s))
                .forEach(System.out::println);
    }

    private static String convertToCsvFormat(String input) {
        if (input.contains("\"")) {
            input = input.replaceAll("\"", "\"\"");
        }
        if (input.contains(",")) {
            input = String.format("\"%s\"", input);
        }
        return input;
    }
}
