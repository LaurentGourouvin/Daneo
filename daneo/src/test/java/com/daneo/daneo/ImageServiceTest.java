package com.daneo.daneo;

import com.daneo.daneo.image.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    void shouldGenerateIllustrationFromRealPrompt() throws Exception {
        byte[] image = imageService.generateIllustration("pomme", "사과", "fruit", "NOUN");

        assertThat(image).isNotEmpty();
        Files.write(Path.of("illustration-pomme.png"), image);
    }
}