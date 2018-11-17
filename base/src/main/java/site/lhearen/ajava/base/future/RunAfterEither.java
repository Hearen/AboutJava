package site.lhearen.ajava.base.future;

import site.lhearen.ajava.mytools.util.Util;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class RunAfterEither {
    public static void main(String...args) {
        CompletableFuture<Void> a = CompletableFuture.runAsync(() -> {
            Util.delay(new Random().nextInt(1000));
            System.out.println("Hello ");
        });

        a = a.runAfterEither(CompletableFuture.runAsync(() -> {
            Util.delay(new Random().nextInt(1000));
            System.out.println("Hi ");
        }), () -> {
            System.out.println("world"); // your c();
        });

        a.join();
    }
}
