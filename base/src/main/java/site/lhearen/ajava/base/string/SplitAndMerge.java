package site.lhearen.ajava.base.string;

import java.util.ArrayList;
import java.util.List;

public class SplitAndMerge {
    public static void main(String... args) {
        String line = "account123,2222,Thnaks for reaching out,\"Hey [[customerFirstName]], Thanks for reaching out to us.\",\"Hey [[customerFirstName]], Thanks for reaching out to us.\"";
        for (String s : splitByComma(line)) {
            System.out.println(s);
        }
    }

    private static List<String> splitByComma(String line) {
        String[] words = line.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < words.length; ++i) {
            if (words[i].startsWith("\"")) { // collect from the start of the cell;
                String s = words[i].substring(1);
                while (i < words.length - 1) {
                    s += "," + words[++i].substring(0, words[i].length() - 1);
                    if (words[i++].endsWith("\"")) break; // jump out of the cell after the closing double quotes;
                }
                list.add(s);
                i--;
            } else {
                list.add(words[i]);
            }
        }
        return list;
    }
}
