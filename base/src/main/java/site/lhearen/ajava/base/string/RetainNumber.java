package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetainNumber {
    public static void main(String... args) {
        String[] ss = {"int int0 = (-953);", "int int1 = (-411);", "int int2 = 5471;", "int int3 = 823;"};
        for (String s : ss) {
            s = s.split("=")[1].replaceAll("[^-0-9]", "");
            System.out.println(s);
        }

        Pattern pattern = Pattern.compile(".*?(-?\\d+)\\D+$"); // non-greedy used; using greedy here will be wrong;
        for (String s: ss) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                System.out.println(matcher.group(1));
            }
        }
    }
}
