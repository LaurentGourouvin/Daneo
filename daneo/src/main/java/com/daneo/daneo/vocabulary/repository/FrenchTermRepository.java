package com.daneo.daneo.vocabulary.repository;

import com.daneo.daneo.vocabulary.domain.FrenchTerm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FrenchTermRepository extends JpaRepository<FrenchTerm, Integer> {
    Optional<FrenchTerm> findByTerm(String frenchTerm);
}
