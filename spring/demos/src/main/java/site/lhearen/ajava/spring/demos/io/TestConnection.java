package site.lhearen.ajava.spring.demos.io;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;


public class TestConnection {
    private static final String RUNNING_STATE = "running";
    private static final String DOWN_STATE = "down";
    public static void main(String... args) {
        String disconnected = "https://mt-tokyo-103-1-elb-pm-1077667326.ap-northeast-1.elb.amazonaws.com:30990";
        String connected = "https://mt-tokyo-190-1-elb-pm-1566365701.ap-northeast-1.elb.amazonaws.com:30990";
        System.out.println(getPrometheusStatus(disconnected));
        System.out.println(getPrometheusStatus(connected));
    }

    private static String getPrometheusStatus(String url) {
        if (!url.contains("http")) {
            return url;
        }
        try {
            HttpClient httpClient = HttpClients
                    .custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setConnectTimeout(5 * 1000)
                            .setSocketTimeout(5 * 1000).build())
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
            HttpUriRequest httpUriRequest = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpUriRequest);
            if (response.getStatusLine().getStatusCode() == 200) {
                return RUNNING_STATE;
            }
        } catch (IOException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException ignored) {
            ignored.printStackTrace();
        }
        return DOWN_STATE;
    }
}
