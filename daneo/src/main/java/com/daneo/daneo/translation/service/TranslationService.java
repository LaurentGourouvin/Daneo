package com.daneo.daneo.translation.service;

import com.daneo.daneo.translation.client.TranslationClient;
import com.daneo.daneo.translation.dto.TranslationResponse;
import com.daneo.daneo.translation.exception.MalformedJsonTranslation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final TranslationClient openAi;

    public TranslationService(TranslationClient openAi) {
        this.openAi = openAi;
    }

    public TranslationResponse translate(String frenchWord) throws JsonProcessingException {
        TranslationResponse translation = openAi.translate(frenchWord);

        boolean hasInvalidTranslation = translation.translations().stream()
                .anyMatch(t -> t.korean() == null || t.meaning() == null || t.partOfSpeech() == null);

        if (hasInvalidTranslation) {
            throw new MalformedJsonTranslation("Malformed JSON result for the translation of " + frenchWord);
        }

        return translation;
    }
}
