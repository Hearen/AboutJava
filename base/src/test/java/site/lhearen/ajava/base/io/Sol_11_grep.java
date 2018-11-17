package site.lhearen.ajava.base.io;

import site.lhearen.ajava.mytools.util.ExceptionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Test;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public class Sol_11_grep {
    @Test
    public void testRecursiveGrep() {
        String curPath = Paths.get("").toAbsolutePath().toString();
        recursiveGrepTo("\\s*@Test\\s*", curPath + "/src/test", curPath + "/src/test/chap8_others/filtered.txt");
    }

    private void recursiveGrepTo(String patternStr, String rootPath, String outputPath) {
        Pattern pattern = Pattern.compile(patternStr);
        Map<String, List<String>> filteredFilMap = new HashMap<>();
        try (Stream<Path> paths = Files.walk(Paths.get(rootPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(ExceptionUtil.exWrapper((Path path) -> {
                                Files.lines(path, StandardCharsets.UTF_8)
                                        .filter(s -> pattern.matcher(s).matches())
                                        .forEach(ExceptionUtil.exWrapper((String s) -> {
                                                    filteredFilMap.computeIfAbsent(path.toAbsolutePath().toString(),
                                                            k -> new ArrayList<>()).add(s);
                                                }
                                        ));
                            }));
            out.println(filteredFilMap);
            Files.write(Paths.get(outputPath), filteredFilMap.entrySet().stream()
                    .map(entry -> entry.getKey() + ":" + entry.getValue()).collect(toList()));
//            List<String> filtered = paths.filter(path -> Files.isRegularFile(path))
//                    .flatMap(ExceptionUtil.exWrapper((Path path) -> Files.lines(path, StandardCharsets.UTF_8)))
//                    .filter(s -> pattern.matcher(s).matches())
//                    .collect(toList());
//            out.println(filtered);
//            Files.write(Paths.get(outputPath), filtered);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }
}
