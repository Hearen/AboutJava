package site.lhearen.ajava.base.future;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Output.readFileByWords;

public class Sol_1 {
    @Test
    public void testAtomicReference() {
        AtomicReference<String> theLongest = new AtomicReference<>("");
        List<String> words = readFileByWords();
        words.stream().parallel().forEach(word -> {
            out.println(Thread.currentThread().getName());
            out.println(Thread.currentThread().isDaemon());
            theLongest.accumulateAndGet(word, (w1, w2) -> w1.length() > w2.length() ? w1 : w2);
        });
        out.println(theLongest.get());

        IntStream.range(0, 10_000).parallel().forEach(i -> {
            out.println(Thread.currentThread().isDaemon());
            out.println(i);
        });
    }
}
