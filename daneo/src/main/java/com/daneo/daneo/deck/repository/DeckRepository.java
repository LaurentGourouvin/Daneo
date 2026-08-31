package com.daneo.daneo.deck.repository;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Integer> {
}
