package com.daneo.daneo.vocabulary.dto;

import com.daneo.daneo.vocabulary.enums.PartOfSpeech;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VocabularySenseInfos(
        @NotBlank String frenchTerm,
        @NotBlank String koreanTerm,
        @NotBlank String romanization,
        @NotBlank String meaning,
        @NotNull PartOfSpeech partOfSpeech,
        String imagePath
) {
}
