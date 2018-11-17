package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlRegex {
    public static void main(String... args) {
        String msgs="<InfoStart>\r\n"
                + "id:1234\r\n"
                + "phone:912119882\r\n"
                + "info_type:1\r\n"
                + "<InfoEnd>\r\n"
                +"<InfoStart>\r\n"
                + "id:5678\r\n"
                + "phone:912119881\r\n"
                + "info_type:1\r\n"
                + "<InfoEnd>\r\n";
        Pattern xmlPattern = Pattern.compile("<InfoStart>\\s+id:(\\d+)\\s+phone:(\\d+)\\s+info_type:(\\d+)\\s+<InfoEnd>");
        Matcher matcher = xmlPattern.matcher(msgs);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }
    }
}
