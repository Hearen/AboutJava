package site.lhearen.ajava.base.lambda.se8_1;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_6 {
    private static Runnable uncheck(RunnalbeWithEx runnalbeWithEx) {
        return () -> {
            try {
                runnalbeWithEx.run();
            } catch (Exception ignored) {
            }
        };
    }

    @Test
    public void testExceptionWrapper() {
        new Thread(uncheck(() -> {
            out.println("Zzzzz...");
            Thread.sleep(1000);
        })).start();
    }

    @FunctionalInterface
    interface RunnalbeWithEx {
        void run() throws Exception;
    }
}
