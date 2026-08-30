package com.daneo.daneo;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.deck.repository.DeckRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class DeckRepositoryIntegrationTest {

    @Autowired
    private DeckRepository deckRepository;

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
}