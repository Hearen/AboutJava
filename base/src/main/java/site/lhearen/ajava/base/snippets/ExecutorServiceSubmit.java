package site.lhearen.ajava.base.snippets;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceSubmit {
    public static void main(String... args) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 8);

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            executor.submit(ExecutorServiceSubmit::parseInParallel);
        }
        System.out.println("I'm here now...");
    }
    private static void parseInParallel() {
        while (true) {
            //do heavy lisfting;
            System.out.println("Hi");
            if (System.nanoTime() < 100) break;
        }
    }
}
