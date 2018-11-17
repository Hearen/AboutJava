package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegxReplace {
    public static void main(String... args) {
        String str2 = "/EMOTIONS_TAX/29027000/Points Of Interest/totem,"
        + "/EMOTIONS_TAX/29044000/Places/Italia,"
        + "/EMOTIONS_TAX/29027000/Military Equipment";

//        str2 = str2.replaceAll(replaceAll"\\d+", "22");
//        System.out.println(str2);
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str2);
        while(matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
