package site.lhearen.ajava.base.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.ExceptionUtil.exWrapper;

public class Sol_10_file_walk {
    @Test
    public void testFileTraverse() {
        testWalk("/home/hearen/git/personal/Java-Demos");
    }

    private void testWalk(String rootPath) {
        try (Stream<Path> entries = Files.walk(Paths.get(rootPath)).onClose(() -> out.println("I am closing..."))) {
            entries.filter(path -> !Files.isDirectory(path) && path.toString().endsWith("java"))
                    .filter(exWrapper((Path path) -> Files.lines(path, StandardCharsets.UTF_8)
                            .anyMatch(line -> line.contains("transient")
                                    || line.contains("volatile")
                                    || line.contains("synchronized")))).forEach(out::println);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        // there is a readAllLines to use while lines only read as required;
    }
}
