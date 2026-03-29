package com.dbpedia.HITL.Prototype.repository;

import com.dbpedia.HITL.Prototype.entity.Triple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// TripleRepository.java
@Repository
public interface TripleRepository extends JpaRepository<Triple, Long> {
    List<Triple> findByStatus(Triple.ValidationStatus status);
    long countByStatus(Triple.ValidationStatus status);
}