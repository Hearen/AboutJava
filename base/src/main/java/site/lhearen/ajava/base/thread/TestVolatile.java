package site.lhearen.ajava.base.thread;

public class TestVolatile {
    private static volatile int race = 0;
//    private static AtomicInteger atomicRace = new AtomicInteger(0);

    private static void inc() {
//        synchronized (TestVolatile.class) { // volatile is not enough to ensure synchronisation;
            race++;
//        }
//        atomicRace.getAndIncrement();
    }

    private static int getRace() {
        return race;
//        return atomicRace.get();
    }

    private static final int THREAD_COUNT = 20;
    private static final int LOOP_SIZE = 1_000;

    public static void main(String...args) {
        for (int i = 0; i < THREAD_COUNT; ++i) {
            new Thread(() -> {
                for (int j = 0; j < LOOP_SIZE; ++j) {
                    TestVolatile.inc();
                }
            }).start();
        }

        while (Thread.activeCount() > 1) {
            Thread.yield();
        }

        System.out.println(getRace());
        System.out.println("Expected Result: " + (THREAD_COUNT * LOOP_SIZE));
    }
}
