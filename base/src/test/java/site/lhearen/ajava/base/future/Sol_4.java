package site.lhearen.ajava.base.future;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Output.readFileByWords;

public class Sol_4 {
    @Test
    public void testLongAccumulator() {
        List<String> words = readFileByWords();
        LongAccumulator shortest = new LongAccumulator(Long::min, Long.MAX_VALUE);
        LongAccumulator longest = new LongAccumulator(Long::max, Long.MIN_VALUE);
        AtomicLong atomicLongShortest = new AtomicLong();
        AtomicLong atomicLongLongest = new AtomicLong();
        words.stream().parallel().forEach(word -> {
//            atomicLongLongest.accumulateAndGet(word.length(), Long::max);
            atomicLongLongest.updateAndGet(x -> Math.max(x, word.length()));
            atomicLongShortest.accumulateAndGet(word.length(), Long::min);
            shortest.accumulate(word.length());
            longest.accumulate(word.length());
        });
        out.println("Shortest Length: " + shortest.intValue());
        out.println("Longest Length: " + longest.intValue());
        out.println("Shortest Length: " + atomicLongShortest.get());
        out.println("Longest Length: " + atomicLongLongest.get());

        LongAccumulator smallest = new LongAccumulator(Long::min, Long.MAX_VALUE);
        AtomicLong atomicLongSmallest = new AtomicLong(Long.MAX_VALUE);
        Stream<Integer> integerStream = Stream.generate(() -> new Random().nextInt(100) + 100).limit(1000);
        integerStream.parallel().forEach(i -> {
            smallest.accumulate(i);
//            atomicLongSmallest.accumulateAndGet(i, Long::min);
            atomicLongSmallest.updateAndGet(x -> Math.min(x, i));
        });
        out.println("Smallest Num: " + smallest.get());
        out.println("Smallest Num: " + atomicLongSmallest.get());
    }
}
