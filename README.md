# Daneo — 단어
<p align="center">
    <img src="./docs/daneo-logo.png" width="500px">
</p>
<p align="center">
    <img src="./docs/daneo-marketing.png" width="700px">
</p>

> Apprends le vocabulaire coréen, un mot à la fois.

**Daneo** est une application web/mobile-first d'apprentissage du vocabulaire coréen à partir de flashcards générées avec l'aide de l'IA. L'utilisateur saisit un mot en français ; l'application propose sa traduction coréenne, calcule sa romanisation et peut générer une illustration. Après validation, la carte est sauvegardée et devient révisable.

Le nom vient du coréen **단어 (*daneo*)**, « le mot ».

---

## Idée en une phrase

Créer une flashcard doit être quasi instantané, et l'IA ne coûte qu'au moment de la création — les consultations et révisions n'appellent jamais l'IA.

## Aperçu du parcours

```
Saisie « pomme »
      ↓  (IA : Structured Output)
사과 / NOUN / « fruit »
      ↓  (backend, déterministe)
romanisation → sagwa
      ↓  (validation utilisateur)
illustration facultative (~5 s)
      ↓
carte sauvegardée → révisable
```

L'IA ne sauvegarde jamais une traduction sans validation. Les mots ambigus (`avocat` → 변호사 *profession* / 아보카도 *fruit*) sont gérés explicitement.

---

## Fonctionnalités (MVP)

- Création et consultation de decks de vocabulaire
- Saisie d'un mot français → traduction coréenne proposée par l'IA
- Romanisation calculée automatiquement côté backend (Revised Romanization)
- Choix du sens lorsqu'un mot français est ambigu
- Validation / correction de la proposition avant sauvegarde
- Génération d'illustration **facultative** (qualité Medium, optimisée en WebP 400×400)
- Session de révision : afficher une carte, révéler la réponse
- États d'erreur IA non bloquants (une carte reste utilisable sans image)

Volontairement hors MVP (prévu ensuite) : répétition espacée (SRS), phrase d'exemple, statistiques, audio, import en lot, détection de doublons.

---

## Stack technique

| Couche | Choix |
|---|---|
| Frontend | Vue 3 (mobile-first, ~390 px de référence) |
| Backend | Java 25 LTS · Spring Boot 4.1 (Spring Framework 7, Hibernate 7) |
| Base de données | PostgreSQL |
| Persistance | Spring Data JPA · Flyway (migrations) |
| Intégration LLM | API OpenAI via `RestClient` + Structured Outputs (JSON Schema) |
| Génération d'images | API image OpenAI |
| Traitement d'image | Scrimage (redimensionnement + encodage WebP) |
| Romanisation | KOROMAN, ou implémentation maison (algorithme RR) en repli |
| Stockage des images | Disque du VPS (abstraction `ImageStorageService`, prête pour un objet type S3/R2) |

Les images ne sont **pas** stockées en base : PostgreSQL ne conserve que leur chemin/URL.

---

## Architecture — monolithe modulaire

Pas de microservices, pas de Kafka, pas d'architecture distribuée. Un seul déployable, découpé en domaines aux frontières claires (au niveau **package**, pas en modules de build) :

```
com.daneo
├── deck          Gestion des decks
├── flashcard     Cartes + vocabulaire (mot / sens)
├── translation   Intégration LLM, Structured Outputs
├── romanization  KOROMAN / implémentation maison
├── image         Génération, optimisation, stockage
├── review        Sessions de révision (SRS à venir)
└── common        Configuration, gestion d'erreurs, utilitaires
```

Chaque domaine expose une API claire et ne dépend pas des entités internes des autres. Découpage architectural classique par module : `Controller → Service → Repository`.

---

## Démarrage

### Prérequis

- JDK 25
- PostgreSQL 16+
- Node.js 20+ (frontend Vue 3)
- Une clé API OpenAI

### Configuration

Variables d'environnement attendues côté backend :

```bash
DANEO_DB_URL=jdbc:postgresql://localhost:5432/daneo
DANEO_DB_USERNAME=daneo
DANEO_DB_PASSWORD=change-me
OPENAI_API_KEY=sk-...
DANEO_IMAGE_STORAGE_PATH=/var/daneo/images
```

### Lancer le backend

```bash
./mvnw spring-boot:run
```

Les migrations Flyway s'appliquent au démarrage.

### Lancer le frontend

```bash
cd frontend
npm install
npm run dev
```

---

## Structure du dépôt

```
daneo/
├── backend/        Application Spring Boot
├── frontend/       Application Vue 3
├── docs/           Cahier des charges, maquettes, prompts IA
└── README.md
```

---

## Feuille de route

- [x] Cahier des charges & maquettes
- [ ] MVP : decks, création de cartes, révision simple
- [ ] Répétition espacée (SRS) + notation Facile / Moyen / Difficile
- [ ] Phrase d'exemple générée
- [ ] Statistiques d'apprentissage
- [ ] Détection de doublons
- [ ] Prononciation audio
- [ ] Migration du stockage d'images vers un objet type Cloudflare R2

---

## Objectif du projet

Daneo est un projet personnel dont le but est double : disposer d'un outil réellement utilisable au quotidien pour apprendre le coréen, et travailler proprement une API REST Spring Boot, l'intégration d'un LLM avec Structured Outputs, la génération et l'optimisation d'images, et une application vraiment mobile-first.
