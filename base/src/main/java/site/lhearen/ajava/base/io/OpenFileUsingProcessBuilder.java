package site.lhearen.ajava.base.io;

import java.io.File;
import java.io.IOException;

public class OpenFileUsingProcessBuilder {
    public static void main(String... args) {
        String filePath = "t";
        openByCommand(filePath);
    }

    private static void openByCommand(String filePath){
        try {
            Process process = new ProcessBuilder("gedit", filePath)
                    .directory(new File("/home/hearen")) // set up your working directory;
                    .start();
            int exitCode = process.waitFor();
            System.out.println(exitCode);
        } catch (IOException | InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }
}
