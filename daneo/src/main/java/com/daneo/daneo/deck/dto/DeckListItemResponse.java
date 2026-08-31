package com.daneo.daneo.deck.dto;

public record DeckListItemResponse(
        Integer id,
        String name,
        String description,
        long cardCount
) {}