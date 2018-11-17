package site.lhearen.ajava.base.lambda.se8_3;

import java.util.List;
import java.util.function.Function;

import org.junit.Test;

import static site.lhearen.ajava.mytools.constants.Constants.NAMES;
import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public class Sol_20 {
    @Test
    public void testStaticMap() {
        Sol_20.map(NAMES, s -> s.length()).stream().forEach(out::println);
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> converter) {
        return list.stream().map(t -> converter.apply(t)).collect(toList());
    }
}
