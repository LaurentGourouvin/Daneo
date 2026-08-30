package com.daneo.daneo.deck.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.generator.EventType;
import org.hibernate.annotations.Generated;

import java.time.Instant;

@Getter
@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Generated
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Generated(event = {EventType.INSERT, EventType.UPDATE})
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Deck(){}

    public Deck(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
