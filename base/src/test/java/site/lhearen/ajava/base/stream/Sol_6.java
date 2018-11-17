package site.lhearen.ajava.base.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;

public class Sol_6 {
    @Test
    public void testCharacterStream() {
        characterStreamNew("HELLO").forEach(out::println);
        characterStreamBasic("HELLO").forEach(out::println);
    }

    private Stream<Character> characterStreamNew(String word) {
        return IntStream.range(0, word.length())
                .mapToObj(i -> word.charAt(i));
//      return word.chars().mapToObj(c -> (char) c);
    }

    private Stream<Character> characterStreamBasic(String word) {
        List<Character> result = new ArrayList<>();
        for (Character c : word.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }
}
