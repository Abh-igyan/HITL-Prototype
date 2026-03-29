package com.dbpedia.HITL.Prototype.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Triple.java
@Entity
@Table(name = "triples")
public class Triple {
    public Long getId() {
        return id;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getSubject() {
        return subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }

    public ValidationStatus getStatus() {
        return status;
    }

    // And the setters if you need to update them later
    public void setStatus(ValidationStatus status) {
        this.status = status;
    }
    public String getCorrectedTriple() {
        return correctedTriple;
    }

    public void setCorrectedTriple(String correctedTriple) {
        this.correctedTriple = correctedTriple;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String originalText;      // raw Hindi Wikipedia sentence

    private String subject;           // extracted S
    private String predicate;         // extracted P
    private String object;            // extracted O

    @Enumerated(EnumType.STRING)
    private ValidationStatus status = ValidationStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String correctedTriple;   // JSON if human edited it

    private String sourceUrl;         // Wikipedia article URL
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum ValidationStatus {
        PENDING, APPROVED, EDITED, REJECTED
    }

    // getters + setters

}
