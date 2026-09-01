package com.daneo.daneo;

import com.daneo.daneo.image.exception.ImageStorageException;
import com.daneo.daneo.image.service.LocalDiskImageStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LocalDiskImageStorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldStoreImageAndReturnWebpReference() {
        LocalDiskImageStorageService service =
                new LocalDiskImageStorageService(tempDir.toString());

        String reference = service.store("fake image bytes".getBytes());

        assertThat(reference).endsWith(".webp");
        assertThat(Files.exists(tempDir.resolve(reference))).isTrue();
    }

    @Test
    void shouldRejectEmptyImage() {
        LocalDiskImageStorageService service =
                new LocalDiskImageStorageService(tempDir.toString());

        assertThatThrownBy(() -> service.store(new byte[0]))
                .isInstanceOf(ImageStorageException.class);
    }
}