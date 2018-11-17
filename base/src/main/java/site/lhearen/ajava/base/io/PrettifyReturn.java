package site.lhearen.ajava.base.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class PrettifyReturn {
    public static void main(String... args) {
        try {
            File logFile = new File("/home/hearen/auto-generator.log");
            List<String> logs = Files.readAllLines(logFile.toPath());
            logs.stream().map(PrettifyReturn::prettifyLine).forEach(System.out::println);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    public static String prettifyLine(String raw) {
        raw = raw.replaceAll("\\\\r", "");
        raw = raw.replaceAll("\\\\n", "\n");
        return raw;
    }
}
