package site.lhearen.ajava.base.lambda.se8_1;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArraySet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Sol_1 {
    @Test
    public void testSort() {
        Integer[] arr = new Random().ints(5).boxed().toArray(Integer[]::new);
        CopyOnWriteArraySet<String> threadNames = new CopyOnWriteArraySet<>();
        threadNames.add(Thread.currentThread().getName());
        Arrays.sort(arr, (a, b) -> {
            threadNames.add(Thread.currentThread().getName());
            return Integer.valueOf(a).compareTo(b);
        });
        System.out.println("okayokayokay");
        assertEquals(threadNames.size(), 1);
    }
}
