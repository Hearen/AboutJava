package site.lhearen.ajava.base.lambda.se8_3;

import java.util.*;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_7 {
    @Test
    public void testComparator() {
        List<String> wordList = new ArrayList<>(Arrays.asList("aa a", " ccc ", "BBB"));
        wordList.sort(getComparator(EnumSet.of(OrderTypeEnum.REVERSE, OrderTypeEnum.CASE_INSENSITIVITY,
                OrderTypeEnum.SPACE_INSENSITIVITY)));
        wordList.forEach(out::println);
        wordList.sort(getComparator(EnumSet.of(OrderTypeEnum.REVERSE, OrderTypeEnum.CASE_INSENSITIVITY)));
        wordList.forEach(out::println);
        wordList.sort(getComparator(EnumSet.of(OrderTypeEnum.SPACE_INSENSITIVITY)));
        wordList.forEach(out::println);
    }

    private Comparator<String> getComparator(EnumSet enumSet) {
        return (s1, s2) -> {
            if (enumSet.contains(OrderTypeEnum.CASE_INSENSITIVITY)) {
                s1 = s1.toLowerCase();
                s2 = s2.toLowerCase();
            }
            if(enumSet.contains(OrderTypeEnum.SPACE_INSENSITIVITY)) {
                s1 = s1.replaceAll("\\s", "");
                s2 = s2.replaceAll("\\s", "");
            }
            if (enumSet.contains(OrderTypeEnum.REVERSE)) {
                return s2.compareTo(s1);
            }
            return s1.compareTo(s2);
        };
    }

    enum OrderTypeEnum {
        REVERSE,
        CASE_INSENSITIVITY,
        SPACE_INSENSITIVITY;
    }
}
