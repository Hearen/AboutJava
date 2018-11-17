package site.lhearen.ajava.base.lambda.se8_3;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_17 {
    @Test
    public void testDoInParallelAsync() {
        doInParallelAsync(() -> {
                        if (ThreadLocalRandom.current().nextInt(2) % 2 == 0) {
                            throw new RuntimeException("First Failed");
                        }
                    out.println("First Ok");
                },
                () -> {
                    if (ThreadLocalRandom.current().nextInt(2) % 2 == 0) {
                        throw new RuntimeException("Second Failed");
                    }
                    out.println("Second Ok");
                }, (e) -> {
                    out.println(e.getMessage());
                });
    }

    private void doInParallelAsync(Runnable first, Runnable second, Consumer<Throwable> handler) {
        Thread firstThread = new Thread(() -> {
            try {
                first.run();
            } catch (Exception e) {
                handler.accept(e);
            }
        });

        Thread secondThread = new Thread(() -> {
            try {
                second.run();
            } catch (Exception e) {
                handler.accept(e);
            }
        });
        firstThread.start();
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch(InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }


    @Test
    public void testTwoSimpleThreads() {
        doInParallelAsync(() -> {
                    out.println("First Ok");
                },
                () -> {
                    out.println("Second Ok");
                });
    }

    private void doInParallelAsync(Runnable first, Runnable second) {
        Thread firstThread = new Thread(() -> {
            first.run();
        });

        Thread secondThread = new Thread(() -> {
            second.run();
        });
        out.println(firstThread.isDaemon());
        out.println(secondThread.isDaemon());
        firstThread.start();
        secondThread.start();
    }

}
