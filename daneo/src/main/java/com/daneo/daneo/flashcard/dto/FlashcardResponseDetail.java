package com.daneo.daneo.flashcard.dto;

import com.daneo.daneo.vocabulary.enums.PartOfSpeech;

import java.time.Instant;

public record FlashcardResponseDetail(
        Integer id, String koreanTerm, String romanization, String frenchTerm, String meaning,
        PartOfSpeech partOfSpeech, String imageUrl,
        Integer reviewCount, Instant lastReviewedAt, Instant nextReviewAt, Instant createdAt) {
}
