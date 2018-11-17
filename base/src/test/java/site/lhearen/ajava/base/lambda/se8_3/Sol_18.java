package site.lhearen.ajava.base.lambda.se8_3;

import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_18 {
    @Test
    public void testFunctionWithEx() {
        Function<String, Integer> getLen = uncheck((String s) -> getLength(s));
        out.println(getLen.apply("Hearen"));
        out.println(getLen.apply("Katherine"));
        out.println(uncheckWithHandler((String s) -> getLength(s),
                (e) -> out.println("Exception: " + e.getMessage()))
                .apply("Katherine"));
    }

    private Integer getLength(String s) throws RuntimeException {
        if (s.length() > 6) {
            throw new RuntimeException("Longer than I expect");
        }
        return s.length();
    }

    private <T, R> Function<T, R> uncheck(FunctionWithEx<T, R> functionWithEx) {
        return (t) -> {
            R r = null;
            try {
                r = functionWithEx.apply(t);
            } catch (Exception e) {
                out.println(e.getMessage());
            }
            return r;
        };
    }

    private <T, R> Function<T, R> uncheckWithHandler(FunctionWithEx<T, R> functionWithEx, Consumer<Throwable> handler) {
        return (t) -> {
            R r = null;
            try {
                r = functionWithEx.apply(t);
            } catch (Exception e) {
                handler.accept(e);
            }
            return r;
        };
    }


    @FunctionalInterface
    interface FunctionWithEx<T, U> {
        U apply(T t) throws Exception;
    }
}
