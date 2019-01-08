package com.worksap.morphling.raptor.dump.thread.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worksap.morphling.raptor.dump.thread.entity.NamespaceDo;

public interface NamespaceDoRepository extends JpaRepository<NamespaceDo, Long> {
    List<NamespaceDo> findByEnvironmentUsageId(Long environmentUsageId);
}
