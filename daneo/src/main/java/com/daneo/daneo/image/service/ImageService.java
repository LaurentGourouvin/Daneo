package com.daneo.daneo.image.service;

import com.daneo.daneo.image.client.ImageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ImageService {

    private final ImageClient openAi;
    private final String promptTemplate;

    public ImageService(ImageClient openAi,
                        @Value("classpath:prompts/generationImage.md") Resource promptResource) throws IOException {
        this.openAi = openAi;
        this.promptTemplate = promptResource.getContentAsString(StandardCharsets.UTF_8);
    }

    private String buildPrompt(String frenchWord, String koreanWord, String meaning, String partOfSpeech) {
        return promptTemplate
                .replace("{{frenchWord}}", frenchWord)
                .replace("{{koreanWord}}", koreanWord)
                .replace("{{meaning}}", meaning)
                .replace("{{partOfSpeech}}", partOfSpeech);
    }

    public byte[] generateIllustration(String frenchWord, String koreanWord, String meaning, String partOfSpeech) {
        String prompt = buildPrompt(frenchWord, koreanWord, meaning, partOfSpeech);
        return openAi.generateImage(prompt);
    }
}
