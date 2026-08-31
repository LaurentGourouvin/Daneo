package com.daneo.daneo.flashcard.domain;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.vocabulary.domain.VocabularySense;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.Instant;

@Entity
@Getter
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "deck_id", nullable = false)
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected Flashcard(){}

    public Flashcard(Deck deck, VocabularySense vocabularySense) {
        this.deck = deck;
        this.vocabularySense = vocabularySense;
    }
}
