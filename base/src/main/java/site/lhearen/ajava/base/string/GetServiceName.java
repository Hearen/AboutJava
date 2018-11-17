package site.lhearen.ajava.base.string;

import static java.lang.System.out;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetServiceName {
    private static final Pattern SERVICE_NAME_PATTERN_1 = Pattern.compile(".*/.*/*/.*/.*/.*/(.*)-\\d{2}\\.\\d{2}\\.\\d{2}.\\d{4}.*");
    private static final Pattern SERVICE_NAME_PATTERN_2 = Pattern.compile(".*/.*/*/(.*)/\\d{4}-\\d{2}-\\d{2}/.*");
    public static void main(String... args) {
        String path1 = "janf/production/heapdump/kubernetes/2018-11-14/10.34.96.17/collabo-17.12.00.0004.20180906.1220janfi5m52-vr6dc.tar.gz";
        String path2 = "d1bf/production/heapdump/hr-production-cjk-ap/2018-12-04/10.103.19.99/1543897551.tar.gz";
        out.println(getServiceNameFromFileName(path1));
        out.println(getServiceNameFromFileName(path2));
    }


    private static String getServiceNameFromFileName(String fileName) {
        String serviceName = fileName;
        Matcher matcher = SERVICE_NAME_PATTERN_1.matcher(fileName);
        if (matcher.find()) {
            serviceName = matcher.group(1);
        } else {
            matcher = SERVICE_NAME_PATTERN_2.matcher(fileName);
            if (matcher.find()) {
                serviceName = matcher.group(1);
            }
        }
        return serviceName;
    }
}
