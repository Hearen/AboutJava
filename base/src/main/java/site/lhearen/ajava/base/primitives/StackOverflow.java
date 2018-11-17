package site.lhearen.ajava.base.primitives;

import static java.lang.System.out;

public class StackOverflow {
    private static int index = 0;
    static void recursion(int i) {
        out.println(Thread.currentThread().getName());
        out.println("The number: " + i);
        if (i == 0) {
            out.println("************** The end ***************");
        } else {
            recursion(++i);
        }
    } // default stack size: 5399
    // -Xss10M: 120_000

    static void recursion_0() {
        out.println(Thread.currentThread().getName());
        out.println(index++);
        recursion_0();
    } // 8000
    public static void main(String...args) {
//        recursion(1);
        recursion_0();
    }
}
