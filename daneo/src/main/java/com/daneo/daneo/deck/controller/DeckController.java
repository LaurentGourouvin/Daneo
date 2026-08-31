package com.daneo.daneo.deck.controller;

import com.daneo.daneo.deck.dto.*;
import com.daneo.daneo.deck.service.DeckService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping
    public ResponseEntity<DeckResponse> create(@RequestBody @Valid DeckCreateRequest request) {
        DeckResponse deck = deckService.createDeck(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(deck.id())
                .toUri();
        return ResponseEntity.created(location).body(deck);
    }

    @GetMapping
    public ResponseEntity<List<DeckListItemResponse>> list() {
        List<DeckListItemResponse> decks = deckService.list();
        return ResponseEntity.ok(decks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckResponse> getById(@PathVariable Integer id) {
        DeckResponse deck = deckService.getDeckById(id);
        return ResponseEntity.ok(deck);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<DeckDetailResponse> deckDetail(@PathVariable Integer id) {
        DeckDetailResponse deck = deckService.getDeckWithCards(id);
        return ResponseEntity.ok(deck);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DeckResponse> updateDeck(@PathVariable Integer id, @RequestBody @Valid DeckUpdateRequest request) {
        DeckResponse deck = deckService.updateDeck(id, request);
        return ResponseEntity.ok(deck);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        deckService.deleteDeck(id);
        return ResponseEntity.noContent().build();
    }
}
