package site.lhearen.ajava.base.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class CreateFile {
    public static void main(String... args) {
        String content = "groups:\n" +
                "- name: textfile_collector_alert.rules\n" +
                "  rules:\n" +
                "  - alert: service_oom\n" +
                "    expr: service_oom_file == 1\n" +
                "    for: 1m\n" +
                "    labels:\n" +
                "      severity: critical\n" +
                "    annotations:\n" +
                "      description: 'Hprof files: {{ $labels.file }}. Reported by instance {{ $labels.instance\n" +
                "        }} of job {{ $labels.job }}.'\n" +
                "      summary: OOM happens";
        String absFilePath = "/tmp/testing-file-local-write.yml";
        System.out.println(writeToLocal(absFilePath, content));
    }


    public static boolean writeToLocal(String absFilePath, String content) {
        File file = new File(absFilePath);
        file.getParentFile().mkdirs();
        try {
            Files.write(file.toPath(), Arrays.asList(content));
        } catch (IOException ignored) {
            ignored.printStackTrace();
            return false;
        }
        return true;
    }
}
