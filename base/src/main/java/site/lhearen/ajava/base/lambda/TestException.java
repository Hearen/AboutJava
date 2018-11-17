package site.lhearen.ajava.base.lambda;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TestException {
    // reference: http://www.baeldung.com/java-lambda-exceptions
    static <T, E extends Exception> Consumer<T>
    consumerWrapper(Consumer<T> consumer, Class<E> clazz) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw ex;
                }
            }
        };
    }
    public static void testUnchecked() {
        List<Integer> integers = Arrays.asList(3, 0, 7, 6, 10, 20);
        integers.forEach(
                consumerWrapper(
                        i -> System.out.println(50 / i),
                        ArithmeticException.class));
    }


    static void writeToFile(Integer integer) throws IOException {
        // logic to write to file which throws IOException
    }

    public static void testChecked() {
        List<Integer> integers = Arrays.asList(3, 9, 7, 0, 10, 20);
        integers.forEach(handlingConsumerWrapper(
                i -> writeToFile(i), IOException.class));
    }

    static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    System.err.println(
                            "Exception occured : " + exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }
}
