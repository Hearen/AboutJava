package site.lhearen.ajava.base.io;

import java.io.File;
import java.util.Arrays;

public class CheckFileExtInFolder {
    private static final String NEW_RULE_EXTENSION = "rules.yml";
    private static final String OLD_RULE_EXTENSION = "rules";
    public static void main(String... args) {
        String folderAbsPath = "/tmp/prometheus/rules/hrcoremt_develop_multi-tenant-single/";
        String anotherAbsPath = "/tmp/prometheus/rules/jillk_develop_multi-tenant-single/";
        System.out.println(getExt(folderAbsPath));
        System.out.println(getExt(anotherAbsPath));
    }

    private static String getExt(String folderAbsPath) {
        File theFolder = new File(folderAbsPath);
        String fileExt = NEW_RULE_EXTENSION;
        if (Arrays.stream(theFolder.listFiles()).anyMatch(file -> file.getName().endsWith(OLD_RULE_EXTENSION))) {
            fileExt = OLD_RULE_EXTENSION;
        }
        return fileExt;
    }
}
