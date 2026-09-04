package com.daneo.daneo.flashcard.repository;

import com.daneo.daneo.flashcard.domain.Flashcard;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import com.daneo.daneo.vocabulary.domain.VocabularySense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
    @Query("""
            select new com.daneo.daneo.flashcard.dto.FlashcardSummary(
                f.id, s.koreanTerm, s.romanization, t.term, s.imagePath)
            from Flashcard f
            join f.vocabularySense s
            join s.frenchTerm t
            where f.deck.id = :deckId
            order by f.createdAt
            """)
    List<FlashcardSummary> findSummariesByDeckId(Integer deckId);

    List<Flashcard> findFlashcardByVocabularySense(VocabularySense voca);
}
