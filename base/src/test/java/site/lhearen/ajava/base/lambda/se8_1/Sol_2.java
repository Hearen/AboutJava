package site.lhearen.ajava.base.lambda.se8_1;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static site.lhearen.ajava.mytools.constants.Constants.ROOT_DIR;
import static site.lhearen.ajava.mytools.util.Output.printFilepaths;

public class Sol_2 {
    private static List<File> listAllDirsUsingFileFilter(String rootDir) {
        return Arrays.asList(new File(rootDir).listFiles(File::isDirectory));
    }

    private static List<File> listAllDirsUsingLambda(String rootDir) {
        return Arrays.stream(new File(rootDir).listFiles())
                .filter(file -> file.isDirectory()) // the lambda;
                .collect(toList());
    }

    private static List<File> listAllDirsUsingMethodReference(String rootDir) {
        return Arrays.stream(new File(rootDir).listFiles())
                .filter(File::isDirectory)
                .collect(toList());
    }

    @Test
    public void testFilePrinter() {
        printFilepaths(listAllDirsUsingFileFilter(ROOT_DIR), "The End of Traditional Version");
        printFilepaths(listAllDirsUsingLambda(ROOT_DIR), "The End of Lambda Version");
        printFilepaths(listAllDirsUsingMethodReference(ROOT_DIR), "The End of Method Reference Version");
    }

}
