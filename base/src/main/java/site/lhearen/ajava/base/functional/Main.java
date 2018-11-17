package site.lhearen.ajava.base.functional;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {

    public static Integer getRandomInt(Supplier<Integer> integerSupplier) {
        return integerSupplier.get();
    }

    public static void consume(Consumer<Integer> integerConsumer) {
        Integer val = new Random().nextInt();
        System.out.println("Generated integer: " + val);
        integerConsumer.accept(val);
    }

    public static void runOnly(Runnable func) {
        System.out.println("Without parameters and returns.");
        func.run();
    }
    public static void main(String[] args) {
        System.out.println(getRandomInt(() -> new Random().nextInt()));
        consume((i) -> System.out.println("I'm integer consumer consuming: " + i));
        runOnly(() -> System.out.println("Just run without return and parameters."));
    }
}
