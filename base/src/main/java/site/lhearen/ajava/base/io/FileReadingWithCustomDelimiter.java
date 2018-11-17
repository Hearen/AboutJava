package site.lhearen.ajava.base.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileReadingWithCustomDelimiter {
    public static void main(String... args) {
        try (Stream<String> lines = Files.lines(Paths.get(Thread.currentThread().getContextClassLoader().getResource("csv.txt").toURI()))) {
            lines.forEach(line -> {
                String[] words = line.split("#\\$#");
                System.out.println(Arrays.toString(words));
            });
        } catch (URISyntaxException | IOException ignored) {
            ignored.printStackTrace();
        }
    }
}
