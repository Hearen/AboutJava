package site.lhearen.ajava.base.snippets;

import java.util.concurrent.locks.ReentrantLock;

public class TestThreadState {
    public static void main(String... args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println(System.currentTimeMillis());
                try {
                    System.out.println(Thread.currentThread().getState());
                    Thread.sleep(1_00);
                    System.out.println(Thread.currentThread().getState());
                    if (System.currentTimeMillis() < 100) break;
                } catch (InterruptedException ignored) {
                    ignored.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println(thread.getState());
        thread.join();
        System.out.println(thread.getState());
        System.out.println("Done");
    }
}
