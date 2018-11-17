package site.lhearen.ajava.spring.demos.io;

import javax.net.ssl.SSLContext;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.NoSuchElementException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class UrlHttpPost {
    static String URL = "https://staging/huelog/athena/dump-file/list";
    static String tokenString = "UM-TOKEN=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsdW9fc29Ad29ya3NhcC5jby5qcCIsImJlbG9uZ2luZ0hvc3QiOiJodHRwczovL21vcnBobGluZy5pbnRlcm5hbC53b3Jrc2FwLmNvbSIsInVzZXJOYW1lIjoibHVvX3NvQHdvcmtzYXAuY28uanAiLCJ1c2VyVHlwZSI6IkVORF9VU0VSIiwidXNlclRva2VuQXR0ZW1wdHMiOjAsInRva2VuRXh0ZW5kTWludXRlcyI6NzIwLCJ0b2tlbkV4dGVuZENvbmRpdGlvbiI6NjAsInBhc3N3b3JkVXBkYXRlZCI6dHJ1ZSwiY2xpZW50TEFOSVAiOiIxNzIuMjYuMTQzLjIiLCJleHAiOjE1NDIyMDQ4MDh9.ZWVGdT5Z_29IdjPR1HTbUYiATv4H6agDIba_O5DsRettcmYs3VFgw9RmafSFy7F1zTbpHOeuhpQiQgGircUQDw";
    static String JsonString = "{\"envType\":\"native\",\"tenant\":\"hrcoremt\",\"landscape\":\"develop\",\"date\":\"2018-11-06\",\"group\":\"kubernetes\"}";


    public static void main(String... args) {
//        testPost();
        testRestTemplate();
    }

    public static void testPost() {
        try {
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(new SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httpPost = new HttpPost(URL);
            //String tokenString = configurationRepository.findOneByKey(SUP_TOKEN).getValue();
            httpPost.setEntity(new StringEntity(JsonString));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 "
                    + "(KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36");
            httpPost.setHeader("Authorization", tokenString);
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response);
        } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException
                | UnsupportedEncodingException | NoSuchElementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testRestTemplate() {
        System.out.println("Using RestTemplate: ");
        RestTemplate restTemplate = null;
        try {
            restTemplate = getRestTemplate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", tokenString);
        requestHeaders.add("Accept", "application/json");
        requestHeaders.add("Content-Type", "application/json");
        HttpEntity requestEntity = new HttpEntity(JsonString, requestHeaders);
        ResponseEntity rssResponse = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestEntity,
                String.class);
        System.out.println(rssResponse.getBody());
    }

    private static RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        };
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
}
