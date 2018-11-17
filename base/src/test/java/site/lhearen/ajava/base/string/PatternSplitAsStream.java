package site.lhearen.ajava.base.string;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;

public class PatternSplitAsStream {
    @Test
    public void testPattern() {
        Pattern splitter = Pattern.compile("\\s+");
        String firstWord = "they will win";
        String secondWord = "will they";
        List<String> secondWordList = splitter.splitAsStream(secondWord).collect(Collectors.toList());

        splitter.splitAsStream(firstWord)
                .filter(w -> !secondWordList.contains(w))
                .forEach(System.out::println);
    }
}
