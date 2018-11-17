package site.lhearen.ajava.base.future;

import java.util.concurrent.CompletableFuture;

import static java.lang.System.out;

public class BasicExceptionHandler {

    public static void main(String... args) {
        testHandle();
        testCompleteExceptionally();
    }

    private static void testHandle() {
        String name = null;
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        }).handle((s, t) -> s != null ? s : "Hello, Stranger!" + t.toString());
        out.println(completableFuture.join());
    }

    private static void testCompleteExceptionally() {
        String name = "Hearen";
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            delay(500L);
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        });

        if (name != null) {
            completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));
        }
        out.println(completableFuture.handle((s, t) -> s != null ? s : "Hello, Stranger!" + t.toString()).join());

    }

    private static void delay(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
}

