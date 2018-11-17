package site.lhearen.ajava.base.lambda.se8_1;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static site.lhearen.ajava.mytools.constants.Constants.ROOT_DIR;
import static site.lhearen.ajava.mytools.util.Output.printFilepaths;

public class Sol_4 {
    private static List<File> sortFiles(File[] files) {
        // folders are at the bottom while files are sorted based on file name;
        File[] copiedFiles = Arrays.copyOf(files, files.length);
        Arrays.sort(copiedFiles, (file1, file2) -> {
            if (file1.isDirectory() && !file2.isDirectory()) {
                return 1;
            } else if (!file1.isDirectory() && file2.isDirectory()) {
                return -1;
            } else {
                return file1.getName().compareToIgnoreCase(file2.getName());
            }
        });
        return Arrays.asList(copiedFiles);
    }

    @Test
    public void testFileSort() {
        File[] files = new File(ROOT_DIR).listFiles();
        printFilepaths(sortFiles(files), "File Sorted with Folders in the End");
    }
}
