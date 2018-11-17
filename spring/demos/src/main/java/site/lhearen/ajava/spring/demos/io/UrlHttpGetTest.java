package site.lhearen.ajava.spring.demos.io;

import site.lhearen.ajava.mytools.util.IoHelper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UrlHttpGetTest {
    private static String urlString = "https://jill.morphling.worksap.com/grafana/api/search";
//    private static String urlString = "https://www.google.com.sg";
    private static String tokenString = "UM-TOKEN=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJHcmFmYW5hIERhc2hib2FyZCIsImJlbG9uZ2luZ0hvc3QiOiJodHRwczovL2ppbGwubW9ycGhsaW5nLndvcmtzYXAuY29tIiwidXNlck5hbWUiOiJHcmFmYW5hIERhc2hib2FyZCIsInVzZXJUeXBlIjoiRU5EX1VTRVIiLCJ1c2VyVG9rZW5BdHRlbXB0cyI6OTk5OTk5OTksInRva2VuRXh0ZW5kTWludXRlcyI6NjAsInRva2VuRXh0ZW5kQ29uZGl0aW9uIjo2MCwicGFzc3dvcmRVcGRhdGVkIjp0cnVlLCJjbGllbnRMQU5JUCI6IjEyNy4wLjAuMSIsImV4cCI6MTU1NzgwMDEyNH0.Qat2ARHrtQAgG2zZOh-121QXg7HCje_T7Dw3nGbEi81RDfZ9Vz3dYk1HiQASDrl2e6ssGJCNORSgdf08SGcqMQ";

//    private static String tokenString = "";
    public static void main(String... args) {
        getWithRunTimeExec();
        getWithProcessorBuilder();
        getUsingUrl();
        getUsingRestTemplate();
    }

    private static void getWithRunTimeExec() {
        System.out.println("Using RunTime.exec: ");
        try {
            Process process = Runtime.getRuntime().exec("curl -v --cookie \"" + tokenString + "\" " + urlString);

            process.waitFor();
            System.out.println(IoHelper.output(process.getInputStream()));
            process = Runtime.getRuntime().exec(new String[]{"curl", "-v", "--cookie", tokenString, urlString});

            process.waitFor();
            System.out.println(IoHelper.output(process.getInputStream()));

        } catch (IOException | InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }

    private static void getWithProcessorBuilder() {
        System.out.println("Using ProcessorBuilder: ");
        try {
            Process process = new ProcessBuilder("curl", "-v", "--cookie", tokenString, urlString).start();
            process.waitFor();
            System.out.println(IoHelper.output(process.getInputStream()));
        } catch (IOException | InterruptedException ignored) {
            ignored.printStackTrace();
        }

    }

    private static void getUsingUrl() {
        System.out.println("Using Url: ");
        try {
            URL myURL = new URL(urlString);
            HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();
            myURLConnection.setRequestMethod("GET");
            myURLConnection.setRequestProperty("cookie", tokenString);
            myURLConnection.connect();
            System.out.println(IoHelper.output(myURLConnection.getInputStream()));
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private static void getUsingRestTemplate() {
        System.out.println("Using RestTemplate: ");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", tokenString);
        HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
        ResponseEntity rssResponse = restTemplate.exchange(
                urlString,
                HttpMethod.GET,
                requestEntity,
                String.class);
        System.out.println(rssResponse.getBody());
    }
}
