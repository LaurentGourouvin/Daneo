package com.daneo.daneo.flashcard.controller;

import com.daneo.daneo.flashcard.dto.FlashcardCreateRequest;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import com.daneo.daneo.flashcard.service.FlashcardCreationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final FlashcardCreationService flashcardService;

    public FlashcardController(FlashcardCreationService flashcardService) {
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
