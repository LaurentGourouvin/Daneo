# Prompt — Design UI/UX de Daneo

Je souhaite concevoir l'interface complète d'une application appelée **Daneo**, une application mobile-first de flashcards destinée à l'apprentissage du vocabulaire coréen par des utilisateurs francophones.

Je veux que tu proposes un **design UI/UX complet, cohérent et moderne**, avec une vraie identité visuelle.

## Concept du produit

Daneo permet à un utilisateur francophone d'apprendre du vocabulaire coréen.

Le parcours principal est le suivant :

1. L'utilisateur choisit ou crée un deck.
2. Il saisit un mot en français, par exemple `pomme`.
3. Une IA propose sa traduction coréenne : `사과`.
4. La romanisation est calculée automatiquement côté backend : `sagwa`.
5. L'utilisateur vérifie et valide la traduction.
6. Il peut demander la génération d'une illustration associée au mot.
7. La flashcard est sauvegardée.
8. L'utilisateur peut ensuite réviser ses cartes.

Exemple de contenu d'une carte :

- illustration d'une pomme ;
- `사과` ;
- `sagwa` ;
- `pomme`.

L'application sera principalement utilisée sur smartphone.

## Direction artistique

Je veux une interface qui évoque :

- l'apprentissage ;
- la mémorisation ;
- la progression ;
- la langue coréenne ;
- la simplicité ;
- le calme ;
- la concentration.

Le design doit être :

- moderne ;
- chaleureux ;
- minimaliste ;
- légèrement ludique ;
- adulte ;
- très lisible ;
- agréable à utiliser quotidiennement.

Je ne veux PAS d'une application qui ressemble à un dashboard SaaS ou à un outil professionnel classique.

Je ne veux pas non plus d'un design enfantin ou excessivement gamifié.

Daneo doit donner envie d'ouvrir l'application quelques minutes chaque jour pour apprendre du vocabulaire.

## Identité coréenne

Créer une identité visuelle subtilement inspirée de la langue coréenne.

Le Hangul peut être utilisé comme élément graphique discret.

Exemples :

`가`

`나`

`다`

`라`

Certaines formes géométriques provenant de l'écriture Hangul peuvent également être utilisées comme motifs graphiques très légers.

Éviter les clichés visuels évidents :

- drapeau sud-coréen omniprésent ;
- temples ;
- K-pop ;
- rouge et bleu utilisés systématiquement ;
- imagerie touristique.

L'identité coréenne doit principalement venir :

- de la typographie ;
- du Hangul ;
- de la composition ;
- des détails graphiques.

## Style des illustrations

Les illustrations générées pour les flashcards auront un style visuel légèrement inspiré :

- du manga ;
- de la bande dessinée ;
- de l'illustration éditoriale moderne.

Elles doivent être :

- propres ;
- simples ;
- chaleureuses ;
- légèrement stylisées ;
- faciles à reconnaître ;
- adaptées à une petite taille sur smartphone.

Elles ne doivent pas être photoréalistes.

Le design global de l'application doit bien fonctionner avec ce type d'illustrations.

Les illustrations ne contiennent **aucun texte**.

Le français, le Hangul et la romanisation sont toujours affichés par l'interface de l'application, jamais intégrés dans l'image.

## Couleurs

Créer une palette douce, moderne et mémorable.

Je souhaite une couleur principale suffisamment identifiable pour devenir la couleur de Daneo.

Elle peut par exemple se situer autour :

- du bleu indigo ;
- du violet doux ;
- d'une couleur froide légèrement désaturée.

Ajouter éventuellement une couleur secondaire plus chaleureuse pour les éléments liés :

- à la réussite ;
- à la progression ;
- aux illustrations ;
- aux actions positives.

Éviter les couleurs extrêmement saturées.

Le fond principal doit rester clair et confortable pour des sessions de révision.

Prévoir également les couleurs nécessaires aux états :

- maîtrisé ;
- difficile ;
- à revoir ;
- information ;
- erreur.

Respecter un contraste suffisant pour l'accessibilité.

## Typographie

L'interface contient simultanément :

- du français ;
- du Hangul ;
- de la romanisation.

Créer une hiérarchie typographique permettant de distinguer immédiatement ces trois niveaux.

Sur une flashcard :

`사과`

doit être l'élément textuel principal.

Puis :

`sagwa`

Puis :

`pomme`

Le Hangul doit rester parfaitement lisible sur un petit smartphone.

## Écran d'accueil

Créer un écran d'accueil simple et motivant.

Il peut afficher :

- une courte salutation ;
- le nombre de cartes à réviser aujourd'hui ;
- la progression récente ;
- un bouton principal `Réviser` ;
- les decks récents ;
- un accès rapide permettant de créer une nouvelle carte.

Ne pas transformer cet écran en dashboard rempli de statistiques.

L'action principale doit immédiatement être identifiable.

## Écran des decks

Créer une interface permettant de consulter les différents decks.

Exemples :

- Cours 01
- Nourriture
- Verbes
- Voyage
- Maison

Chaque deck peut afficher :

- son nom ;
- le nombre de cartes ;
- éventuellement le nombre de cartes à réviser ;
- une indication légère de progression.

Ajouter une action claire permettant de créer un deck.

## Écran d'un deck

Afficher :

- le nom du deck ;
- le nombre de cartes ;
- les flashcards existantes ;
- une action très visible `Ajouter un mot`.

Chaque carte dans la liste peut montrer rapidement :

- le mot coréen ;
- le mot français ;
- éventuellement une miniature de son illustration.

## Création d'une flashcard

Cet écran est essentiel.

### Étape 1 — saisie

Afficher un champ simple :

`Quel mot veux-tu apprendre ?`

Exemple :

`pomme`

Bouton principal :

`Traduire`

### Étape 2 — proposition IA

Afficher clairement :

**Français**

pomme

**Coréen**

사과

L'utilisateur doit pouvoir :

- accepter la proposition ;
- la modifier ;
- choisir une autre traduction si le mot français est ambigu.

### Étape 3 — romanisation

Après validation du coréen, la romanisation apparaît automatiquement :

`사과`

`sagwa`

### Étape 4 — illustration

Proposer une action séparée et facultative :

`Générer une illustration`

Pendant la génération, afficher un état de chargement agréable.

L'image générée ne contient aucun texte.

### Étape 5 — aperçu

Afficher l'aperçu complet de la flashcard avant sauvegarde.

Bouton final :

`Ajouter au deck`

Le workflow doit sembler extrêmement rapide et naturel.

## Gestion des mots ambigus

Certains mots français peuvent avoir plusieurs traductions.

Exemple :

`avocat`

L'application peut proposer :

**변호사**

Avocat — profession

**아보카도**

Avocat — fruit

Créer une interface élégante permettant à l'utilisateur de sélectionner le sens voulu sans rendre le workflow complexe.

## Flashcard

La flashcard est le cœur visuel de Daneo.

Créer un design particulièrement soigné.

Exemple de face réponse :

- illustration ;
- Hangul ;
- romanisation ;
- français.

La carte doit avoir :

- beaucoup d'espace ;
- une illustration clairement identifiable ;
- un Hangul très lisible ;
- très peu d'éléments parasites.

La flashcard doit rester belle avec ou sans illustration.

L'illustration ne contient jamais :

- le mot français ;
- le mot coréen ;
- la romanisation ;
- une légende.

Tout le texte appartient à l'interface.

## Mode révision

Créer une expérience extrêmement focalisée.

Pendant une révision, masquer les éléments inutiles de navigation.

Exemple :

**8 / 20**

Grande flashcard centrale.

Face question :

`pomme`

Action :

`Afficher la réponse`

Après révélation :

- illustration ;
- `사과` ;
- `sagwa` ;
- `pomme`.

Puis trois choix :

- Difficile
- Moyen
- Facile

Les boutons doivent être facilement utilisables au pouce sur smartphone.

Prévoir éventuellement une animation légère lorsque la carte est retournée.

Éviter toute animation excessive.

## Progression

Créer une représentation motivante mais discrète de la progression.

Exemples :

`12 mots appris cette semaine`

`42 cartes maîtrisées`

Éviter :

- les monnaies virtuelles ;
- les systèmes complexes de points ;
- les badges omniprésents ;
- les streaks agressifs.

Le produit doit encourager l'apprentissage sans créer de pression.

## États particuliers

Prévoir les designs pour :

- deck vide ;
- aucune carte à réviser ;
- génération IA en cours ;
- génération d'image en cours ;
- échec de traduction ;
- échec de génération d'image ;
- carte sans image ;
- absence de connexion ;
- confirmation de suppression.

Les erreurs liées à l'IA ne doivent jamais casser le workflow.

## Responsive

La priorité absolue est le smartphone.

Concevoir d'abord pour une largeur autour de **390 px**.

Puis prévoir l'adaptation pour :

- grands smartphones ;
- tablette ;
- desktop.

Sur desktop, ne pas simplement étirer l'application sur toute la largeur.

Conserver une largeur de lecture confortable.

## Navigation mobile

Proposer une navigation très simple.

Elle peut par exemple contenir :

- Accueil
- Decks
- Ajouter
- Réviser
- Réglages

Le bouton de création peut être légèrement mis en avant.

Ne conserver que les sections réellement utiles.

## Contraintes UX

Toutes les actions principales doivent être facilement utilisables au pouce.

Éviter :

- les petits boutons ;
- les menus complexes ;
- les modales inutiles ;
- les tableaux ;
- les écrans surchargés ;
- les formulaires longs.

L'utilisateur doit pouvoir créer une flashcard en quelques secondes.

## Résultat attendu

Je veux une proposition cohérente comprenant au minimum :

1. identité visuelle de Daneo ;
2. palette de couleurs ;
3. typographies et hiérarchie ;
4. composants principaux ;
5. navigation mobile ;
6. écran d'accueil ;
7. liste des decks ;
8. détail d'un deck ;
9. workflow de création d'une flashcard ;
10. sélection d'un sens ambigu ;
11. aperçu d'une flashcard ;
12. écran de révision avant révélation ;
13. écran de révision après révélation ;
14. états de chargement ;
15. états d'erreur ;
16. comportement responsive.

Le résultat doit pouvoir servir directement de référence pour l'implémentation frontend en **Vue 3**.

La priorité n'est pas de créer une interface spectaculaire.

La priorité est :

**belle + immédiatement compréhensible + agréable à utiliser quotidiennement + parfaitement adaptée à l'apprentissage du vocabulaire coréen.**