package com.worksap.morphling.raptor.dump.thread.service;

import static com.worksap.morphling.raptor.dump.thread.util.DumpUtils.CUR_SOURCE_VERSION;
import static com.worksap.morphling.raptor.dump.thread.util.DumpUtils.SOURCE_NAME;
import static com.worksap.morphling.raptor.dump.thread.util.DumpUtils.SOURCE_VERSION;
import static com.worksap.morphling.raptor.dump.thread.util.DumpUtils.getCheckSum;
import static com.worksap.morphling.raptor.dump.thread.util.DumpUtils.getSharedStateStackTraceListMap;
import static com.worksap.morphling.raptor.dump.thread.util.DumpUtils.prepareDumpVo;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.convertListToSize;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getMapFromString;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.getStateGroup;
import static com.worksap.morphling.raptor.dump.thread.util.ThreadUtils.toJsonString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worksap.morphling.doberman.client.context.SessionContextHolder;
import com.worksap.morphling.raptor.dump.thread.dao.ThreadDumpDoRepository;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpDo;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;
import com.worksap.morphling.raptor.dump.thread.executor.DumpExcutor;
import com.worksap.morphling.raptor.dump.thread.util.DeadLockUtils;
import com.worksap.morphling.raptor.dump.thread.util.DumpUtils;
import com.worksap.morphling.raptor.dump.thread.util.ThreadUtils;
import com.worksap.morphling.raptor.dump.thread.util.parser.DumpParser;
import com.worksap.morphling.raptor.dump.thread.vo.DumpComparisonVo;
import com.worksap.morphling.raptor.dump.thread.vo.ThreadDumpVo;
import com.worksap.morphling.raptor.productdashboard.RetrieverException;
import com.worksap.morphling.raptor.productdashboard.retriever.promethues.ProductInstanceRetriever;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DumpService {
    private static final String KUBE_MASTER_0 = "kubernetes-master[0]";
    private static final String KUBE_MASTER_NAME = "kubernetes-master";
    @Autowired
    private DumpExcutor dumpExcutor;

    @Autowired
    private ThreadDumpDoRepository dumpDoRepository;

    @Autowired
    private DumpParser dumpParser;

    @Autowired
    private S3DownLoadService downLoadService;

    @Autowired
    private ProductInstanceRetriever productInstanceRetriever;

    private static void updateThreadDumpDo(ThreadDumpDo threadDumpDo) {
        ThreadDumpVo threadDumpVo = prepareDumpVo(threadDumpDo);
        threadDumpDo.setThreadDumpVoJson(toJsonString(threadDumpVo));
    }

    public List<Long> parseUploadedFileList(MultipartFile[] uploadedFiles) {
        List<MultipartFile> multipartFileList = new ArrayList<>();
        if (uploadedFiles != null && uploadedFiles.length != 0) {
            multipartFileList.addAll(Arrays.asList(uploadedFiles));
        }
        String userName = SessionContextHolder.getCurrentUserName();
        return multipartFileList.stream().map(DumpUtils::convertMultiPartToFile)
                .map(file -> this.parseFile(file, userName, true))
                .collect(Collectors.toList());
    }

    public Long parseFile(File dumpedFile, String userName, boolean isUploaded) {
        Map<String, String> initParams = new HashMap<>();
        if (!isUploaded) {
            initParams.put(SOURCE_NAME, "");
        }
        initParams.put(SOURCE_VERSION, CUR_SOURCE_VERSION);

        ThreadDumpDo threadDumpDo = parseFileWithoutDb(dumpedFile, userName, toJsonString(initParams));

        if (threadDumpDo.getId() > -1) {
            threadDumpDo = dumpDoRepository.save(threadDumpDo);
        }
        return threadDumpDo.getId();
    }

    private ThreadDumpDo parseFileWithoutDb(File dumpedFile, String userName, String details) {
        String checkSum = getCheckSum(dumpedFile);
        List<ThreadDumpDo> threadDumpDoList = dumpDoRepository.findByCheckSumAndCreatedBy(checkSum, userName);
        if (threadDumpDoList.size() != 0) {
            return threadDumpDoList.get(0);
        }
        ThreadDumpDo threadDumpDo = ThreadDumpDo.builder().id(0L).build();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dumpedFile))) {
            lines.addAll(br.lines().collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Thread Parsing File Reading failed!");
            threadDumpDo.setId(-1L);
            return threadDumpDo;
        }
        try {
            threadDumpDo = parseThreadDump(lines, false);
            threadDumpDo.setDetails(details);
            threadDumpDo.setCheckSum(checkSum);
            threadDumpDo.setFileName(dumpedFile.getName());
            threadDumpDo.setCreatedBy(userName);
            updateThreadDumpDo(threadDumpDo);
            threadDumpDo.setId(0L);
            return threadDumpDo;
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("Parsing failed!");
            threadDumpDo.setId(-1L);
        }
        return threadDumpDo;
    }

    public String generateDumpFile(Long envId, String podName, String namespace) {
        return dumpExcutor.run(envId, getKubeMasterIp(envId), podName, namespace);
    }

    public String generateDumpFile(Long envId, String podName, String namespace, Long dumpCount, Long dumpInterval) {
        return dumpExcutor.run(envId, getKubeMasterIp(envId), podName, namespace, dumpCount, dumpInterval);
    }

    public String downloadDumpFile(Long dumpId) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        return downLoadService.getS3DownloadUrl(threadDumpDo.getFileName());
    }

    public DumpComparisonVo getComparisonVo(List<Long> dumpIds) {
        // ToDo: parallel required?
        List<ThreadDumpDo> dumpDoList = dumpIds.stream().map(dumpDoRepository::findOne).collect(Collectors.toList());
        List<String> fileNames = dumpDoList.stream().map(dumpDo -> dumpDo.getFileName()).collect(Collectors.toList());
        Map<StateEnum, Map<String, List<Long>>> sharedStateStackTraceListMap =
                getSharedStateStackTraceListMap(dumpDoList);
        List<Map<StateEnum, Integer>> stateCountMapList = dumpDoList.stream()
                .map(dumpDo -> convertListToSize(getStateGroup(dumpDo))).collect(Collectors.toList());
        return DumpComparisonVo.builder()
                .dumpIds(dumpIds)
                .dumpFileNames(fileNames)
                .stateMapList(stateCountMapList)
                .sharedStateStackTraceMapList(sharedStateStackTraceListMap).build();
    }

    public String getVo(Long dumpId) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);

        Map<String, String> detailMap = getMapFromString(threadDumpDo.getDetails());
        if (!detailMap.getOrDefault(SOURCE_VERSION, "").equalsIgnoreCase(CUR_SOURCE_VERSION)) {
            detailMap.put(SOURCE_VERSION, CUR_SOURCE_VERSION);
            threadDumpDo.setDetails(toJsonString(detailMap));
            updateThreadDumpDo(threadDumpDo);
            dumpDoRepository.save(threadDumpDo);
        }
        return threadDumpDo.getVo();
    }

    public List<List<String>> getSimpleDeadLockLoops(Long dumpId) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        return DeadLockUtils.getDeadLockLoops(threadDumpDo);
    }

    public String getStackTrace(Long dumpId, String stackTrace) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        Map<String, List<ThreadDo>> threadDoMap = ThreadUtils.getCallStackGroup(threadDumpDo);
        return tranceListToJsonStr(threadDoMap.get(stackTrace));
    }

    public String getStackWithState(Long dumpId, String stackTrace, String state) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        Map<String, Map<StateEnum, List<ThreadDo>>> threadDoWithStateMap = ThreadUtils
                .getCallStackWithStateGroup(threadDumpDo);
        return tranceListToJsonStr(threadDoWithStateMap.get(stackTrace).get(state));
    }

    public String getMostUsedMethod(Long dumpId, String mostUsedMethod) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        Map<String, List<ThreadDo>> temp = ThreadUtils.getMostUsedMethodGroup(threadDumpDo);
        List<ThreadDo> temp2 = temp.get(mostUsedMethod);
        return tranceListToJsonStr(temp2);
    }

    public String getCpuConsuming(Long dumpId, String methodName) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        return tranceListToJsonStr(
                ThreadUtils.getCpuConsumingThreads(threadDumpDo).getOrDefault(methodName, new ArrayList<>()));
    }

    public String getGcThreads(Long dumpId) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        return tranceListToJsonStr(ThreadUtils.getGcGroup(threadDumpDo));
    }

    public String getFinalizerThreads(Long dumpId) {
        ThreadDumpDo threadDumpDo = dumpDoRepository.findOne(dumpId);
        return tranceListToJsonStr(ThreadUtils.getFinalizerGroup(threadDumpDo));
    }

    private ThreadDumpDo parseThreadDump(List<String> lines, boolean isParallel) throws ParseException {
        ThreadDumpDo threadDumpDo = dumpParser.parse(lines, isParallel);
        threadDumpDo.getThreadDoList().forEach(threadDo -> threadDo.setDumpDo(threadDumpDo));
        return threadDumpDo;
    }

    private String tranceListToJsonStr(List<ThreadDo> threadDoList) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(threadDoList);
        } catch (JsonProcessingException e) {
            log.error("Error to trance the thread do list");
        }
        return "[]";
    }

    public String getKubeMasterIp(Long envId) {
        try {
            Map<String, String> instanceMap = productInstanceRetriever.getInstanceList(envId);
            for (Map.Entry<String, String> entry : instanceMap.entrySet()) {
                if (KUBE_MASTER_NAME.equals(entry.getValue())) {
                    return entry.getKey();
                }
            }
        } catch (RetrieverException e) {
            // Cannot find, Use default
        }
        return KUBE_MASTER_0;
    }
}
