package com.worksap.morphling.raptor.dump.thread.executor;

import static com.worksap.morphling.raptor.common.BeanNameHelper.getBeanName;
import static com.worksap.morphling.raptor.common.CommonSymbol.HYPHEN;
import static com.worksap.morphling.raptor.common.CommonSymbol.LEFT_BRACKET;
import static com.worksap.morphling.raptor.common.CommonSymbol.RIGHT_BRACKET;
import static com.worksap.morphling.raptor.common.CommonSymbol.UNDERLINE;
import static com.worksap.morphling.raptor.dump.thread.service.S3DownLoadService.AWS_ACCESS_KEY_NAME;
import static com.worksap.morphling.raptor.dump.thread.service.S3DownLoadService.AWS_SECRET_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksap.morphling.raptor.dump.thread.dao.NamespaceDoRepository;
import com.worksap.morphling.raptor.dump.thread.entity.NamespaceDo;
import com.worksap.morphling.raptor.dump.thread.service.S3DownLoadService;
import com.worksap.morphling.raptor.elephas.entity.EnvironmentUsage;
import com.worksap.morphling.raptor.scheduler.executor.AnsibleCommandTunnelExecutor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DumpExcutor extends AnsibleCommandTunnelExecutor {

    public static final Integer TEN_THOUSAND = 10_000;
    private static final String POD_NAME = "podName";
    private static final String EXECUTING_SCRIPT = "generate-several-dump-files.yml";
    private static final String JOB_IDENTIFIER_PREFIX = "GENERATE_DUMP_FILES";
    private static final String EMPTY_STRING = "";
    private static final String ENV_NAME = "envName";
    private static final String DUMP_FILE_NAME = "dumpFileName";
    private static final String DIR_NAME = "dirName";
    private static final String DUMP_COUNT = "dumpCount";
    private static final String DUMP_INTERVAL = "dumpInterval";
    private static final String AWS_ACCESS_KEY = "awsAccessKey";
    private static final String AWS_SE = "awsSecretKey";

    @Autowired
    NamespaceDoRepository namespaceDoRepository;

    @Autowired
    S3DownLoadService s3DownLoadService;

    public String run(Long envId, String host, String podName, String namespace) {
        return run(envId, host, podName, namespace, 1L, 0L);
    }

    public String run(Long envId, String host, String podName, String namespace,
                      Long dumpCount, Long dumpInterval) {

        EnvironmentUsage environmentUsage = environmentUsageRepository
                .getFirstByTenantNameAndLandscapeNameAndEnvType("optj", "develop", "native");

        if (environmentUsage == null
                || environmentUsage.getAwsVpc() == null
                || environmentUsage.getAwsVpc().getAwsCredential() == null) {
            return "failed";
        }

        Map<String, String> s3Key = s3DownLoadService.getS3Key();

        String accessKey = s3Key.get(AWS_ACCESS_KEY_NAME);
        String keyContent = s3Key.get(AWS_SECRET_NAME);

        String envName = getEnvName(namespace, envId);

        Long systemTime = getSystemTime();
        int randomNum = getRandomNum();

        String jobIdentifier = getJobIdentifier(JOB_IDENTIFIER_PREFIX, envId, systemTime, randomNum);
        String filePreFix = getFileName(envName, podName, systemTime, randomNum);

        Map<String, String> parameterMap = new HashMap<>();

        parameterMap.put(POD_NAME, podName);
        parameterMap.put(ENV_NAME, envName);
        parameterMap.put(AWS_ACCESS_KEY, accessKey);
        parameterMap.put(AWS_SE, keyContent);
        parameterMap.put(DUMP_FILE_NAME, filePreFix);
        parameterMap.put(DIR_NAME, getDirName(systemTime, randomNum));
        parameterMap.put(DUMP_COUNT, String.valueOf(dumpCount));
        parameterMap.put(DUMP_INTERVAL, String.valueOf(dumpInterval));

        String postHook = getBeanName(GenerateExecutorPostHook.class);

        return execute(envId, host, jobIdentifier, EXECUTING_SCRIPT, parameterMap, null, postHook);
    }


    private String getEnvName(String namespace, Long envId) {
        String envName;
        if (namespace == null || EMPTY_STRING.equals(namespace)) {
            List<NamespaceDo> namespaceDoList = namespaceDoRepository.findByEnvironmentUsageId(envId);
            if (namespaceDoList.size() != 0) {
                envName = namespaceDoList.get(0).getNamespace();
            } else {
                EnvironmentUsage environmentUsage = environmentUsageRepository.findOne(envId);
                envName = environmentUsage.getTenant().getName() + HYPHEN + environmentUsage.getLandscape().getName();
            }
        } else {
            envName = namespace;
        }
        return envName;
    }

    private Long getSystemTime() {
        return System.currentTimeMillis();
    }

    private int getRandomNum() {
        return RandomUtils.nextInt(0, Integer.MAX_VALUE);
    }

    private String getJobIdentifier(String initIdentifier, Long envId, Long systemTime, int randomNum) {

        return initIdentifier
                + HYPHEN
                + LEFT_BRACKET
                + envId
                + RIGHT_BRACKET
                + HYPHEN
                + systemTime
                + HYPHEN
                + randomNum;
    }

    private String getFileName(String envName, String podName, Long systemTime, int randomNum) {
        return envName.replace(HYPHEN, UNDERLINE)
                + UNDERLINE
                + podName.replace(HYPHEN, UNDERLINE)
                + UNDERLINE
                + systemTime
                + UNDERLINE
                + randomNum % TEN_THOUSAND;
    }

    private String getDirName(Long systemTime, int randomNum) {
        return EMPTY_STRING + systemTime + randomNum % TEN_THOUSAND;
    }
}
