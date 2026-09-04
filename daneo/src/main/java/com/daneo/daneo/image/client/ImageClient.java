package com.daneo.daneo.image.client;

import com.daneo.daneo.image.client.dto.ImageRequest;
import com.daneo.daneo.image.client.dto.ImageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Base64;

@Component
public class ImageClient {

    private final RestClient openAi;
    private static final String OPEN_IMAGE_GENERATION_URL = "/images/generations";
    private static final String size = "1024x1024";
    private static final String quality = "medium";
    private static final String background = "transparent";
    private static final String format = "png";

    @Value("${daneo.openai.model-image}")
    private String model;

    public ImageClient(@Qualifier("openAiImageClient") RestClient openAi) {
        this.openAi = openAi;
    }

    public byte[] generateImage(String prompt) {

        var payload = new ImageRequest(model, prompt, 1, size, quality, background, format);

        var response = openAi.post()
                .uri(OPEN_IMAGE_GENERATION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(ImageResponse.class);

        String base64 = response.data().get(0).b64Json();

        return Base64.getDecoder().decode(base64);
    }
}
