package com.worksap.morphling.raptor.dump.thread.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.worksap.morphling.raptor.dump.thread.service.DumpService;
import com.worksap.morphling.raptor.dump.thread.vo.DumpComparisonVo;
import com.worksap.morphling.raptor.productdashboard.RetrieverException;
import com.worksap.morphling.raptor.prometheus.annotation.MethodTracker;

@RestController
@RequestMapping("dump/thread")
public class DumpController {
    @Autowired
    DumpService dumpService;

    @GetMapping("/vo/{dumpId}")
    @MethodTracker
    public String getDumpVo(@PathVariable Long dumpId) {
        return dumpService.getVo(dumpId);
    }

    @GetMapping("/generate/{envId}/{podName:.+}")
    @MethodTracker(types = {MethodTracker.TypeEnum.WITH_ENVIRONMENT_NAME, MethodTracker.TypeEnum.WITH_POD_NAME})
    public String generateDumpFile(
            @PathVariable Long envId,
            @PathVariable String podName,
            @RequestParam(value = "namespace", required = false) String namespace,
            @RequestParam(value = "dumpCount", required = false) Long dumpCount,
            @RequestParam(value = "dumpInterval", required = false) Long dumpInterval) throws RetrieverException {
        if (dumpCount == null || dumpInterval == null) {
            return dumpService.generateDumpFile(envId, podName, namespace);
        } else {
            return dumpService.generateDumpFile(envId, podName, namespace, dumpCount, dumpInterval);
        }
    }

    @PostMapping("/parse/compare/")
    @MethodTracker
    public List<Long> uploadAnalyzeAndCompare(@RequestParam MultipartFile[] multiFileArr) {
        // The @RequestParam key word need front-end and back-end name contract.
        return dumpService.parseUploadedFileList(multiFileArr);
    }

    @GetMapping("/compare/{dumpIds}")
    @MethodTracker
    public DumpComparisonVo compareDump(@PathVariable Long[] dumpIds) {
        List<Long> dumpIdList = new ArrayList<>();
        dumpIdList.addAll(Arrays.asList(dumpIds));
        return dumpService.getComparisonVo(dumpIdList);
    }

    @GetMapping("/download/{dumpId}")
    @MethodTracker
    public String downloadDumpFile(@PathVariable Long dumpId) {
        return dumpService.downloadDumpFile(dumpId);
    }

    @GetMapping("/{dumpId}/simple-deadlock")
    public List<List<String>> getSimpleDeadLockLoops(@PathVariable Long dumpId) {
        return dumpService.getSimpleDeadLockLoops(dumpId);
    }

    @GetMapping("/{dumpId}/stackTrace/{stack:.+}")
    public String listStackTrace(@PathVariable Long dumpId, @PathVariable String stack) {
        String result = dumpService.getStackTrace(dumpId, stack
                .replace("%2F", "/")
                .replace("%7C", "|"));
        if (result == null) {
            return "[]";
        }
        return result;
    }

    @GetMapping("/{dumpId}/mostUsedMethod/{mostUsedMethod:.+}")
    public String getMostUsedMethodList(@PathVariable Long dumpId,
                                        @PathVariable String mostUsedMethod) throws JsonProcessingException {
        return dumpService.getMostUsedMethod(dumpId, mostUsedMethod);
    }

    @GetMapping("/{dumpId}/cpuConsuming/{methodName:.+}")
    public String getCpuConsuming(@PathVariable Long dumpId, @PathVariable String methodName) {
        return dumpService.getCpuConsuming(dumpId, methodName);
    }

    @GetMapping("/{dumpId}/gc")
    public String getGc(@PathVariable Long dumpId) {
        return dumpService.getGcThreads(dumpId);
    }

    @GetMapping("/{dumpId}/finalizer")
    public String getFinalizer(@PathVariable Long dumpId) {
        return dumpService.getFinalizerThreads(dumpId);
    }
}
