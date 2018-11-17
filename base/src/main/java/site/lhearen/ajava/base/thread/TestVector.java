package site.lhearen.ajava.base.thread;

import java.util.Vector;

public class TestVector {
    private static final int VECTOR_SIZE = 10;
    private static final int THREAD_MAX = 20;
    private static Vector<Integer> v = new Vector<>();

    public static void main(String...args) {
        while (true) {
            for (int i = 0; i < VECTOR_SIZE; ++i) {
                v.add(i);
            }
            Thread removeThread = new Thread(() -> {
                for (int i = 0; i < v.size(); ++i) {
                    v.remove(i);
                }
            });

            Thread getThread = new Thread(() -> {
                for (int i = 0; i < v.size(); ++i) {
                    System.out.println(v.get(i));
                }
            });

            removeThread.start();
            getThread.start();

            while (Thread.activeCount() > THREAD_MAX) ;

            if (Thread.activeCount() > THREAD_MAX) {
                break;
            }
        }
    }
}
