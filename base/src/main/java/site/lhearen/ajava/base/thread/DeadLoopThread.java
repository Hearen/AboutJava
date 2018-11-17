package site.lhearen.ajava.base.thread;

import java.util.Random;
import java.util.concurrent.Callable;

public class DeadLoopThread {
    public static void createBusyThread() {
        Thread thread = new Thread(() -> {
            while (true) { // infinite loop without checkstyle warning;
                if (new Random().nextInt(100) > 100) {
                    break;
                }
            }
        }, "testBusyThread");
        thread.start();
    }

    public static void main(String... args) {
        createBusyThread();
        Callable<Void> callable = () -> {
            Thread.sleep(1000);
            System.out.println("hi");
            return null;
        };
    }

}
