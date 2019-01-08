package com.worksap.morphling.raptor.dump.thread.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;
import com.worksap.morphling.raptor.dump.thread.service.DumpService;
import com.worksap.morphling.raptor.dump.thread.service.ThreadInfoService;

@RestController
@RequestMapping("dump/thread-info")
public class ThreadInfoController {
    @Autowired
    DumpService dumpService;

    @Autowired
    ThreadInfoService infoService;

    @GetMapping("/{infoId}")
    public ThreadDo getThreadInfo(@PathVariable Long infoId) {
        return infoService.getInfo(infoId);
    }

    @GetMapping("/list/{threadIds}")
    public List<ThreadDo> listThreadInfo(@PathVariable List<Long> threadIds) {
        return infoService.getInfoList(threadIds);
    }

    @GetMapping("/{dumpId}/state/{state}")
    public List<ThreadDo> listState(@PathVariable Long dumpId, @PathVariable String state) {
        return infoService.getStateInfoList(dumpId, state);
    }

    @GetMapping("/{dumpId}/pool/{groupName}")
    public List<ThreadDo> listByGroupName(@PathVariable Long dumpId, @PathVariable String groupName) {
        return infoService.getGroupInfoList(dumpId, groupName);
    }

    @GetMapping("/{dumpId}/pool-state/{stateEnum}/{groupName}")
    public List<ThreadDo> listByGroupNameAndState(@PathVariable Long dumpId, @PathVariable String groupName,
                                                  @PathVariable StateEnum stateEnum) {
        return infoService.getGroupWithStateInfoList(dumpId, groupName, stateEnum);
    }

    @GetMapping("/{dumpId}/daemon/{isDaemon}")
    public List<ThreadDo> listDaemonOrNondaemon(@PathVariable Long dumpId, @PathVariable boolean isDaemon) {
        return infoService.getDaemonOrNonDaemon(dumpId, isDaemon);
    }

    @GetMapping("/{dumpId}/lockholder/{lock}")
    public List<ThreadDo> listLockHolders(@PathVariable Long dumpId, @PathVariable String lock) {
        return infoService.getLockHolders(dumpId, lock);
    }

    @GetMapping("/{dumpId}/lockwaiter/{lock}")
    public List<ThreadDo> listLockWaiters(@PathVariable Long dumpId, @PathVariable String lock) {
        return infoService.getLockWaiters(dumpId, lock);
    }

}
