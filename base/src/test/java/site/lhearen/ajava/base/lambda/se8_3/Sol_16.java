package site.lhearen.ajava.base.lambda.se8_3;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_16 {
    @Test
    public void testInOrder() {
        doInOrderAsync(() -> out.println("Before biConsumer"), (t, e) -> {
            out.println(t);
            out.println(e.toString());
        }, "The Third");
        doInOrderAsync(() -> {
            throw new IllegalStateException();
        }, (t, e) -> {
            if (t != null) {
                out.println(t);
            }
            out.println(e.toString());
        });
    }

    private <T> void doInOrderAsync(Runnable first, BiConsumer<T, Throwable> biConsumer, T third) {
        new Thread(() -> {
            first.run();
            try {
                throw new IllegalAccessException();
            } catch (IllegalAccessException e) {
                biConsumer.accept(third, e);
            }
        }).start();
    }

    private <T> void doInOrderAsync(Supplier<T> first, BiConsumer<T, Throwable> biConsumer) {
        new Thread(() -> {
            T t = null;
            try {
                t = first.get();
            } catch (Exception e) {
                biConsumer.accept(t, e);
            }
        }).start();
    }
}
