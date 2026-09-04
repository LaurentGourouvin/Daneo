ALTER TABLE flashcard
DROP CONSTRAINT fk_flashcard_deck;

ALTER TABLE flashcard
    ADD CONSTRAINT fk_flashcard_deck
        FOREIGN KEY (deck_id)
            REFERENCES deck(id)
            ON DELETE SET NULL;