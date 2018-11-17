package site.lhearen.ajava.base.string;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Byte2Character {
    public static void main(String[] args) {
        byte[] bytes = "12        13        14".getBytes();
        System.out.println(Arrays.toString(bytes));
        String str = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(str);
    }
}
