# Daneo — Application de flashcards de vocabulaire coréen assistée par IA

## Présentation

**Daneo** est une application web/mobile-first destinée à faciliter l'apprentissage du vocabulaire coréen à partir de flashcards générées avec l'aide de l'intelligence artificielle.

L'objectif est de rendre la création de cartes extrêmement rapide : l'utilisateur saisit simplement un mot en français et l'application se charge de proposer sa traduction en coréen ainsi qu'une illustration permettant d'associer visuellement le mot à sa signification.

L'application doit rester simple, rapide et agréable à utiliser depuis un smartphone.

---

## Fonctionnement principal

L'utilisateur saisit un mot français, par exemple :

**pomme**

L'application utilise ensuite l'IA pour proposer :

* le mot français : `pomme`
* sa traduction coréenne : `사과`
* sa catégorie grammaticale : `nom`
* éventuellement une phrase d'exemple en coréen
* la traduction française de cette phrase
* un prompt adapté à la génération d'une illustration

La **romanisation** (`sagwa`) n'est **pas** demandée à l'IA : elle est calculée côté backend à partir du coréen validé (voir section *Romanisation*).

Exemple :

**사과를 먹어요.**
Je mange une pomme.

Une image représentant clairement une pomme peut ensuite être générée par un modèle de génération d'images en qualité **Medium**. La génération d'image est **optionnelle** (voir section *Génération des images*).

L'utilisateur peut vérifier et modifier les informations proposées avant de créer définitivement la flashcard.

---

## Workflow de création

```text
Saisie du mot français
        ↓
      "pomme"
        ↓
Génération IA de la traduction
        ↓
사과 / nom / exemple  (via Structured Output)
        ↓
Romanisation calculée côté backend
        ↓
사과 → sagwa
        ↓
Validation ou modification par l'utilisateur
        ↓
Génération de l'image  (optionnelle)
        ↓
Illustration d'une pomme
        ↓
Création de la flashcard
        ↓
Sauvegarde
```

L'IA ne doit donc pas enregistrer automatiquement une traduction sans validation utilisateur.

Cela permet notamment de gérer les mots français ayant plusieurs sens.

Exemple :

`avocat`

peut correspondre à :

* `변호사` → avocat, profession
* `아보카도` → avocat, fruit

---

## Modèle conceptuel : mot / sens / carte

Un mot français **n'est pas une clé unique** (cf. `avocat` ci-dessus). Pour éviter un refactoring douloureux quand viendront la détection de doublons et le choix entre traductions (voir *Évolutions*), on distingue dès la conception trois notions :

* **Mot (terme français)** — la chaîne saisie, par ex. `avocat`.
* **Sens / traduction** — un couple (mot français, traduction coréenne) porteur d'un sens précis, par ex. `avocat → 아보카도 (fruit)`.
* **Carte (flashcard)** — l'objet révisable, rattaché à un sens et à un deck, avec ses données de révision.

Pour le **MVP**, l'implémentation peut rester volontairement simple (une carte porte directement mot fr + mot ko), mais le modèle est pensé pour qu'un même mot français puisse plus tard porter plusieurs sens sans migration lourde. L'idée est d'anticiper la relation, pas de la sur-implémenter tout de suite.

---

## Flashcard

Une flashcard pourrait contenir :

```text
┌──────────────────────┐
│                      │
│       [ IMAGE ]      │
│                      │
│        사과          │
│       sagwa          │
│                      │
└──────────────────────┘
```

et permettre d'afficher/masquer certaines informations pendant les révisions.

Les données associées à une carte seraient notamment :

* mot français
* mot coréen
* romanisation *(calculée côté backend)*
* catégorie grammaticale
* phrase d'exemple coréenne
* traduction de la phrase
* image *(optionnelle)*
* date de création
* informations liées aux révisions

Comme tous les champs de sens de lecture (fr, ko, romanisation, image) sont stockés, n'importe quel sens de révision (fr→ko, ko→fr, image→mot) restera possible plus tard **sans migration**.

---

## Romanisation

La romanisation suit le système **Revised Romanization of Korean** (système officiel en Corée du Sud). C'est un traitement **déterministe** : il n'a pas sa place dans un appel IA, où il serait irrégulier et coûteux. Il est donc calculé côté backend, à partir du coréen **après validation** de l'utilisateur.

**Décision :**

1. Utiliser en priorité la bibliothèque open-source **KOROMAN** (disponible en Java, suit la Revised Romanization et gère les règles de prononciation : assimilation consonantique, palatalisation, loi du son initial). À vérifier avant de s'engager : disponibilité d'un artefact Maven/Gradle propre et activité du dépôt.
2. **Fallback si l'intégration de KOROMAN est bancale** : implémenter la romanisation à la main. L'algorithme RR est simple — décomposition de chaque syllabe Hangul en jamo (calcul direct sur le code Unicode, les syllabes étant contiguës) puis mapping. ~200 lignes, avec une vraie valeur pédagogique.

Dans les deux cas, la romanisation est **exclue du Structured Output** de l'IA : cela allège le prompt et supprime une source d'incohérence.

---

## Génération des images

Les illustrations sont destinées principalement aux flashcards et seront affichées dans une taille relativement petite sur smartphone.

La génération se fera en **qualité Medium**.

L'image générée est ensuite redimensionnée et optimisée côté backend pour obtenir une version :

**400 × 400 px en WebP**

destinée à l'affichage dans l'application (~20–40 Ko par image).

L'objectif n'est pas d'obtenir une image artistique ou extrêmement détaillée, mais une illustration immédiatement reconnaissable permettant de créer une association visuelle avec le vocabulaire.

```text
pomme → 🍎
chat → 🐈
voiture → 🚗
maison → 🏠
```

**L'image est optionnelle.** Une bonne partie du vocabulaire ne s'illustre pas proprement (mots abstraits comme `liberté`, `devoir`, verbes, adjectifs, ou le sens `avocat-profession`). Une carte doit pouvoir exister **sans image**, avec un fallback (emoji ou simplement aucune image). Cela simplifie le workflow et réduit les coûts.

### Stockage des images

**Décision : stockage sur le VPS (OVH).**

Les fichiers `.webp` sont stockés sur le disque du VPS ; PostgreSQL ne conserve que le **chemin / l'URL** de l'image, jamais le binaire.

Pour une app personnelle, les volumes sont négligeables (10 000 cartes ≈ ~300 Mo). Inconvénients assumés : images couplées au serveur (sauvegardes à gérer soi-même, pas de CDN).

> **Note :** *CloudFront* (CDN d'AWS, payant) n'est pas une solution de stockage et a été écarté. Une alternative de stockage objet — **Cloudflare R2**, compatible S3, free tier permanent de 10 Go et egress gratuit — reste envisageable plus tard.

Pour préserver ce choix futur sans se bloquer, on introduit dès le départ une **abstraction de stockage** :

```text
interface ImageStorageService
    store(...) : String  // renvoie le chemin/URL
    getUrl(...) : String

  ├── LocalDiskImageStorageService   (MVP, disque VPS)
  └── R2ImageStorageService          (évolution, S3-compatible)
```

Passer du disque local à R2 plus tard = une nouvelle implémentation, sans toucher au reste du code.

---

## Gestion des erreurs et des appels externes

Le workflow de création enchaîne plusieurs **appels externes non transactionnels** (traduction LLM, puis génération d'image). Il faut définir tôt ce qui est sauvegardé et quand :

* La **traduction** peut réussir alors que la **génération d'image** échoue → la carte doit pouvoir être créée sans image, l'image étant (re)générable ensuite.
* La génération/régénération d'image doit être **isolée** : on peut relancer *uniquement* l'image sans refaire la traduction.
* Prévoir la gestion des **timeouts**, des **erreurs API** (quota, indisponibilité) et des **réponses IA invalides** (validation du Structured Output).
* Les erreurs d'appels externes doivent remonter proprement à l'utilisateur (message clair, possibilité de réessayer), sans laisser de carte dans un état incohérent.

---

## Principe important concernant l'IA

Une flashcard ne doit appeler l'IA que lors de sa création ou d'une régénération explicitement demandée.

Une fois la carte créée :

```text
pomme
사과
sagwa
image.webp
```

tout est enregistré.

Les consultations et les sessions de révision n'effectuent donc **aucun appel à l'IA**.

Cela permet de maîtriser les coûts.

---

## Stack technique

### Frontend

**Vue 3**

Responsabilités :

* création des flashcards
* validation des propositions IA
* consultation des decks
* interface de révision
* affichage responsive/mobile-first

### Backend

**Java / Spring Boot**

Architecture en couches classique par module :

```text
Controller
    ↓
Service
    ↓
Repository
```

avec des services dédiés aux interactions avec les APIs d'IA. Par exemple :

```text
FlashcardController

FlashcardService

KoreanTranslationService
        ↓
   OpenAI API  (Structured Outputs)

RomanizationService
        ↓
   KOROMAN (ou implémentation maison)

ImageGenerationService
        ↓
   OpenAI Image API

ImageStorageService
        ↓
   Disque VPS  (→ R2 plus tard)

FlashcardRepository
        ↓
    PostgreSQL
```

### Base de données

**PostgreSQL**

Elle stockera notamment :

* les flashcards
* les decks
* le vocabulaire (mots / sens)
* les informations de révision
* les chemins/URLs vers les images

Les images elles-mêmes ne sont pas stockées dans PostgreSQL.

---

## Architecture : monolithe modulaire

Daneo est un **monolithe modulaire** Spring Boot. Le projet n'introduit **pas** de microservices, Kafka ou architecture distribuée.

Découpage envisagé par **domaines**, sous forme de **packages** aux frontières claires (pas de modules Maven/Gradle séparés au départ) :

```text
com.daneo
  ├── deck         (gestion des decks)
  ├── flashcard    (cartes + vocabulaire : mot / sens)
  ├── translation  (intégration LLM, Structured Outputs)
  ├── romanization (KOROMAN / implémentation maison)
  ├── image        (génération + optimisation + stockage)
  ├── review       (sessions de révision ; SRS en évolution)
  └── common       (config, gestion d'erreurs, utilitaires)
```

Principes :

* Chaque domaine expose une **API claire** et ne fouille pas dans les entités des autres.
* Discipline stricte sur les **dépendances entre domaines**.
* Les frontières pourront être **durcies plus tard** (jusqu'à des modules de build séparés) si un domaine se stabilise — inutile de le faire dès le MVP.

---

## MVP

La première version doit volontairement rester simple. Elle permettra de :

1. créer un deck de vocabulaire ;
2. saisir un mot français ;
3. générer sa traduction coréenne avec l'IA (Structured Output validé) ;
4. calculer et afficher la romanisation (backend) ;
5. générer une phrase d'exemple simple ;
6. valider ou corriger la proposition ;
7. générer une illustration en qualité Medium (**optionnelle**) ;
8. sauvegarder la flashcard ;
9. consulter les cartes d'un deck ;
10. lancer une session de révision ;
11. retourner une carte pour afficher sa réponse.

---

## Évolutions possibles

Une fois le MVP terminé, plusieurs fonctionnalités pourront être ajoutées progressivement :

* **répétition espacée (SRS)** — c'est ce qui rend l'app réellement utile au quotidien ; les champs de révision sont déjà prévus dans le modèle ;
* notation **Facile / Moyen / Difficile** ;
* calcul automatique de la prochaine date de révision ;
* statistiques d'apprentissage ;
* suivi du nombre de mots appris ;
* catégories de vocabulaire ;
* import de listes de mots ;
* génération de plusieurs cartes en lot ;
* prononciation audio du mot coréen ;
* reconnaissance des cartes déjà existantes afin d'éviter les doublons ;
* possibilité de choisir entre plusieurs traductions lorsqu'un mot est ambigu ;
* passage du stockage d'images sur **Cloudflare R2** (via l'abstraction déjà en place).

---

## Objectif technique du projet

Daneo doit rester un **monolithe modulaire Spring Boot**, sans complexité distribuée artificielle.

L'intérêt technique est de travailler proprement sur :

* une API REST Spring Boot ;
* Vue 3 ;
* PostgreSQL / JPA / Hibernate ;
* intégration d'une API externe ;
* intégration d'un LLM et **Structured Outputs** ;
* validation des réponses IA ;
* génération d'images ;
* gestion et optimisation de fichiers ;
* traitement déterministe (romanisation) ;
* gestion des erreurs et des appels externes non transactionnels ;
* conception d'une application réellement mobile-first ;
* architecture modulaire propre.

L'objectif final est d'obtenir une application que son développeur puisse réellement utiliser quotidiennement pour apprendre son vocabulaire coréen.
