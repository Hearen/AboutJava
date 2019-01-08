package com.worksap.morphling.raptor.dump.thread.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worksap.morphling.raptor.dump.thread.dao.ThreadDumpSimpleDoRepository;
import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpSimpleDo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDumpService {

    @Autowired
    ThreadDumpSimpleDoRepository threadDumpSimpleDoRepository;

    public List<ThreadDumpSimpleDo> getUserHistory(String userName) {
        return threadDumpSimpleDoRepository.findByCreatedByOrderByParsedTimeDesc(userName);
    }

    public List<ThreadDumpSimpleDo> getAllHistory() {
        return threadDumpSimpleDoRepository.findAllByOrderByParsedTimeDesc();
    }
}
