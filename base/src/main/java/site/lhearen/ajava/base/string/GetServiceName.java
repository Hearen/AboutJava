package site.lhearen.ajava.base.string;

import static java.lang.System.out;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class GetServiceName {

    private static final Pattern[] SERVICE_NAME_PATTERNS = {
            Pattern.compile("(?x)"
                    + "\\w+/ # tenant\n"
                    + "\\w+/ # landscape\n"
                    + "\\w+/ # heapdump\n"
                    + "\\w+/ # group\n"
                    + "\\d{4}-\\d{2}-\\d{2}/ # date\n"
                    + "[^/]+/ # host addr \n"
                    + "(.*)-[^/]+-[^/]+(\\.tar\\.gz|\\.bin) # random number\n"),
            Pattern.compile("(?x)"
                    + "\\w+/ # tenant\n"
                    + "\\w+/ # landscape\n"
                    + "\\w+/ # heapdump\n"
                    + "(.*)/ # group including service name \n"
                    + "\\d{4}-\\d{2}-\\d{2}/ # date\n"
                    + "[^/]+/ # host addr \n"
                    + "\\d+(.tar.gz)|(.bin) # random number\n")
    };
    private static final String[] GROUP_TYPES = {"kubenertes", "cassandra", "hr-develop-cjk-ap", "hr-develop-cws-ap"};
    private static final String[] SPECIAL_SERVICE_NAMES = {"cjk-ap", "cws-ap"};

    public static void main(String... args) {
        try {
            List<String> lines = Files.readAllLines(Paths.get("/home/hearen/git/personal/AboutJava/base/resources/serviceNames.txt"));
            lines.stream().distinct().filter(line -> line.length() > 20)
                    .map(line -> line.replaceAll("[\\\", ]", ""))
                    .forEach(GetServiceName::getServiceNameFromS3Path);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private static String getServiceNameFromS3Path(String dumpRelativePath) {
        String serviceName = dumpRelativePath;
        for (Pattern serviceNamePattern : SERVICE_NAME_PATTERNS) {
            Matcher matcher = serviceNamePattern.matcher(dumpRelativePath);
            if (matcher.find()) {
                serviceName = matcher.group(1);
                break;
            }
        }
        for (String s : SPECIAL_SERVICE_NAMES) {
            if (StringUtils.isNotEmpty(serviceName) && serviceName.toUpperCase().contains(s.toUpperCase())) {
                serviceName = s;
                break;
            }
        }
        out.println("***ServiceName: " + serviceName + "\nFrom: " + dumpRelativePath);
        if (isServiceNameParsingFailed(serviceName, dumpRelativePath)) {
            out.println("--------------------------------------------------------------------------  parsing failed ");
        }
        return serviceName;
    }


    /**
     * if failed at parsing service name parsed (from the s3 dump file key)
     *
     * @param serviceName
     * @param relativePath
     * @return
     */
    private static boolean isServiceNameParsingFailed(String serviceName, String relativePath) {
        return relativePath.equalsIgnoreCase(serviceName)
                || Stream.of(GROUP_TYPES)
                .anyMatch(groupName -> groupName.equalsIgnoreCase(serviceName));
    }
}
