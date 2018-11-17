package site.lhearen.ajava.base.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CircularDeadLock {
    private static int LOCK_COUNT = 4;
    private static List<Object> Shared_Locks = new ArrayList<>(LOCK_COUNT);
    private static volatile int StartIndex = 0;

    public static void main(String... args) {
        IntStream.range(0, LOCK_COUNT).forEach((i) -> Shared_Locks.add(new Object()));
        IntStream.range(0, 5).forEach((t) -> {
            for (int i = 0; i < LOCK_COUNT; ++i) {
                Thread newThread = new Thread(() -> startFromLock());
                newThread.start();
                StartIndex++;
            }
        });
    }

    private static void startFromLock() {
        int startIndex = StartIndex;
        nestedLock(startIndex, startIndex);
    }

    private static void nestedLock(int startIndex, int theStart) {
        if (startIndex % LOCK_COUNT == theStart) {
            return;
        }
        synchronized (Shared_Locks.get((startIndex) % LOCK_COUNT)) {
            try {
                Thread.sleep(250);
                System.out.println(Thread.currentThread().getName() + " is locking at " + (startIndex % LOCK_COUNT));
                nestedLock(startIndex + 1, theStart);
            } catch (InterruptedException ignored) {

            }
        }
    }
}
