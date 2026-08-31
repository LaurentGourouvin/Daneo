package com.daneo.daneo;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.deck.dto.DeckResponse;
import com.daneo.daneo.deck.dto.DeckUpdateRequest;
import com.daneo.daneo.deck.exception.DeckNotFoundException;
import com.daneo.daneo.deck.repository.DeckRepository;
import com.daneo.daneo.deck.service.DeckService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class DeckRepositoryIntegrationTest {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    @Mock
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
}