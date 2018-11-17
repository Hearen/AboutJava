package site.lhearen.ajava.base.stream;

import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_8 {
    @Test
    public void testZip() {
        zipStream(Stream.of(1, 2, 3, 4), Stream.of('a', 'b')).forEach(out::println);
    }

    private <T> Stream<T> zipStream(Stream<T> a, Stream<T> b) {
        Iterator<T> secondIterator = b.iterator();
        Stream.Builder<T> builder = Stream.builder();
        a.forEach(first -> {
            if (secondIterator.hasNext()) {
                builder.add(first).add(secondIterator.next());
            } else {
                a.close();
            }
        });
        return builder.build();
    }
}
