package com.daneo.daneo.flashcard.dto;

public record FlashcardSummary(
        Integer id,
        String koreanTerm,
        String romanization,
        String frenchTerm,
        String imagePath
) {}