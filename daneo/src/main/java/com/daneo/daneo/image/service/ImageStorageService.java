package com.daneo.daneo.image.service;

import com.daneo.daneo.image.exception.ImageStorageException;

/**
 * Stores flashcard illustrations and exposes them through a stable reference.
 * <p>
 * Implementations decide where and how images are persisted (local disk,
 * S3-compatible object storage, etc.). Callers depend only on this contract
 * and never on a concrete storage mechanism.
 */
public interface ImageStorageService {

    /**
     * Stores the given image and returns a unique reference to it.
     *
     * @param image the raw bytes of the image to store (must not be null or empty)
     * @return a unique, stable reference identifying the stored image;
     *         this same value can later be passed to {@link #buildUrl(String)}}
     * @throws ImageStorageException if the image cannot be stored
     */
    String store(byte[] image);

    /**
     * Builds the publicly accessible URL for a previously stored image.
     *
     * @param reference a reference returned by {@link #store(byte[])}
     * @return the URL through which the image can be retrieved
     */
    String buildUrl(String reference);

    boolean delete(String reference);
}