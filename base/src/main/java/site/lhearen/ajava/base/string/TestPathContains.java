package site.lhearen.ajava.base.string;

import java.util.regex.Pattern;

public class TestPathContains {
    private static final Pattern VALID_FILE_PATH_PATTERN = Pattern.compile(".*/.*tomcat.*/.*/log.*");
    public static void main(String...args) {
        String validPath0 = "/opt/apache-tomcat-8.0.43/conf/logback.xml";
        String validPath1 = "/opt/apache-tomcat/conf/logback.xml";
        String validPath2 = "/opt/tomcat/conf/logback.xml";
        System.out.println(getNameFromPath(validPath0));
        System.out.println(checkPermission(validPath0));
        System.out.println(checkPermission(validPath1));
        System.out.println(checkPermission(validPath2));

        String invalidPath0 = "tomcat/conf/logback.xml";
        String invalidPath1 = "/opt/tomcatlogback.xml";
        System.out.println(checkPermission(invalidPath0));
        System.out.println(checkPermission(invalidPath1));
    }

    private static boolean checkPermission(String path) {
        return VALID_FILE_PATH_PATTERN.matcher(path).matches();
    }
    private static String getNameFromPath(String podFilePath) {
        String originalFileName = podFilePath.substring(podFilePath.lastIndexOf("/") + 1);
        return String.format("%s-%d.%s", originalFileName.substring(0, originalFileName.indexOf(".")),
                System.nanoTime(), originalFileName.substring(originalFileName.indexOf(".") + 1));
    }
}
