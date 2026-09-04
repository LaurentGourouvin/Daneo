package com.daneo.daneo.image.service;

import com.daneo.daneo.flashcard.service.FlashcardService;
import com.daneo.daneo.image.exception.ImageStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class LocalDiskImageStorageService implements ImageStorageService {

    private final Path storageDirectory;
    private final String publicBaseUrl;
    private static final Logger log = LoggerFactory.getLogger(LocalDiskImageStorageService.class);

    public LocalDiskImageStorageService(@Value("${daneo.image.storage-path}") String storagePath,
                                        @Value("${daneo.image.public-base-url}") String publicBaseUrl) {

        this.storageDirectory = Path.of(storagePath);
        this.publicBaseUrl = publicBaseUrl;

        try {
            Files.createDirectories(this.storageDirectory);
        } catch (IOException e) {
            throw new ImageStorageException("Cannot create image storage directory: " + storagePath, e);
        }
    }

    @Override
    public String store(byte[] image) {

        if(image == null || image.length == 0) {
            throw new ImageStorageException("Image content is empty.");
        }

        try {
            String filename = UUID.randomUUID() + ".webp";

            Path path = storageDirectory.resolve(filename);
            Files.write(path, image);

            return filename;
        } catch (IOException e) {
            throw new ImageStorageException("Cannot write/store the image.", e);
        }
    }

    @Override
    public String buildUrl(String reference) {
        return publicBaseUrl + "/images/" + reference;
    }

    @Override
    public boolean delete(String reference) {
        try {
            Path path = storageDirectory.resolve(reference);
            Files.deleteIfExists(path);
            return true;
        } catch (IOException e) {
            log.warn("Cannot delete the image." + reference);
        }
        return false;
    }
}
