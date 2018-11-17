package site.lhearen.ajava.base.lambda.se8_1;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static site.lhearen.ajava.mytools.constants.Constants.ROOT_DIR;
import static java.util.stream.Collectors.toList;
import static site.lhearen.ajava.mytools.util.Output.printFilepaths;

public class Sol_3 {
    private static List<File> getFilesWithExtUsingFilenameFilter(String rootDir, String ext) {
        return Arrays.asList(new File(rootDir)
                .listFiles((file, filename) -> filename.endsWith(ext)));
    }

    private static List<File> getFilesWithExtUsingLambda(String rootDir, String ext) {
        return Arrays.stream(new File(rootDir).listFiles())
                .filter(file -> file.getName().endsWith(ext))
                .collect(toList());
    }

    @Test
    public void testFileFilter() {
        printFilepaths(getFilesWithExtUsingFilenameFilter(ROOT_DIR, ".vim"), "Using FilenameFilter");
        printFilepaths(getFilesWithExtUsingLambda(ROOT_DIR, ".vim"), "Using lambda");
    }
}
