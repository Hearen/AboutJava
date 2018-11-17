package site.lhearen.ajava.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetPodPrefix {
    private static final Pattern POD_NAME_PATTERN = Pattern.compile("(.+)-\\w+-\\w+");
    public static final String BUCKET_NAME_VALUE = "test-hearen";
    public static void main(String... args) {
        String[] podNames = {"collabo-archive-6647476489-n8q85", "com-filetransfer-w-6678bfb48f-lnhzn",
        "com-ifx-7cb7bdd87f-8q8fc", "drive-555889f8b8-9bpcf", "es-index-consumer-7658b6ffc9-gjm6b"};
        for (String podName : podNames) {
            System.out.println(getPodPrefixName(podName));
        }
        String absPath = "/test-hearen/editor/logback.xml";
        System.out.println(getRelativePath(absPath));
    }

    private static String getPodPrefixName(String podName) {
        Matcher matcher = POD_NAME_PATTERN.matcher(podName);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return podName;
    }

    private static String getRelativePath(String fileAbsPath) {
        return fileAbsPath.substring(fileAbsPath.indexOf(BUCKET_NAME_VALUE) + BUCKET_NAME_VALUE.length());
    }
}
