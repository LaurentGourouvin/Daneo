package com.daneo.daneo.vocabulary.repository;

import com.daneo.daneo.vocabulary.domain.VocabularySense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VocabularySenseRepository extends JpaRepository<VocabularySense, Integer> {
    Optional<VocabularySense> findByFrenchTermIdAndKoreanTerm(Integer frenchTermId, String korean);
}
