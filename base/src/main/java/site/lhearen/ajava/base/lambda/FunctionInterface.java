package site.lhearen.ajava.base.lambda;

import java.util.function.Function;

public class FunctionInterface {
    public static void main(String... args) {
        getT(1, String::valueOf);
        getT(1, A::valueOf);
    }

    private static <T> T getT(int i, Function<Integer, T> converter) {
        return converter.apply(i);
    }

    static class Base {

    }

    static class A{
        int val;
        public static A valueOf(int i) {
            return new A(i);
        }

        public A(int i) {
            this.val = i;
        }
    }

    static class B {
        int val;
        public static B valueOf(int i) {
            return new B(i);
        }

        public B(int i) {
            this.val = i;
        }
    }
}
