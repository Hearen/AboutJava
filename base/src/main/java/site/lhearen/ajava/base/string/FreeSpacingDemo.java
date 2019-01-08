package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FreeSpacingDemo {
    public static void main(String[] args) {
        String re = "(?x)"
                + "# (?x) is the free-spacing flag\n"
                + "#anything here between the first and last will be ignored\n"
                + "#in free-spacing mode, whitespace between regular expression tokens is ignored\n"
                + "(19|20\\d\\d)       # year (group 1)\n"
                + "[-/\\.]             # separator\n"
                + "(\\d{2})            # month (group 2)\n"
                + "[-/\\.]             # separator\n"
                + "(\\d{2})            # day (group 3)";
        Pattern pattern = Pattern.compile(re);
        Stream.of("2018-12-07", "2018.12.07", "2018/12/07").forEach(aTest -> {
            System.out.println("**************** Testing: " + aTest);
            final Matcher matcher = pattern.matcher(aTest);
            if (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    System.out.println("Group - " + i + ": " + matcher.group(i));
                }
            }
        });
    }
}
