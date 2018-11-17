package site.lhearen.ajava.base.string;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {
    public static void main(String... args) {

    }

    public static int runTest(String regex, String text, int flags) {
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }

    public static int runTest(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }

    @Test
    public void givenRegex_whenMatchFailsWithLiteralFlag_thenCorrect() {
        int matches = runTest("(.*)", "text", Pattern.LITERAL);

        assertFalse(matches > 0);
    }

    @Test
    public void givenRegex_whenMatchesWithLiteralFlag_thenCorrect() {
        int matches = runTest("(.*)", "text(.*)", Pattern.LITERAL);

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
                + "this is a fox");
        assertTrue(matches > 0);
    }

    @Test
    public void givenTwoSets_whenMatchesIntersection_thenCorrect() {
        int matches = runTest("[1-6&&[5-9]]", "123456789");
        assertEquals(matches, 2);
    }

    @Test
    public void givenTwoSets_whenMatchesUnion_thenCorrect() {
        int matches = runTest("[1-3[2-5]]", "123456789");
        assertEquals(matches, 5);
    }

    @Test
    public void givenSetWithSubtraction_whenMatchesAccurately_thenCorrect() {
        int matches = runTest("[0-9&&[^012468]]", "123456789");

        assertEquals(matches, 4);
    }

    @Test
    public void givenNumberRange_whenMatchesAccurately_thenCorrect2() {
        int matches = runTest("[30-35]", "Two Uppercase alphabets 34 overall");
        assertEquals(matches, 1);
    }
}
