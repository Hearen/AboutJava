package site.lhearen.ajava.base.lambda.se8_1;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_7 {
    private static Runnable andThen(Runnable a, Runnable b) {
        return () -> {
            a.run();
            b.run();
        };
    }

    @Test
    public void testLambda() {
        Runnable c = andThen(() -> out.println("Hello, "), () -> out.println("world."));
        c.run();
    }
}
