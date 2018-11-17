package site.lhearen.ajava.base.thread;

import static java.lang.System.out;

public class NewThread {
    public static void main(String...args) {
        Thread newThread = new Thread(() -> {
            out.println(Thread.currentThread().getName());
            out.println(Thread.currentThread().getState());
        });
        out.println("Newly created thread");
        out.println(newThread.getState());
        newThread.start();
    }
}
