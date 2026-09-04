package com.daneo.daneo.deck.dto;

import com.daneo.daneo.flashcard.dto.FlashcardSummary;

import java.time.Instant;
import java.util.List;

public record DeckDetailResponse(
        Integer id,
        String name,
        String description,
        List<FlashcardSummary> cards,
        Instant createdAt,
        Instant updatedAt
) {
}