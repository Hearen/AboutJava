package site.lhearen.ajava.mytools.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ExceptionUtil {
    // https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
    public static <T, R> Function<T, R> exWrapper(FunctionWithEx<T, R> functionWithEx) {
        return (T t) -> {
            R r = null;
            try {
                r = functionWithEx.apply(t);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            return r;
        };
    }


    public static <T> Supplier<T> exWrapper(SupplierWithEx<T> supplierWithEx) {
        return () -> {
            T t = null;
            try {
                t = supplierWithEx.get();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            return t;
        };
    }

    public static Runnable exWrapper(RunnableWithEx runnableWithEx) {
        return () -> {
            try {
                runnableWithEx.run();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        };
    }

    public static <T> Consumer<T> exWrapper(ConsumerWithEx<T> consumerWithEx) {
        return (T t) -> {
            try {
                consumerWithEx.accept(t);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        };
    }

    public static <T> Predicate<T> exWrapper(PredicateWithEx<T> predicateWithEx) {
        return (T t) -> {
            boolean ret = true;
            try {
                ret = predicateWithEx.test(t);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
            return ret;
        };
    }

    @FunctionalInterface
    public interface SupplierWithEx<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface FunctionWithEx<T, R> {
        R apply(T t) throws Exception;
    }

    @FunctionalInterface
    public interface RunnableWithEx {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface ConsumerWithEx<T> {
        void accept(T t) throws Exception;
    }

    @FunctionalInterface
    public interface PredicateWithEx<T> {
        boolean test(T t) throws Exception;
    }
}

