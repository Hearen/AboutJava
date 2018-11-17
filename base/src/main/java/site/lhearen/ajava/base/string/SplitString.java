package site.lhearen.ajava.base.string;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SplitString {
    public static void main(String... args) {
        /**
         * If you want the output as you provided, try this:

         String s = "Apple Orange Mango Pineapple";
         s = Stream.of(s.split("\\s")).collect(Collectors.joining("\n"));

         Output:

         Apple
         Orange
         Mango
         Pineapple

         But instead, if you wanna retain the `\n`, try this:

         String s = "Apple Orange Mango Pineapple";
         s = s.replaceAll("\\s", "\\\\n");

         Output will be:

         Apple\nOrange\nMango\nPineapple
         */
        String s = "Apple Orange Mango Pineapple";
        s = s.replaceAll("\\s", "\\\\n");
        System.out.println(s);
        s = "Apple Orange Mango Pineapple";
        s = Stream.of(s.split("\\s")).collect(Collectors.joining("\n"));
        System.out.println(s);
//        String[] myArr = {"Bob    Marley", "Barbara    Newton", "John    Smith"};
//        splitArray(myArr);
//        splitArrayUsingStream(myArr);
    }

    private static void splitArray(String[] arr) {
        int len = arr.length;
        String[] firstNames = new String[len];
        String[] lastNames = new String[len];
        for (int i = 0; i < len; ++i) {
            String[] names = arr[i].split("\\s+");
            firstNames[i] = names[0];
            lastNames[i] = names[1];
        }
        System.out.println(Arrays.deepToString(firstNames));
        System.out.println(Arrays.deepToString(lastNames));
    }

    private static void splitArrayUsingStream(String[] arr) {
        Pair[] pairs = Arrays.stream(arr).map(s -> {
            String[] names = s.split("\\s+");
            return new Pair<>(names[0], names[1]);
        }).collect(Collectors.toList()).toArray(new Pair[arr.length]);
        System.out.println(Arrays.deepToString(pairs));
    }
}
