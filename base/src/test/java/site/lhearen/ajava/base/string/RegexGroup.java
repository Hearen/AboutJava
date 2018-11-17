package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import static java.lang.System.out;

public class RegexGroup {
    @Test
    public void testRegxGroup() {
        Pattern cityStateCodePattern = Pattern.compile("(?<city>.+),\\s*(?<state>.+),\\s*(?<code>\\d{5,9})");
        String correct = "The Columbus, Ohio State, 232001";
        Matcher matcher = cityStateCodePattern.matcher(correct);
        if (matcher.find()) {
            // number based on the open parenthesis;
            // inconvenient;
            out.println("The State: " + matcher.group(2));
            out.println("The City: " + matcher.group(1));
            out.println("The Code: " + matcher.group(3));
        }

        correct = "The Columbus, Ohio State, 232001";
        matcher = cityStateCodePattern.matcher(correct);
        if (matcher.find()) {
            // more readable and controllable;
            out.println("The State: " + matcher.group("state"));
            out.println("The City: " + matcher.group("city"));
            out.println("The Code: " + matcher.group("code"));
        }
        String wrong = "Columbus, Ohio, 2320"; // code is wrong - at least 5 numbers;
        matcher = cityStateCodePattern.matcher(wrong);
        if (matcher.find()) {
            out.println("The State: " + matcher.group("state"));
            out.println("The City: " + matcher.group("city"));
            out.println("The Code: " + matcher.group("code"));
        }
    }
}
