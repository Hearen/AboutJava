package site.lhearen.ajava.base.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteFolder {
    public static void main(String... args) {
        String folderAbsPath = "/tmp/prometheus/rules/jillk_develop_multi-tenant-single/";
        System.out.println(deleteFolder(folderAbsPath));
        System.out.println("Folder: " + folderAbsPath + " still exists: " + Files.exists(Paths.get(folderAbsPath)));
    }

    public static boolean deleteFolder(String folderAbsPath) {
        File folder = new File(folderAbsPath);
        for (File file: folder.listFiles()) {
            if (file.isDirectory()) {
                return deleteFolder(file.getAbsolutePath());
            } else if (file.isFile()) {
                try {
                    System.out.println("Deleting file: " + file.getAbsolutePath());
                    Files.delete(Paths.get(file.getAbsolutePath()));
                } catch(IOException ignored) {
                    return false;
                }
            }
        }
        try {
            System.out.println("Deleting folder: " + folderAbsPath);
            Files.delete(Paths.get(folderAbsPath));
        } catch(IOException ignored) {
            return false;
        }
        return true;
    }
}
