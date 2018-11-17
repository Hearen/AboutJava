package site.lhearen.ajava.base.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitNumStr {
    private final static Pattern NUM_STR_PATTERN = Pattern.compile("(\\d+)(\\w+)");
    public static void main(String... args) {
        List<String> list = new ArrayList<>(Arrays.asList("12absc", "2bbds", "abc"));
        for (String s : list) {
            System.out.println(Arrays.toString(split(s)));
        }
    }

    private static String[] split(String numStr) {
        Matcher matcher = NUM_STR_PATTERN.matcher(numStr);
        if (matcher.find()) {
            return new String[] {matcher.group(1), matcher.group(2)};
        } else return new String[] { numStr };
    }
}

