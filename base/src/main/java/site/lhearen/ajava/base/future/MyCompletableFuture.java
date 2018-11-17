package site.lhearen.ajava.base.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

class MyCompletableFuture<T> extends CompletableFuture<T> {

    public <U> CompletableFuture<U> handleAndCompose(BiFunction<? super T, Throwable,
                ? extends CompletableFuture<U>> fn) {
        return super.handle(fn).thenCompose(x -> x);
    }

}


