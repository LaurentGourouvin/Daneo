package com.daneo.daneo.flashcard.service;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.deck.exception.DeckNotFoundException;
import com.daneo.daneo.deck.repository.DeckRepository;
import com.daneo.daneo.flashcard.domain.Flashcard;
import com.daneo.daneo.flashcard.dto.FlashcardCreateRequest;
import com.daneo.daneo.flashcard.dto.FlashcardResponseDetail;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import com.daneo.daneo.flashcard.exception.FlashcardNotFoundException;
import com.daneo.daneo.flashcard.repository.FlashcardRepository;
import com.daneo.daneo.image.exception.ImageGenerationException;
import com.daneo.daneo.image.service.ImageService;
import com.daneo.daneo.image.service.ImageStorageService;
import com.daneo.daneo.romanization.service.RomanizationService;
import com.daneo.daneo.vocabulary.domain.VocabularySense;
import com.daneo.daneo.vocabulary.dto.VocabularySenseInfos;
import com.daneo.daneo.vocabulary.exception.VocabularySenseNotFoundException;
import com.daneo.daneo.vocabulary.repository.VocabularySenseRepository;
import com.daneo.daneo.vocabulary.service.VocabularySenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class FlashcardService {

    private static final Logger log = LoggerFactory.getLogger(FlashcardService.class);

    private final RomanizationService romanizationService;
    private final ImageService imageService;
    private final VocabularySenseService vocabularyService;
    private final ImageStorageService imageStorageService;
    private final DeckRepository deckRepository;
    private final FlashcardRepository flashcardRepository;
    private final VocabularySenseRepository vocabularySenseRepository;

    public FlashcardService(RomanizationService romanizationService, ImageService imageService,
                            VocabularySenseService vocabularyService, ImageStorageService imageStorageService,
                            DeckRepository deckRepository, FlashcardRepository flashcardRepository, VocabularySenseRepository vocabularySenseRepository) {
        this.romanizationService = romanizationService;
        this.imageService = imageService;
        this.vocabularyService = vocabularyService;
        this.deckRepository = deckRepository;
        this.flashcardRepository = flashcardRepository;
        this.imageStorageService = imageStorageService;
        this.vocabularySenseRepository = vocabularySenseRepository;
    }

    public FlashcardSummary create(FlashcardCreateRequest request) {
        Deck deck = deckRepository.findById(request.deckId()).orElseThrow(() -> new DeckNotFoundException(request.deckId()));
        String romanized = romanizationService.romanize(request.koreanTerm());
        String imagePath;

        if (request.generateImage()) {
            try {
                byte[] generatedImage = imageService.generateIllustration(request.frenchWord(),
                        request.koreanTerm(), request.meaning(), request.partOfSpeech().toString());

                byte[] optimizedImage = imageService.resizeImage(generatedImage);

                imagePath = imageStorageService.store(optimizedImage);
            } catch (Exception e) {
                log.warn("Image generation failed for {}, creating card without image", request.frenchWord(), e);
                imagePath = null;
            }
        } else {
            imagePath = null;
        }

        VocabularySenseInfos senseData = new VocabularySenseInfos(request.frenchWord(), request.koreanTerm(), romanized,
                request.meaning(), request.partOfSpeech(), imagePath);

        VocabularySense vocabulary = vocabularyService.findOrCreate(senseData);

        Flashcard flashcard = flashcardRepository.save(new Flashcard(deck, vocabulary));

        String imageUrl = imagePath != null ? imageStorageService.buildUrl(imagePath) : null;

        return new FlashcardSummary(flashcard.getId(), vocabulary.getKoreanTerm(), romanized, request.frenchWord(), imageUrl);
    }

    @Transactional(readOnly = true)
    public FlashcardResponseDetail getById(Integer id) {
        Flashcard card = flashcardRepository.findById(id)
                .orElseThrow(() -> new FlashcardNotFoundException("Flashcard not found id: " + id));

        return toFlashCardResponseDetail(card);
    }

    private FlashcardResponseDetail toFlashCardResponseDetail(Flashcard card) {
        VocabularySense sense = card.getVocabularySense();

        return new FlashcardResponseDetail(
                card.getId(),
                sense.getKoreanTerm(),
                sense.getRomanization(),
                sense.getFrenchTerm().getTerm(),
                sense.getMeaning(),
                sense.getPartOfSpeech(),
                sense.getImagePath() != null ? imageStorageService.buildUrl(sense.getImagePath()) : null,
                card.getReviewCount(),
                card.getLastReviewedAt(),
                card.getNextReviewAt(),
                card.getCreatedAt()
        );
    }

    @Transactional
    public void deleteById(Integer id) {
        Flashcard card = flashcardRepository.findById(id)
                .orElseThrow(() -> new FlashcardNotFoundException("Flashcard not found :" + id));

        VocabularySense voca = card.getVocabularySense();

        flashcardRepository.delete(card);
        flashcardRepository.flush();

        List<Flashcard> list = flashcardRepository.findFlashcardByVocabularySense(voca);

        if (list.isEmpty()) {
            if (voca.getImagePath() != null) {
                imageStorageService.delete(voca.getImagePath());
            }
            vocabularySenseRepository.delete(voca);
        }
    }

    @Transactional
    public void regenerateImage(Integer id) {
        Flashcard card = flashcardRepository.findById(id)
                .orElseThrow(() -> new FlashcardNotFoundException("Flashcard not found : " + id));

        VocabularySense voca = card.getVocabularySense();

        String oldImagePath = voca.getImagePath();

        try {
            byte[] newImage = imageService.generateIllustration(voca.getFrenchTerm().getTerm(), voca.getKoreanTerm(),
                    voca.getMeaning(), voca.getPartOfSpeech().toString());

            if (newImage.length == 0) {
                throw new ImageGenerationException("Image generation failed");
            }

            byte[] optimizedImage = imageService.resizeImage(newImage);

            String imagePath = imageStorageService.store(optimizedImage);

            voca.updateImagePath(imagePath);
            vocabularySenseRepository.flush();

            if (oldImagePath != null) {
                imageStorageService.delete(oldImagePath);
            }

        } catch (IOException e) {
            throw new ImageGenerationException("Image regeneration failed for card " + id, e);
        }
    }
}
