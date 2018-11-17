package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRange {
    public static void main(String... args) {
        String s = "abc abc abc xyz xyz xyz";
        Pattern pattern = Pattern.compile("(abc\\s+\\w+\\s+xyz)");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            s = s.substring(matcher.start() + 1); // ignore the just-matched and move on;
            matcher = pattern.matcher(s);
        }
    }
}


/*

Perhaps you need some modifications while matching:

    public static void main(String... args) {
        String s = "abc abc abc xyz xyz xyz";
        Pattern pattern = Pattern.compile("(abc\\s+\\w+\\s+xyz)");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            s = s.substring(matcher.start() + 1); // ignore the just-matched and move on;
            matcher = pattern.matcher(s);
        }
    }

Output:

    abc abc xyz
    abc xyz xyz
 */