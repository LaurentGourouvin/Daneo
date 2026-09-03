package com.daneo.daneo.flashcard.controller;

import com.daneo.daneo.flashcard.dto.FlashcardCreateRequest;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import com.daneo.daneo.flashcard.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public ResponseEntity<FlashcardSummary> create(@RequestBody @Valid FlashcardCreateRequest request) {
        FlashcardSummary flashcard = flashcardService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(flashcard.id())
                .toUri();
        return ResponseEntity.created(location).body(flashcard);
    }
}
