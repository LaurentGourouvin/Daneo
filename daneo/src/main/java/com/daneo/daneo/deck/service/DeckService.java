package com.daneo.daneo.deck.service;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.deck.dto.DeckUpdateRequest;
import com.daneo.daneo.deck.exception.DeckNotFoundException;
import com.daneo.daneo.deck.repository.DeckRepository;
import com.daneo.daneo.deck.dto.DeckCreateRequest;
import com.daneo.daneo.deck.dto.DeckResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeckService {

    private final DeckRepository deckRepository;

    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
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

    private DeckResponse toResponse(Deck deck) {
        return new DeckResponse(deck.getId(), deck.getName(), deck.getDescription(), deck.getCreatedAt(), deck.getUpdatedAt());
    }

    private Deck toEntity(DeckCreateRequest req) {
        return new Deck(req.name(), req.description());
    }
}
