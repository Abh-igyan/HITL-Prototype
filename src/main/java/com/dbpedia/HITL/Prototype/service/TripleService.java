package com.dbpedia.HITL.Prototype.service;

import com.dbpedia.HITL.Prototype.entity.Triple;
import com.dbpedia.HITL.Prototype.repository.TripleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TripleService.java
@Service
public class TripleService {

    @Autowired
    private TripleRepository repo;

    public Triple save(Triple t) { return repo.save(t); }

    public List<Triple> getPending() {
        return repo.findByStatus(Triple.ValidationStatus.PENDING);
    }

    public Triple validate(Long id, String status, String correction) {
        Triple t = repo.findById(id).orElseThrow();
        t.setStatus(Triple.ValidationStatus.valueOf(status.toUpperCase()));
        if (correction != null) t.setCorrectedTriple(correction);
        return repo.save(t);
    }

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        for (Triple.ValidationStatus s : Triple.ValidationStatus.values())
            stats.put(s.name(), repo.countByStatus(s));
        return stats;
    }
}