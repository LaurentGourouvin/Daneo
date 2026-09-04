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

    @Test
    void shouldResizeAndConvertToWebp() throws Exception {
        byte[] original = imageService.generateIllustration("pomme", "사과", "fruit", "NOUN");
        System.out.println("Original PNG = " + original.length + " bytes");

        byte[] optimized = imageService.resizeImage(original);
        System.out.println("Optimized WebP = " + optimized.length + " bytes");

        Files.write(Path.of("optimized-pomme.webp"), optimized);

        assertThat(optimized).isNotEmpty();
        assertThat(optimized.length).isLessThan(original.length);
    }
}