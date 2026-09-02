package com.daneo.daneo.flashcard.service;

import com.daneo.daneo.deck.domain.Deck;
import com.daneo.daneo.deck.exception.DeckNotFoundException;
import com.daneo.daneo.deck.repository.DeckRepository;
import com.daneo.daneo.flashcard.domain.Flashcard;
import com.daneo.daneo.flashcard.dto.FlashcardCreateRequest;
import com.daneo.daneo.flashcard.dto.FlashcardSummary;
import com.daneo.daneo.flashcard.repository.FlashcardRepository;
import com.daneo.daneo.image.service.ImageService;
import com.daneo.daneo.image.service.ImageStorageService;
import com.daneo.daneo.romanization.service.RomanizationService;
import com.daneo.daneo.vocabulary.domain.VocabularySense;
import com.daneo.daneo.vocabulary.dto.VocabularySenseInfos;
import com.daneo.daneo.vocabulary.service.VocabularySenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FlashcardCreationService {

    private static final Logger log = LoggerFactory.getLogger(FlashcardCreationService.class);

    private final RomanizationService romanizationService;
    private final ImageService imageService;
    private final VocabularySenseService vocabularyService;
    private final ImageStorageService imageStorageService;
    private final DeckRepository deckRepository;
    private final FlashcardRepository flashcardRepository;

    public FlashcardCreationService(RomanizationService romanizationService, ImageService imageService,
                                    VocabularySenseService vocabularyService, ImageStorageService imageStorageService,
                                    DeckRepository deckRepository, FlashcardRepository flashcardRepository) {
        this.romanizationService = romanizationService;
        this.imageService = imageService;
        this.vocabularyService = vocabularyService;
        this.deckRepository = deckRepository;
        this.flashcardRepository = flashcardRepository;
        this.imageStorageService = imageStorageService;
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

}
