package site.lhearen.ajava.base.future;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import org.junit.Test;

public class Sol_2 {
    @Test
    public void TestIdIncrementer() {
        LongAdder id_0 = new LongAdder();
        id_0.add(1L); // perform not better than AtomicLong in such case;
        // no atomic operations as AtomicLong getAndIncrement();
        // preferably used in high contention scenarios for statistics collecting;
        // based on : https://stackoverflow.com/questions/50739351/is-the-longadder-a-wrong-choice-for-the-id-generator

        // from the Doc:
        // returns the current total combined across the variables maintaining the sum.
        // But under high contention,
        // expected throughput of this class is significantly higher, at the expense of higher space consumption.

        AtomicLong id = new AtomicLong();
        id.getAndIncrement();
    }
}
