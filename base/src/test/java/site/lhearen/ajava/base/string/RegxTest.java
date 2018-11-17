package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static site.lhearen.ajava.base.string.util.Helper.runTest;

public class RegxTest {

    /**
     * http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
     * https://www.tutorialspoint.com/java/java_regular_expressions.htm
     * https://medium.com/factory-mind/regex-tutorial-a-simple-cheatsheet-by-examples-649dc1c3f285
     * http://www.rexegg.com/regex-uses.html
     */

    @Test
    public void givenRegexWithCanonEq_whenMatchesOnEquivalentUnicode_thenCorrect() {
        int matches = runTest("\u00E9", "\u0065\u0301", Pattern.CANON_EQ); // enable canonical equivalence;
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithCaseInsensitiveMatcher_whenMatchesOnDifferentCases_thenCorrect() {
        int matches = runTest("dog", "This is a Dog", Pattern.CASE_INSENSITIVE);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithFlag_thenCorrect() {
        int matches = runTest("dog$  #check end of text","This is a dog", Pattern.COMMENTS); // allow comments in pattern;
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegexWithComments_whenMatchesWithEmbeddedFlag_thenCorrect() {
        int matches = runTest("(?x)dog$  #check end of text", "This is a dog", Pattern.COMMENTS); // allow comments in pattern;
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithMultilineFlag_thenCorrect() {
        int matches = runTest("dog$", "This is a dog" + System.getProperty("line.separator")
                + "this is a fox", Pattern.MULTILINE);
        assertTrue(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithEmbeddedMultilineFlag_thenCorrect() {
        int matches = runTest("(?m)dog$", "This is a dog" + System.getProperty("line.separator")
                + "this is a fox", Pattern.MULTILINE);
        assertTrue(matches > 0);
    }
    @Test
    public void givenMatch_whenGetsIndices_thenCorrect() {
        Pattern pattern = Pattern.compile("dog");
        Matcher matcher = pattern.matcher("This dog is mine");
        matcher.find();
        // the start and end indices of the match;
        assertEquals(5, matcher.start());
        assertEquals(8, matcher.end());
    }

    @Test
    public void whenStudyMethodsWork_thenCorrect() {
        Pattern pattern = Pattern.compile("dog");
        Matcher matcher = pattern.matcher("dogs are friendly");
        // matches requires the entire input sequence to be matched, while lookingAt does not.
        assertTrue(matcher.lookingAt());
        assertFalse(matcher.matches());
    }


}
