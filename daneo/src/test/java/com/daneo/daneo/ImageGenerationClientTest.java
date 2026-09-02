package com.daneo.daneo;

import com.daneo.daneo.image.client.ImageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ImageGenerationClientTest {

    @Autowired
    private ImageClient imageClient;

    @Test
    void shouldGenerateImage() throws Exception {
        byte[] image = imageClient.generateImage("a simple dog, manga style, no text");

        assertThat(image).isNotEmpty();
        System.out.println("Image size = " + image.length + " bytes");
        Files.write(Path.of("test-java.png"), image);
    }
}