CREATE TABLE french_term
(
    id         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    term       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_french_term_term UNIQUE (term)
);

CREATE TABLE vocabulary_sense
(
    id             INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    korean_term    VARCHAR(100) NOT NULL,
    romanization   VARCHAR(100) NOT NULL,
    part_of_speech VARCHAR(30)  NOT NULL, -- catégorie grammaticale
    meaning        VARCHAR(255) NOT NULL,
    example_korean VARCHAR(255),
    example_french VARCHAR(255),
    image_path     VARCHAR(255),
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    french_term_id INTEGER NOT NULL,

    CONSTRAINT fk_vocabulary_sense_french_term
        FOREIGN KEY (french_term_id)
            REFERENCES french_term (id)
);

CREATE TABLE deck
(
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE flashcard
(
    id                  INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    deck_id             INTEGER NOT NULL,
    vocabulary_sense_id INTEGER NOT NULL,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_flashcard_deck
        FOREIGN KEY (deck_id)
            REFERENCES deck (id),

    CONSTRAINT fk_flashcard_vocabulary_sense
        FOREIGN KEY (vocabulary_sense_id)
            REFERENCES vocabulary_sense (id)
);