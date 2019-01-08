package com.worksap.morphling.raptor.dump.thread.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDumpSimpleDo;

public interface ThreadDumpSimpleDoRepository extends JpaRepository<ThreadDumpSimpleDo, Long> {
    List<ThreadDumpSimpleDo> findByCreatedByOrderByParsedTimeDesc(String userName);

    List<ThreadDumpSimpleDo> findAllByOrderByParsedTimeDesc();
}
