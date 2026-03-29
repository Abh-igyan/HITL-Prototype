package com.dbpedia.HITL.Prototype.controller;

import com.dbpedia.HITL.Prototype.entity.Triple;
import com.dbpedia.HITL.Prototype.service.TripleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// TripleController.java
@RestController
@RequestMapping("/api/triples")
public class TripleController {

    @Autowired
    private TripleService service;

    // Python extraction script calls this
    @PostMapping
    public ResponseEntity<Triple> ingest(@RequestBody Triple triple) {
        return ResponseEntity.ok(service.save(triple));
    }

    // Dashboard fetches pending triples
    @GetMapping("/pending")
    public List<Triple> getPending() {
        return service.getPending();
    }

    // Annotator approves/rejects
    @PatchMapping("/{id}/validate")
    public ResponseEntity<Triple> validate(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String correction) {
        return ResponseEntity.ok(service.validate(id, status, correction));
    }

    // Stats for dashboard header
    @GetMapping("/stats")
    public Map<String, Long> stats() {
        return service.getStats();
    }
}
