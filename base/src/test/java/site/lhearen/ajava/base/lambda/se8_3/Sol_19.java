package site.lhearen.ajava.base.lambda.se8_3;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static java.lang.System.out;
import static javax.swing.text.html.parser.DTDConstants.NAMES;

public class Sol_19 {

    // https://stackoverflow.com/questions/24308146/why-is-a-combiner-needed-for-reduce-method-that-converts-type-in-java-8#answer-24316429

    /**
     * Why combiner is required?
     * when converting to another type, accumulator is not enough since its input are two types
     * while the last combining operation requires the target type instead of target type and original type;
     *
     * Why accumulator requires U instead of "? Super U"?
     * accumulator target type acts as consumer when accumulating but producer when being combined by combiner;
     */
    @Test
    public void testReduce() {
        List<String> names = new ArrayList<>(NAMES);
        int nameTotalLen = names.stream().reduce(0, (a, s) -> a += s.length(), Integer::sum);
        out.println(nameTotalLen);
    }
}
