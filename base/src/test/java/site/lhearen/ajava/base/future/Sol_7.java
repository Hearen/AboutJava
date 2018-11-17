package site.lhearen.ajava.base.future;

import javafx.util.Pair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_7 {
    @Test
    public void testMapValueLargest() {
        ConcurrentHashMap<String, Long> theMap = new ConcurrentHashMap<>();
        LongStream.range(0, 1_000_000).forEach(i -> theMap.put("Key - " + i, i));
        out.println(findLargestByAccumulator(theMap));
        out.println(findLargestByReduceEntries(theMap));
        out.println(findLargestByReduce(theMap));
    }

    private String findLargestByAccumulator(Map<String, Long> theMap) {
        AtomicReference<Map.Entry<String, Long>> atomicReference = new AtomicReference<>();
        theMap.entrySet()
                .stream()
                .parallel()
                .forEach(entry ->
                {
//                    out.println(Thread.currentThread().getName());
                    atomicReference.accumulateAndGet(entry,
                            (oldEntry, newEntry) ->
                                    oldEntry == null || newEntry.getValue() > oldEntry.getValue() ?
                                            newEntry : oldEntry);
                });
        return atomicReference.get().getKey();
    }

    private String findLargestByReduceEntries(ConcurrentHashMap<String, Long> theMap) {
        return theMap.reduceEntries(4,
                (entry1 , entry2) -> entry1.getValue() >  entry2.getValue()? entry1 : entry2 ).getKey();
    }

    private String findLargestByReduce(ConcurrentHashMap<String, Long> theMap) {
        return theMap.reduce(4, (k, v) -> new Pair<String, Long>(k, v), (p1, p2) ->
                p1.getValue() > p2.getValue() ? p1 : p2).getKey();
    }
}
