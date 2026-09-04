package com.daneo.daneo.deck.dto;

import java.time.Instant;

public record DeckResponse(
        Integer id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
