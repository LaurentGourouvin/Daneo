package com.daneo.daneo;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.deck.dto.DeckDetailResponse;
import com.daneo.daneo.deck.dto.DeckResponse;
import com.daneo.daneo.deck.dto.DeckUpdateRequest;
import com.daneo.daneo.deck.exception.DeckNotFoundException;
import com.daneo.daneo.deck.repository.DeckRepository;
import com.daneo.daneo.deck.service.DeckService;
import com.daneo.daneo.flashcard.domain.Flashcard;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import com.daneo.daneo.flashcard.repository.FlashcardRepository;
import com.daneo.daneo.vocabulary.domain.FrenchTerm;
import com.daneo.daneo.vocabulary.domain.VocabularySense;
import com.daneo.daneo.vocabulary.enums.PartOfSpeech;
import com.daneo.daneo.vocabulary.repository.FrenchTermRepository;
import com.daneo.daneo.vocabulary.repository.VocabularySenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class DeckRepositoryIntegrationTest {

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private FlashcardRepository flashcardRepository;
    @Autowired
    private FrenchTermRepository frenchTermRepository;
    @Autowired
    private VocabularySenseRepository vocabularySenseRepository;
    @Autowired
    private DeckService deckService;

    @Test
    void shouldPersistAndReadDeck() {
        Deck saved = deckRepository.save(
                new Deck("Korean basics", "Basic Korean vocabulary")
        );

        Deck found = deckRepository.findById(saved.getId())
                .orElseThrow();

        assertThat(found.getName()).isEqualTo("Korean basics");
        assertThat(found.getDescription()).isEqualTo("Basic Korean vocabulary");
    }

    @Test
    void shouldDeleteDeck() {
        Deck saved = deckRepository.save(
                new Deck("Korean basics", "Basic Korean vocabulary")
        );

        deckService.deleteDeck(saved.getId());

        assertThatThrownBy(() -> deckService.getDeckById(saved.getId()))
                .isInstanceOf(DeckNotFoundException.class);
    }

    @Test
    void shouldRenameDeck() {
        Deck saved = deckRepository.save(
                new Deck("Korean basics", "Basic Korean vocabulary")
        );
        DeckResponse renamed = deckService.updateDeck(saved.getId(), new DeckUpdateRequest("Rename deck"));
        assertThat(renamed.name()).isEqualTo("Rename deck");
    }

    @Test
    void shouldReturnDeckDetailWithEmptyCardsWhenNoFlashcards() {
        Deck saved = deckRepository.save(new Deck("Nourriture", "Vocabulaire nourriture"));

        DeckDetailResponse detail = deckService.getDeckWithCards(saved.getId());

        assertThat(detail.id()).isEqualTo(saved.getId());
        assertThat(detail.name()).isEqualTo("Nourriture");
        assertThat(detail.cards()).isEmpty();
    }

    @Test
    void shouldThrowWhenDeckDoesNotExist() {
        assertThatThrownBy(() -> deckService.getDeckWithCards(999_999))
                .isInstanceOf(DeckNotFoundException.class);
    }

    @Test
    void shouldReturnDeckDetailWithCardSummaries() {
        Deck deck = deckRepository.save(new Deck("Nourriture", null));

        FrenchTerm avocat = frenchTermRepository.save(new FrenchTerm("avocat"));
        VocabularySense sense = vocabularySenseRepository.save(new VocabularySense(
                "아보카도", "abokado", PartOfSpeech.NOUN, "fruit",
                null, null, "avocat.webp", avocat));
        flashcardRepository.save(new Flashcard(deck, sense));

        DeckDetailResponse detail = deckService.getDeckWithCards(deck.getId());

        assertThat(detail.cards()).hasSize(1);
        FlashcardSummary card = detail.cards().get(0);
        assertThat(card.koreanTerm()).isEqualTo("아보카도");
        assertThat(card.romanization()).isEqualTo("abokado");
        assertThat(card.frenchTerm()).isEqualTo("avocat");
        assertThat(card.imagePath()).isEqualTo("avocat.webp");
    }
}