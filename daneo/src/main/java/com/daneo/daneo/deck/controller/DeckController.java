package com.daneo.daneo.deck.controller;

import com.daneo.daneo.deck.dto.DeckCreateRequest;
import com.daneo.daneo.deck.dto.DeckResponse;
import com.daneo.daneo.deck.service.DeckService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @GetMapping("/{id}")
    public ResponseEntity<DeckResponse> getById(@PathVariable Integer id) {
        DeckResponse deck = deckService.getDeckById(id);
        return ResponseEntity.ok(deck);
    }
}
