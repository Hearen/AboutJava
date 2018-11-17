package site.lhearen.ajava.base.thread;

public class TestThreadCountLimit {
    private static Object s = new Object();
    private static int count = 0;

    public static void main(String[] argv) {
        for (int i = 0; i < 10000 ; ++i) {
            Thread thread = new Thread(() -> {
                synchronized (s) {
                    count += 1;
                    System.err.println("New thread #" + count);
                }
                for (; ; ) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }
}
