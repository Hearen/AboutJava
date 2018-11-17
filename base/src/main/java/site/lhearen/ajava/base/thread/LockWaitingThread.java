package site.lhearen.ajava.base.thread;

public class LockWaitingThread {
    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testLockThread");
        thread.start();
    }

    public static void main(String... args) {
        for (int i = 0; i < 10; ++i) {
            createLockThread(new Object());
        }
    }
}
