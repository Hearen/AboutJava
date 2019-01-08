package com.worksap.morphling.raptor.dump.thread.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.worksap.morphling.raptor.dump.thread.entity.ThreadDo;
import com.worksap.morphling.raptor.dump.thread.enums.StateEnum;

public interface ThreadDoRepository extends JpaRepository<ThreadDo, Long> {

    @Query(value = "select * from thread_info as t where t.thread_dump_id = ?1 and t.state_enum=?2",
            nativeQuery = true)
    List<ThreadDo> findAllByDumpDoIdAndStateEnum(Long dumpId, String stateEnum);

    List<ThreadDo> findAllByDumpDoIdAndNameStartsWith(Long dumpId, String groupName);

    List<ThreadDo> findAllByDumpDoIdAndNameStartsWithAndStateEnum(Long dumpId, String groupName, StateEnum stateEnum);

    List<ThreadDo> findAllByDumpDoIdAndDaemon(Long dumpId, boolean isDaemon);

    List<ThreadDo> findAllByThreadIdIsIn(List<Long> threadIds);

    @Query(value = "select * from thread_info as t where t.thread_dump_id = ?1 and t.locks_held like %?2%",
            nativeQuery = true)
    List<ThreadDo> findAllByDumpDoIdAndLocksHeldContains(Long dumpId, String lock);


    @Query(value = "select * from thread_info as t where t.thread_dump_id = :dumpId and t.locks_waiting like %:lock%",
            nativeQuery = true)
    List<ThreadDo> findAllByDumpDoIdAndLocksWaitingContains(@Param("dumpId") Long dumpId, @Param("lock") String lock);
}
