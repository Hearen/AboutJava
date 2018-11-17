package site.lhearen.ajava.base.lambda.se8_3;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_24 {
    @Test
    public void testPairFlatMap() {
        Pair<Pair<String, String>, Pair<String, String>> namePair = new Pair<>(new Pair<>("Hi", "Hearen"),
                new Pair<>("Hello", "Katherine"));
        flatMap(namePair, String::length).forEach(out::println);
        // actually this should be working only under this condition: only one type of the pair required;
        // Pair<T, U> is not supported;
        List<Pair<Pair<String, String>, Pair<String, String>>> pairs = new ArrayList<>();
        pairs.add(namePair);
        pairs.stream().flatMap(nestedPair -> flatMap(nestedPair, String::length)).forEach(out::println);
    }

    private <T, R> Stream<Pair<R, R>> flatMap(Pair<Pair<T, T>, Pair<T, T>> nestedPair, Function<T, R> mapper) {
        List<Pair<R, R>> list = new ArrayList<>();
        list.add(new Pair<>(mapper.apply(nestedPair.getKey().getKey()), mapper.apply(nestedPair.getKey().getValue())));
        list.add(new Pair<>(mapper.apply(nestedPair.getValue().getKey()), mapper.apply(nestedPair.getValue().getValue())));
        return list.stream();
    }
}
