package site.lhearen.ajava.base.stream;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import static site.lhearen.ajava.mytools.constants.Constants.FILE_PATH;
import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Output.readWordsFromFilePath;

public class Sol_1 {

    @Test
    public void testParallelStream() {
        AtomicInteger counter = new AtomicInteger(0);
        readWordsFromFilePath(FILE_PATH).stream().parallel()
                .filter(word -> word.length() > 12)
                .forEach(word -> {
                    counter.getAndIncrement();
                });
        out.println(counter.get());
    }

    @Test
    public void testBasic() {
        int count = 0;
        for (String word : readWordsFromFilePath(FILE_PATH)) {
            if (word.length() > 12) {
                count++;
            }
        }
        out.println(count);
    }

}
