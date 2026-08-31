package com.daneo.daneo.deck.service;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.deck.dto.DeckDetailResponse;
import com.daneo.daneo.deck.dto.DeckUpdateRequest;
import com.daneo.daneo.deck.exception.DeckNotFoundException;
import com.daneo.daneo.deck.repository.DeckRepository;
import com.daneo.daneo.deck.dto.DeckCreateRequest;
import com.daneo.daneo.deck.dto.DeckResponse;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import com.daneo.daneo.flashcard.repository.FlashcardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final FlashcardRepository flashcardRepository; // Trouver une autre solution pour éviter ce couplage

    public DeckService(DeckRepository deckRepository, FlashcardRepository flashcardRepository) {
        this.deckRepository = deckRepository;
        this.flashcardRepository = flashcardRepository;
    }

    @Transactional(readOnly = true)
    public DeckResponse getDeckById(Integer id) {
        Deck deck = deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException(id));
        return toResponse(deck);
    }

    @Transactional
    public DeckResponse createDeck(DeckCreateRequest req) {
        Deck deck = deckRepository.save(toEntity(req));
        return toResponse(deck);
    }

    @Transactional
    public void deleteDeck(Integer id) {
        Deck deck = deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException(id));
        deckRepository.delete(deck);
    }

    @Transactional
    public DeckResponse updateDeck(Integer id, DeckUpdateRequest request) {
        Deck deck = deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException(id));
        deck.renameDeck(request.name());
        return toResponse(deck);
    }

    @Transactional(readOnly = true)
    public DeckDetailResponse getDeckWithCards(Integer id) {
        Deck deck = deckRepository.findById(id).orElseThrow(() -> new DeckNotFoundException(id));
        List<FlashcardSummary> flashcards = flashcardRepository.findSummariesByDeckId(id);
        return new DeckDetailResponse(
                deck.getId(), deck.getName(), deck.getDescription(),
                flashcards, deck.getCreatedAt(), deck.getUpdatedAt());
    }

    private DeckResponse toResponse(Deck deck) {
        return new DeckResponse(deck.getId(), deck.getName(), deck.getDescription(), deck.getCreatedAt(), deck.getUpdatedAt());
    }

    private Deck toEntity(DeckCreateRequest req) {
        return new Deck(req.name(), req.description());
    }
}
