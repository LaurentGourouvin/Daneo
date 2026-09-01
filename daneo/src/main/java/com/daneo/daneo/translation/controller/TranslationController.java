package com.daneo.daneo.translation.controller;

import com.daneo.daneo.translation.dto.TranslationRequest;
import com.daneo.daneo.translation.dto.TranslationResponse;
import com.daneo.daneo.translation.service.TranslationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/translate")
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping
    public ResponseEntity<TranslationResponse> translate(@RequestBody @Valid TranslationRequest request) throws JsonProcessingException {
        TranslationResponse translation = translationService.translate(request.frenchTerm());
        return ResponseEntity.ok(translation);
    }
}
