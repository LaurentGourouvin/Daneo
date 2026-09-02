package com.daneo.daneo;

import com.daneo.daneo.image.exception.ImageStorageException;
import com.daneo.daneo.image.service.LocalDiskImageStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalDiskImageStorageServiceTest {

    @TempDir
    Path tempDir;
    private LocalDiskImageStorageService service;

    @BeforeEach
    void setUp() {
        service = new LocalDiskImageStorageService(tempDir.toString(), "http://localhost:8080");
    }

    @Test
    void shouldStoreImageAndReturnWebpReference() {
        String reference = service.store("fake image bytes".getBytes());

        assertThat(reference).endsWith(".webp");
        assertThat(Files.exists(tempDir.resolve(reference))).isTrue();
    }

    @Test
    void shouldRejectEmptyImage() {
        assertThatThrownBy(() -> service.store(new byte[0]))
                .isInstanceOf(ImageStorageException.class);
    }

    @Test
    void storedImageIsAccessibleViaUrl() {
        String reference = service.store("fake".getBytes());
        String url = service.buildUrl(reference);

        assertThat(url).isEqualTo("http://localhost:8080/images/" + reference);
    }
}