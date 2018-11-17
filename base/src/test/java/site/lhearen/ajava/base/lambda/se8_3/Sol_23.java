package site.lhearen.ajava.base.lambda.se8_3;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_23 {
    @Test
    public void testPairMap() {
        Pair<String, String> namePair = new Pair<>("Hearen", "Katherine");
        out.println(map(namePair, String::length));
        List<Pair<String, String>> pairs = new ArrayList<>(Arrays.asList(new Pair<>("Hi", "You"),
                new Pair<>("Yes", "Me?")));
        pairs.stream().map(pair -> map(pair, String::length)).forEach(out::println);
    }

    private <T, R> Pair<R, R> map(Pair<T, T> pair, Function<T, R> mapper) {
        return new Pair<>(mapper.apply(pair.getKey()), mapper.apply(pair.getValue()));
    }
}
