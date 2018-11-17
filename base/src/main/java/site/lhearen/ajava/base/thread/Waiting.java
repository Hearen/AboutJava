package site.lhearen.ajava.base.thread;

public class Waiting {
    public static void main(String... args) {
        // top -H present in thread mode
        // top -Hp <pid> present the threads in specific process;
        // jps -l locate the jvm thread id
        // jstack <thread_id>
        for (int i = 0; i < 10; ++i) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread busyThread = new Thread(() -> {
            int i = 0;
            while (true) {
                i  = (i++) % 100;
            }
        });
        busyThread.setName("Busy thread");
        busyThread.start();
    }
}
