package site.lhearen.ajava.base.stream;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import static site.lhearen.ajava.mytools.constants.Constants.FILE_PATH;
import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Output.readWordsFromFilePath;

public class Sol_3 {
    @Test
    public void testLimitInParallelStream() {
        AtomicInteger counter = new AtomicInteger(0);
        Long start = System.nanoTime();
        readWordsFromFilePath(FILE_PATH).stream().parallel()
                .filter(word -> word.length() > 12)
                .forEachOrdered(word -> counter.getAndIncrement());
        out.println(counter.get());
        out.println("Time costed by parallelStream: " + (System.nanoTime() - start));
    }

    @Test
    public void testLimitInSequentialStream() {
        AtomicInteger counter = new AtomicInteger(0);
        Long start = System.nanoTime();
        readWordsFromFilePath(FILE_PATH).stream()
                .filter(word -> word.length() > 12)
                .forEachOrdered(word -> counter.getAndIncrement());
        out.println(counter.get());
        out.println("Time costed by plain Stream: " + (System.nanoTime() - start));
    }
}
