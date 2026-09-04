package com.daneo.daneo.flashcard.dto;

import com.daneo.daneo.vocabulary.enums.PartOfSpeech;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FlashcardCreateRequest(
        @NotNull @Positive Integer deckId,
        @NotBlank String frenchWord,
        @NotBlank String koreanTerm,
        @NotNull PartOfSpeech partOfSpeech,
        @NotBlank String meaning,
        boolean generateImage
) {}
