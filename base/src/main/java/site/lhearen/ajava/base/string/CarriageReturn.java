package site.lhearen.ajava.base.string;

import static java.lang.System.out;

public class CarriageReturn {
    public static void main(String... args) {
        out.println("Hello\nworld");
        out.println(String.format("Hello%n%s","world"));
    }
}
