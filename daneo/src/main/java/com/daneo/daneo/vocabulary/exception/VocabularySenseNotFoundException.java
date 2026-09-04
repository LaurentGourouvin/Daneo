package com.daneo.daneo.vocabulary.exception;

import com.daneo.daneo.common.exception.NotFoundException;

public class VocabularySenseNotFoundException extends NotFoundException {
    public VocabularySenseNotFoundException(String message) {
        super(message);
    }
}
