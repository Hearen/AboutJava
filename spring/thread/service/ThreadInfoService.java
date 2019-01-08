package com.worksap.morphling.raptor.dump.thread.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksap.morphling.raptor.dump.thread.dao.ThreadDoRepository;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ThreadInfoService {
    @Autowired
    private ThreadDoRepository threadDoRepository;

    public ThreadDo getInfo(Long infoId) {
        return threadDoRepository.findOne(infoId);
    }

    public List<ThreadDo> getInfoList(List<Long> idList) {
        return threadDoRepository.findAllByThreadIdIsIn(idList);

    }

    public List<ThreadDo> getStateInfoList(Long dumpId, String state) {
        return threadDoRepository.findAllByDumpDoIdAndStateEnum(dumpId, state);
    }

    public List<ThreadDo> getGroupInfoList(Long dumpId, String groupName) {
        return threadDoRepository.findAllByDumpDoIdAndNameStartsWith(dumpId, groupName);
    }

    public List<ThreadDo> getGroupWithStateInfoList(Long dumpId, String groupName, StateEnum stateEnum) {
        return threadDoRepository.findAllByDumpDoIdAndNameStartsWithAndStateEnum(dumpId, groupName, stateEnum);
    }

    public List<ThreadDo> getDaemonOrNonDaemon(Long dumpId, boolean isDaemon) {
        return threadDoRepository.findAllByDumpDoIdAndDaemon(dumpId, isDaemon);
    }

    public List<ThreadDo> getLockHolders(Long dumpId, String lock) {
        List<ThreadDo> dos = threadDoRepository.findAllByDumpDoIdAndLocksHeldContains(dumpId, lock);
        return dos;
    }

    public List<ThreadDo> getLockWaiters(Long dumpId, String lock) {
        return threadDoRepository.findAllByDumpDoIdAndLocksWaitingContains(dumpId, lock);
    }

}
