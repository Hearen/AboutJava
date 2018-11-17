package site.lhearen.ajava.base.lambda;

import java.util.function.Consumer;

public class CastToFunctionalInterface {
    public static void main(String... args) {
        ((Consumer<Integer>) CastToFunctionalInterface::consumeInteger)
                .andThen(CastToFunctionalInterface::consumeAnotherInteger)
                .accept(10);
    }

    private static void consumeInteger(Integer a) {
        System.out.println("I'm an Integer: " + a);
    }

    private static void consumeAnotherInteger(Integer b) {
        System.out.println("I'm another integer: " + b);
    }
}
