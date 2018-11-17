package site.lhearen.ajava.base.string.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    private static Pattern pattern;
    private static Matcher matcher;
    public static int runTest(String regex, String text, int flags) {
        pattern = Pattern.compile(regex, flags);
        matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()){
            matches++;
        }
        return matches;
    }
}
