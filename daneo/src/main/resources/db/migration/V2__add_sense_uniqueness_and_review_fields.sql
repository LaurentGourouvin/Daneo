-- Enforce sense reuse: a given Korean translation of a French term must exist only once.
-- Because a vocabulary_sense carries the romanization and the generated image, sharing a
-- single sense across multiple flashcards means the image is generated once and reused,
-- which keeps AI/image costs down and lays the groundwork for future duplicate detection.
-- The key is (french_term_id, korean_term) — the validated Korean identifies the sense;
-- `meaning` is an editable, AI-generated label and would be a poor uniqueness key.
ALTER TABLE vocabulary_sense
    ADD CONSTRAINT uk_vocabulary_sense UNIQUE (french_term_id, korean_term);

-- Review fields live on the flashcard (not the sense): the same sense reused in two decks
-- must track its progress independently in each. Added now, even though spaced repetition
-- (SRS) is out of MVP scope, so enabling SRS later won't require a heavy migration on a
-- table that already holds data.
ALTER TABLE flashcard
    ADD COLUMN review_count     INTEGER NOT NULL DEFAULT 0,
    ADD COLUMN last_reviewed_at TIMESTAMP WITH TIME ZONE,
    ADD COLUMN next_review_at   TIMESTAMP WITH TIME ZONE;