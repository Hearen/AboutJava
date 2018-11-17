package site.lhearen.ajava.base.future;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Output.readWordsFromFilePath;

public class Sol_6 {
    @Test
    public void testConcurrentHashMap() {
        File folder = new File("/Users/lhearen/programs/JavaSe8Solutions/src/test/chap1_lambda_basic");
        Map<String, Set<File>> wordFileMap = new ConcurrentHashMap<>();
        Arrays.stream(folder.listFiles()).parallel().forEach(file -> {
            out.println(file.getPath());
            out.println(Thread.currentThread().getName());
            List<String> words = readWordsFromFilePath(file.getPath());
            // benefits compared with merge:
            // 1. No need to create a new Set each time;
            // 2. No need to copy the oldSet each time;
            // 3. Much easier to use;
            words.stream().forEach(word -> wordFileMap.computeIfAbsent(word, (unused) -> new HashSet<>()).add(file));
        });
        wordFileMap.entrySet().forEach(entry -> {
            out.println("\"" + entry.getKey() + "\" in " + entry.getValue().size() + " file(s)");
        });
    }
}
