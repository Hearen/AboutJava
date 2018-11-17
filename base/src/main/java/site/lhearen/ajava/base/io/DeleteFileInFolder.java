package site.lhearen.ajava.base.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteFileInFolder {
    public static void main(String... args) {
        String folderAbsPath = "/tmp/prometheus/rules/jillk_develop_multi-tenant-single/";
        String filePrefix = "cassandra";
        File folder = new File(folderAbsPath);
        File[] files = folder.listFiles();
        try {
            for (File file : files) {
                if (file.getName().startsWith(filePrefix)) {
                    System.out.println("Locate the file file by prefix: " + filePrefix + " : " + file.getName());
                    Files.delete(Paths.get(file.getAbsolutePath()));
                }
            }
        } catch(IOException ignored) {
            ignored.printStackTrace();
        }
    }
}
