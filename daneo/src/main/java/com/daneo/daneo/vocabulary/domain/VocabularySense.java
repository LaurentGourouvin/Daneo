package com.daneo.daneo.vocabulary.domain;

import com.daneo.daneo.vocabulary.enums.PartOfSpeech;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "vocabulary_sense")
@Getter
public class VocabularySense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "korean_term", nullable = false, length = 100)
    private String koreanTerm;

    @Column(nullable = false, length = 100)
    private String romanization;

    @Enumerated(EnumType.STRING)
    @Column(name = "part_of_speech", nullable = false, length = 30)
    private PartOfSpeech partOfSpeech;

    @Column(nullable = false)
    private String meaning;

    @Column(name = "example_korean")
    private String exampleKorean;

    @Column(name = "example_french")
    private String exampleFrench;

    @Column(name = "image_path")
    private String imagePath;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "french_term_id", nullable = false)
    private FrenchTerm frenchTerm;

    protected VocabularySense() {
    }

    public VocabularySense(String koreanTerm, String romanization, PartOfSpeech partOfSpeech, String meaning,
                           String exampleKorean, String exampleFrench, String imagePath,
                           FrenchTerm frenchTerm) {
        this.koreanTerm = koreanTerm;
        this.romanization = romanization;
        this.partOfSpeech = partOfSpeech;
        this.meaning = meaning;
        this.exampleKorean = exampleKorean;
        this.exampleFrench = exampleFrench;
        this.imagePath = imagePath;
        this.frenchTerm = frenchTerm;
    }

    public VocabularySense(String koreanTerm, String romanization, PartOfSpeech partOfSpeech, String meaning,
                           String imagePath, FrenchTerm frenchTerm) {
        this.koreanTerm = koreanTerm;
        this.romanization = romanization;
        this.partOfSpeech = partOfSpeech;
        this.meaning = meaning;
        this.imagePath = imagePath;
        this.frenchTerm = frenchTerm;
    }

    public void updateImagePath (String path) {
        imagePath = path;
    }
}
