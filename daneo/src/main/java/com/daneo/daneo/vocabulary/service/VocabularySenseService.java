package com.daneo.daneo.vocabulary.service;

import com.daneo.daneo.vocabulary.domain.FrenchTerm;
import com.daneo.daneo.vocabulary.domain.VocabularySense;
import com.daneo.daneo.vocabulary.dto.VocabularySenseInfos;
import com.daneo.daneo.vocabulary.repository.FrenchTermRepository;
import com.daneo.daneo.vocabulary.repository.VocabularySenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VocabularySenseService {

    private final FrenchTermRepository frenchTermRepository;
    private final VocabularySenseRepository vocabularySenseRepository;

    public VocabularySenseService(FrenchTermRepository frenchTermRepository,
                                  VocabularySenseRepository vocabularySenseRepository) {
        this.frenchTermRepository = frenchTermRepository;
        this.vocabularySenseRepository = vocabularySenseRepository;
    }

    @Transactional
    public VocabularySense findOrCreate(VocabularySenseInfos data) {
        FrenchTerm frenchTerm = frenchTermRepository.findByTerm(data.frenchTerm())
                .orElseGet(() -> frenchTermRepository.save(new FrenchTerm(data.frenchTerm())));

        return vocabularySenseRepository
                .findByFrenchTermIdAndKoreanTerm(frenchTerm.getId(), data.koreanTerm())
                .orElseGet(() -> vocabularySenseRepository.save(new VocabularySense(
                        data.koreanTerm(), data.romanization(), data.partOfSpeech(),
                        data.meaning(), data.imagePath(), frenchTerm
                )));
    }

}
