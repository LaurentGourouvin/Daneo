package com.daneo.daneo.translation.client;

import com.daneo.daneo.translation.client.dto.*;
import com.daneo.daneo.translation.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class TranslationClient {
    private final RestClient openAI;
    private static final String OPEN_TEXT_GENERATION_URL = "/responses";
    private final String prompt;
    private final ObjectMapper objectMapper;
    private final JsonNode schema;

    @Value("${daneo.openai.model-text}")
    private String model;

    public TranslationClient(@Qualifier("openAiClient") RestClient openAI,
                             @Value("classpath:prompts/translation.md") Resource promptResource,
                             @Value("classpath:schemas/translation-schema.json") Resource schemaResource,
                             ObjectMapper objectMapper) throws IOException {
        this.openAI = openAI;
        this.prompt = promptResource.getContentAsString(StandardCharsets.UTF_8);
        this.objectMapper = objectMapper;
        this.schema = objectMapper.readTree(schemaResource.getInputStream());
    }

    public TranslationResponse translate(String frenchTerm) throws JsonProcessingException {

        var payload = new OpenAiRequest(model,
                List.of(
                        new OpenAiInput("system", prompt),
                        new OpenAiInput("user", frenchTerm)
                ),
                new OpenAiText(
                        new OpenAiFormat("json_schema", "translation", true, schema)
                )
        );

        // on sérialise manuellement car le convertisseur du RestClient déforme le JsonNode du schéma
        String json = objectMapper.writeValueAsString(payload);

        var response = openAI.post()
                .uri(OPEN_TEXT_GENERATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .retrieve()
                .body(OpenAiResponse.class);

        String rawJson = response.output().stream()
                .filter(item -> "message".equals(item.type()))
                .findFirst()
                .orElseThrow()
                .content().get(0)
                .text();

        return objectMapper.readValue(rawJson, TranslationResponse.class);
    }
}
