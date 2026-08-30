package com.daneo.daneo.deck.exception;

import com.daneo.daneo.common.exception.NotFoundException;

public class DeckNotFoundException extends NotFoundException {
    public DeckNotFoundException(Integer id) {
        super("Deck not found " + id);
    }
}
