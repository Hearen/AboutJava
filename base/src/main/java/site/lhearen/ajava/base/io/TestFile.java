package site.lhearen.ajava.base.io;

import java.io.File;
import java.util.Arrays;

import static java.lang.System.out;

public class TestFile {

    public static void main(String... args) {
        String rootDir = "/home/hearen";
//        listAllDirsTraditional(rootDir);
        listAllDirsStream(rootDir);
    }
    private static void listAllDirsTraditional(String rootDir) {
        File[] files = new File(rootDir).listFiles();
        for(File file: files) {
            if (file.isDirectory()) {
                out.println(file.getPath());
            }
        }
        out.println("End of For Loop Version");
    }

    private static void listAllDirsStream(String rootDir) {
        Arrays.stream(new File(rootDir).listFiles())
                .filter(File::isDirectory)
                .map(File::getPath)
                .forEach(out::println);
        out.println("End of Stream Version");
    }
}
