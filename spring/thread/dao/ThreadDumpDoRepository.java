package com.worksap.morphling.raptor.dump.thread.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpDo;

public interface ThreadDumpDoRepository extends JpaRepository<ThreadDumpDo, Long> {
    List<ThreadDumpDo> findByCheckSumAndCreatedBy(String checkSum, String userName);
}
