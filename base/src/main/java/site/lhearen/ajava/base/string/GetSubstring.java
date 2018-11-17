package site.lhearen.ajava.base.string;

import static java.lang.System.out;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSubstring {
    public static void main(String... args) {
        String urlString1 = "https://opt-tool-eva.s3-ap-northeast-1.amazonaws.com/optj/develop/heapdump/kubernetes/2018-10-30-acall_develop_ac_accounting_18.06.00.0000.20180626.1729_xgmn5_1533108230959_32441.bin?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20181204T044743Z&X-Amz-SignedHeaders=host&X-Amz-Expires=7200&X-Amz-Credential=AKIAIQ7VVN5O6FZRXPTA%2F20181204%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=358727ed1e71d06df7ff4313f74ea3f2f7e5c4947083c60604cbe39441999e6b";
        String urlString2 = "https://opt-tool-eva.s3-ap-northeast-1.amazonaws.com/optj/develop/heapdump/kubernetes/2018-10-30-acall_develop_ac_accounting_18.06.00.0000.20180626.1729_xgmn5_1533108230959_32441.tar.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20181204T044743Z&X-Amz-SignedHeaders=host&X-Amz-Expires=7200&X-Amz-Credential=AKIAIQ7VVN5O6FZRXPTA%2F20181204%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=358727ed1e71d06df7ff4313f74ea3f2f7e5c4947083c60604cbe39441999e6b";
        Pattern pattern = Pattern.compile("https://.*com(.*)\\?.*");
        Matcher matcher = pattern.matcher(urlString1);
        if (matcher.find()) {
            out.println(matcher.group(1));
        }
        out.println(getDumpUrlSubstring(urlString1));
        matcher = pattern.matcher(urlString2);
        if (matcher.find()) {
            out.println(matcher.group(1));
        }
        out.println(getDumpUrlSubstring(urlString2));
    }

    public static String getDumpUrlSubstring(String dumpSignedUrl) {
        if (dumpSignedUrl.contains(".com") && (dumpSignedUrl.contains(".bin") || dumpSignedUrl.contains(".tar.gz"))) {
            if (dumpSignedUrl.contains(".bin")) {
                return dumpSignedUrl.substring(dumpSignedUrl.indexOf(".com") + 1, dumpSignedUrl.lastIndexOf(".bin"));
            }
            return dumpSignedUrl.substring(dumpSignedUrl.indexOf(".com") + 1, dumpSignedUrl.lastIndexOf(".tar.gz"));
        }
        return dumpSignedUrl;
    }
}
