package com.daneo.daneo.translation.service;

import com.daneo.daneo.config.OpenAiConfig;
import com.daneo.daneo.translation.client.TranslationClient;
import com.daneo.daneo.translation.dto.TranslationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final TranslationClient openAi;

    public TranslationService(TranslationClient openAi) {
        this.openAi = openAi;
    }

    public TranslationResponse translate(String frenchWord) throws JsonProcessingException {
        return openAi.translate(frenchWord);
    }
}
