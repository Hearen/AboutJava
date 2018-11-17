package site.lhearen.ajava.base.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.lang.System.out;

public class Sol_9 {
    public static void main(String... args) {
        testScanner();
        testBufferedReader();
    }

    private static void testScanner() {
        Scanner reader = new Scanner(System.in);
        out.println("Input a string: ");
        String s = reader.nextLine();
        out.println("String: " + s);
        out.println("Input an integer: ");
        int a = reader.nextInt();
        out.println("Integer: " + a);
        out.println("Input a double: ");
        double d = reader.nextDouble();
        out.println("Double: " + d);
    }

    private static void testBufferedReader() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            out.println("Input a string: ");
            String s = reader.readLine();
            out.println("String: " + s);
            out.println("Input an integer: ");
            int a = Integer.valueOf(reader.readLine());
            out.println("Integer: " + a);
            double d = Double.valueOf(reader.readLine());
            out.println("Double: " + d);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }
}
