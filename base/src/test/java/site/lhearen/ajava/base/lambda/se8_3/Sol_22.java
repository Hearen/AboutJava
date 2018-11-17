package site.lhearen.ajava.base.lambda.se8_3;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Sol_22 {
    @Test
    public void testCompletableFutureFlatMap() {
        CompletableFuture<Integer> intFuture = CompletableFuture.supplyAsync(() -> 1)
                .thenCompose(a -> CompletableFuture.supplyAsync(() -> a + 1));
        // works as a map resulting in nested CompletableFuture;
//        intFuture.thenApply(a -> CompletableFuture.supplyAsync(() -> a + 1));
        assertEquals(intFuture.join().intValue(), 2);
    }
}
