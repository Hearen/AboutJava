package site.lhearen.ajava.base.string;

import java.util.regex.Pattern;

public class CreditCardMatching {

    private final static Pattern CREDIT_CARD_PATTERN = Pattern.compile("^4\\d+");

    public static void main(String... args) {
        String s = "4485762248404312";
        String s1 = "4485-7622-4840-4312";
        String s2 = "4485 7622 4840 4312";
        System.out.println(s.length());
        if (s1.matches("^4\\d{3}-\\d{4}-\\d{4}-\\d{4}")) {
            System.out.println("with hyphen: " + s1);
        }
        if (s2.matches("^4\\d{3}\\s*\\d{4}\\s*\\d{4}\\s*\\d{4}")) {
            System.out.println("with whitespaces: " + s2);
        }
        if (s.matches("^4\\d{3}\\s*\\d{4}\\s*\\d{4}\\s*\\d{4}")) {
            System.out.println("with whitespaces: " + s);
        }
        if (s.matches("^4\\d{12,19}")) {
            System.out.println(s);
        }
        if (CREDIT_CARD_PATTERN.matcher(s).matches()) {
            System.out.println(s);
        }
    }
}
