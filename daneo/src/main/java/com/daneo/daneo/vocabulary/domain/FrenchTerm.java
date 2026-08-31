package com.daneo.daneo.vocabulary.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.time.Instant;

@Entity
@Table(name = "french_term")
@Getter
public class FrenchTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String term;

    @Generated(event = {EventType.INSERT})
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected FrenchTerm(){}

    public FrenchTerm(String term) {
        this.term = term;
    }
}
