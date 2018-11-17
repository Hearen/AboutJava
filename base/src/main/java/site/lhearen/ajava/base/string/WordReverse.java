package site.lhearen.ajava.base.string;

public class WordReverse {
    public static void main(String... args) {
        String s = " We have bananas\r" +
                "He has apples\r"  +
                "I own 3 cars\n"   +
                "*!";
        System.out.println(reverseSentenceThenWord(s));
    }

    /**
     * return itself if the @param s is null or empty;
     * @param s
     * @return the words (non-whitespace character compound) reversed string;
     */
    private static String reverseSentenceThenWord(String s) {
        if (s == null || s.length() == 0) return s;
        char[] arr = s.toCharArray();
        int len = arr.length;
        reverse(arr, 0, len - 1);
        boolean inWord = !isSpace(arr[0]); // used to track the start and end of a word;
        int start = inWord ? 0 : -1; // is the start valid?
        for (int i = 0; i < len; ++i) {
            if (!isSpace(arr[i])) {
                if (!inWord) {
                    inWord = true;
                    start = i; // just set the start index of the new word;
                }
            } else {
                if (inWord) { // from word to space, we do the reverse for the traversed word;
                    reverse(arr, start, i - 1);
                }
                inWord = false;
            }
        }
        if (inWord) reverse(arr, start, len - 1); // reverse the last word if it ends the sentence;
        String ret = new String(arr);
         ret = showWhiteSpaces(ret);
        // uncomment the line above to present all whitespace escape characters;
        return ret;
    }

    private static void reverse(char[] arr, int i, int j) {
        while (i < j) {
            char c = arr[i];
            arr[i] = arr[j];
            arr[j] = c;
            i++;
            j--;
        }
    }

    private static boolean isSpace(char c) {
        return String.valueOf(c).matches("\\s");
    }

    private static String showWhiteSpaces(String s) {
        String[] hidden = {"\t", "\n", "\f", "\r"};
        String[] show = {"\\\\t", "\\\\n", "\\\\f", "\\\\r"};
        for (int i = hidden.length - 1; i >= 0; i--) {
            s = s.replaceAll(hidden[i], show[i]);
        }
        return s;
    }
}

