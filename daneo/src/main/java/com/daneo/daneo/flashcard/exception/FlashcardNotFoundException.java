package com.daneo.daneo.flashcard.exception;

import com.daneo.daneo.common.exception.NotFoundException;

public class FlashcardNotFoundException extends NotFoundException {
    public FlashcardNotFoundException(String message) {
        super(message);
    }
}
