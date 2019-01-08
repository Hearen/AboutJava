package com.worksap.morphling.raptor.dump.thread.service;

import static com.worksap.morphling.raptor.elephas.biz.util.Security.decrypt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.worksap.morphling.raptor.elephas.dao.EnvironmentUsageRepository;
import com.worksap.morphling.raptor.elephas.entity.EnvironmentUsage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class S3DownLoadService {
    public static final String AWS_ACCESS_KEY_NAME = "accessKey";
    public static final String AWS_SECRET_NAME = "keyContent";
    public static final Long DEFAULT_S3_URL_EXPIRATION_TIME = 24 * 60 * 60 * 1000L;
    private static final String CLIENT_REGION = "ap-northeast-1";
    private static final String BACKET_NAME = "opt-dump";
    private static final String THREAD_DUMP_FOLDER = "thread-dump/";
    @Autowired
    EnvironmentUsageRepository environmentUsageRepository;

    public String getS3DownloadUrl(String fileName) {
        return getPreSignedS3FileUrl(
                getThreadDumpS3FilePath(fileName), DEFAULT_S3_URL_EXPIRATION_TIME);
    }

    public File download(String fileName, boolean isGeneratingPhase) {
        if (!isGeneratingPhase) {
            fileName = getThreadDumpS3FilePath(fileName);
        }
        String fileFullName = "thread-dump/" + fileName;
        String filePath = "/tmp/" + fileName;
        return download(fileFullName, filePath);
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "PATH_TRAVERSAL_IN",
            justification = "Specified by System Only")
    public File download(String srcPath, String desPath) {
        Map<String, String> s3Key = getS3Key();

        AWSCredentials credentials = new BasicAWSCredentials(s3Key.get(AWS_ACCESS_KEY_NAME),
                s3Key.get(AWS_SECRET_NAME));

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_NORTHEAST_1)
                .build();

        if (s3client.doesBucketExist(BACKET_NAME)) {
            S3Object s3object = s3client.getObject(BACKET_NAME, srcPath);
            S3ObjectInputStream inputStream = s3object.getObjectContent();

            File downLoadFile = null;
            try {
                downLoadFile = new File(desPath);
                Files.copy(inputStream, downLoadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Cannot trans the file properly");
            }
            return downLoadFile;
        }

        return null;
    }


    private String getThreadDumpS3FilePath(String fileName) {
        return THREAD_DUMP_FOLDER.concat(fileName.substring(0, fileName.lastIndexOf("-")).concat(".tar"));
    }

    public Map<String, String> getS3Key() {
        EnvironmentUsage environmentUsage = environmentUsageRepository
                .getFirstByTenantNameAndLandscapeNameAndEnvType("optj", "develop", "native");

        if (environmentUsage == null
                || environmentUsage.getAwsVpc() == null
                || environmentUsage.getAwsVpc().getAwsCredential() == null) {
            return null;
        }

        String accessKey = environmentUsage.getAwsVpc().getAwsCredential().getAccessKeyId();
        String keyContent = decrypt(environmentUsage.getAwsVpc().getAwsCredential().getSecretAccessKey());

        Map<String, String> s3Key = new HashMap<>();
        s3Key.put(AWS_ACCESS_KEY_NAME, accessKey);
        s3Key.put(AWS_SECRET_NAME, keyContent);
        return s3Key;
    }

    public String getPreSignedS3FileUrl(String fileFullPath) {
        return getPreSignedS3FileUrl(fileFullPath, DEFAULT_S3_URL_EXPIRATION_TIME);
    }

    public String getPreSignedS3FileUrl(String fileFullPath, Long expireTimeInMs) {
        try {
            Map<String, String> s3Key = getS3Key();

            AWSCredentials credentials = new BasicAWSCredentials(s3Key.get(AWS_ACCESS_KEY_NAME),
                    s3Key.get(AWS_SECRET_NAME));

            AmazonS3 s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.AP_NORTHEAST_1)
                    .build();

            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += expireTimeInMs;
            expiration.setTime(expTimeMillis);

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(BACKET_NAME, fileFullPath)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            log.info("S3 Pre-Signed URL: {}, expired after {} ms", url.toString(), expireTimeInMs);
            return url.toString();
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }
        return "";
    }
}
