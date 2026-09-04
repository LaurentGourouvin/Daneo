package com.daneo.daneo.deck.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeckUpdateRequest(
        @NotBlank @Size(max = 100)
        String name
) {
}
