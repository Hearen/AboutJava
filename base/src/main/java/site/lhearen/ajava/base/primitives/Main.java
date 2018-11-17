package site.lhearen.ajava.base.primitives;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Main {
    public final static boolean IS_NOT_CORRECT = false;
    private final boolean isCorrect; // just initialized for only once;

    public Main(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    private static void testFloat() {
        out.println("Testing basic float usage...............");
        float f = 0.125F;
        out.println(f);

        double d = 0.125; // default double;
        out.println(d);

        double d1 = 0.125D; // the same;
        out.println(d1);

        double d2 = 0x1.0P-3; // 0.125; why p here?
        out.println(d2);
        out.println("The end of float double test..............");
        out.println();
    }

    private static void testInt() {
        out.println("Testing basic int usage...............");
        int i = 17;
        out.println(i);

        long l = 17L; // using uppercase L;
        out.println(l);

        int io = 021; // not recommended;
        out.println(io);

        int ih = 0x11;
        out.println(ih);

        int ib = 0b10001;
        out.println(ib);

        long ll = 1_000_000L;
        out.println(ll);

        long llb = 0b11_110_100_001_001_000_000;
        out.println(llb);
        out.println(Long.toBinaryString(llb));

        out.println("The end of int test..............");
        out.println();
    }

    static void testBitManipulation() {
        int base = 0B1_111;
        if ((base & 1) == 1) {
            out.println("First bit is one!");
        }
        out.println();
    }

    static void testString() {
        String hello = String.join("^", "H", "i");
        out.println(hello);

        final String s = "This string might be shared!";
        out.println(s);

        if (hello.equals("H^i")) {
            out.println("Using equals or equalsIgnoreCase to compare two strings.");
        }

        if (hello != "H^i") {
            out.println("\"==\" is used to compare reference instead of the value itself.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        Stream.iterate(0, i -> i+2).limit(5).forEach( i -> stringBuilder.append(i).append("-") );
        out.println(stringBuilder);
        out.println("Using StringBuilder to avoid re-creating a temporary string when appending");
        out.println();
    }

    private static void testPrimitiveControl() {
        int count = 0;
        test_label:
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 100; ++j) {
                for (int k = 0; k < 100; ++k) {
                    count++;
                    if (count > 5) {
                        break test_label;
                    }
                }
            }
        }
        out.println("Breaking in a nested loop via label: " + count);
        out.println();
    }

    private static void testArray() {
        int[] arr0 = new int[10];
        out.println(Arrays.toString(arr0));
        boolean[] bools0 = new boolean[10];
        out.println(Arrays.toString(bools0));
        Object[] objs0 = new Object[10];
        out.println(Arrays.toString(objs0));
        out.println("Default values should be 0, false and null of array.");

        Arrays.fill(arr0, 7);
        out.println(Arrays.toString(arr0));

        int[] arr1 = new int[10];
        Arrays.fill(arr1, 7);
        if (Arrays.equals(arr0, arr1)) {
            out.println("Directly comparing too arrays.");
        }

        for (int i = 0; i < arr0.length; ++i) {
            arr0[i] = Math.abs(new Random().nextInt()) % 1000;
        }
        Arrays.sort(arr0);
        out.println(Arrays.toString(arr0));
      
        int[][] nestedArr = new int[2][];
        nestedArr[0] = new int[]{1, 2, 3};
        nestedArr[1] = new int[]{4, 5, 6, 7};
        for(int[] arr: nestedArr) {
            for(int a: arr) {
                out.print(a);
            }
        }
        out.println();
        out.println(Arrays.deepToString(nestedArr));
        out.println();
    }

    public void sayHi() {
        if (this.isCorrect) {
            out.println("I'm correctly initialized!");
        }
        out.println("Hello, welcome to my world!");
        out.println();
    }

    public static void main(String[] args) {
        testFloat();
        testInt();
        testBitManipulation();
        testString();
        testPrimitiveControl();
        testArray();
    }
}
