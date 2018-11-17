package site.lhearen.ajava.base.snippets;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumerExecutorService {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>(20);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Runnable producerTask = () -> {
            try {
                int value = 0;
                while (true) {
                    blockingQueue.put(value);
                    System.out.println("Produced " + value);
                    value++;
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable consumerTask = () -> {
            try {
                while (true) {
                    int value = blockingQueue.take();
                    System.out.println("Consume " + value);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        executor.execute(producerTask);
        executor.execute(producerTask);
        executor.execute(consumerTask);
        executor.shutdown();
        while (0 < 1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                ignored.printStackTrace();
            }
            System.out.println(blockingQueue.size());
        }
    }

}
