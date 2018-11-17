package site.lhearen.ajava.base.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessBuilderWithDirectory {
    public static void main(String... args) {
        ProcessBuilder processBuilder = new ProcessBuilder("ls"); // pass in your command and options;
        processBuilder.directory(new File("/home")); // specify you directory here;
        try {
            Process process = processBuilder.start();
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }
}
