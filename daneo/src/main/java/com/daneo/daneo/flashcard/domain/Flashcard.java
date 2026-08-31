package com.daneo.daneo.flashcard.domain;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.vocabulary.domain.VocabularySense;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;


import java.time.Instant;

@Entity
@Getter
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vocabulary_sense_id", nullable = false)
    private VocabularySense vocabularySense;

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount = 0;

    @Column(name = "last_reviewed_at")
    private Instant lastReviewedAt;

    @Column(name = "next_review_at")
    private Instant nextReviewAt;

    @Generated
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Generated(event = {EventType.INSERT, EventType.UPDATE})
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Flashcard(){}
}
