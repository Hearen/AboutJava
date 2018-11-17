package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegixFind {
    public static void main(String... args) {
        String input = "this is a sentence continents=Asia end continents=Europe end continents=Africa end " +
                "continents=Australia end continents=South America end continents=North America end continents=Antartica end";
        Pattern pattern = Pattern.compile("(continents)=(.+?) end");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(matcher.group(1) + ": " + matcher.group(2));
        }
    }
}
