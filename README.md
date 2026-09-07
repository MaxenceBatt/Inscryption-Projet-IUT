# Inscryption-Projet-IUT

# 🌲 Projet Inscryption - SAE21

Développement du jeu Inscryption en java dans le cadre de la SAE21 du BUT Informatique (IUT Robert Schuman, Université de Strasbourg).

---

## Description

Ce projet est une tentative de réplique du jeu **Inscryption**, en cinq semaines et en équipe de deux. Le joueur peut jouer Trois parties d'**Inscryption** avec entre chaque partie l'ajout d'une carte dansledeck et la méchanique de la pierre de sacrifice pour transmettre les pouvoirs.

---

## 📸 Aperçu

### Plateau de jeu
![Plateau de jeu](assets/PlateauDeJeuInscryption.png)

### Main et actions
![Main et actions](assets/main&actionInscryption.png)

### Pierre de sacrifice et nouvelle carte
![Sacrifice et nouvelle carte](assets/NouvelleCarte&PierreDeSacrifice.png)

---

## ✨ Fonctionnalités

| Volet | Description |
|-------|-------------|
| **1 — Plateau de jeu** | Plateau de jeu avec des cartes colorés en fonction du type de la carte et une pioche qui donne le nombre de cartes restantes |
| **2 — Affichage du deck** | Deck affiché en dessous du plutôt avec pour chaque carte les PV, les dégâts, Le coût en sang et en os et les pouvoirs |
| **3 — Rappel des actions disponibles** | Un rappel des  actions disponibles est affiché après chaque action |
| **4 — Choix d'une nouvelle carte** | Après chaque partie le joueur à le choix entre deux cartes à ajouter à son deck |
| **5 — Pierre de sacrifice** | Après chaque partie le joueur peut transmettre les pouvoirs d'une carte à une autre |

---

## 🏗️ Architecture du projet

```
Inscryption-Project-IUT/
│
├── .idea/
│   └── libraries/
│
├── assets/
│
├── deps/
│
├── out/
│   ├── production/
│   │   └── project-inscryption/
│   │       └── inscription/
│   │           ├── cards/
│   │           │   └── powers/
│   │           ├── game/
│   │           └── ui/
│   │
│   └── test/
│       └── project-inscryption/
│           └── inscription/
│               ├── cards/
│               │   └── powers/
│               └── game/
│
├── src/
│   └── inscription/
│       ├── cards/
│       │   └── powers/
│       ├── game/
│       └── ui/
│
├── tests/
│   └── inscription/
│       ├── cards/
│       │   └── powers/
│       └── game/
│
└── uml/
```

---

# 🃏 Cartes du jeu

## 🐾 Cartes Animaux

| Animal | ❤️ PV | ⚔️ Attaque | 🩸 Coût sang | 🦴 Coût os | ✨ Pouvoir |
|---|---:|---:|---:|---:|---|
| 🐱 Chat | 1 | 0 | 1 | 0 | `ManyLives` |
| 🐻 Grizzly | 6 | 4 | 3 | 0 | — |
| 🐺 Coyote | 1 | 2 | 0 | 4 | — |
| 🐿️ Ecureuil | 1 | 0 | 0 | 0 | — |
| 🦦 Hermine | 3 | 1 | 1 | 0 | — |
| 🐺 Louveteau | 1 | 1 | 1 | 0 | `Growth` |
| 🐺 Loup | 2 | 3 | 2 | 0 | — |
| 🪲 Punaise | 2 | 1 | 0 | 2 | `Stinky` |
| 🫎 Elan | 4 | 2 | 2 | 0 | `Sprinter` |
| 🐍 Vipère | 1 | 1 | 2 | 0 | `DeadlyContact` |
| 🦔 Porc-Épic | 2 | 1 | 1 | 0 | `SharpQuills` |
| 🐦 Moineau | 2 | 1 | 1 | 0 | — |
| 🐦‍⬛ Corbeau | 3 | 2 | 2 | 0 | — |

## 🪽 Animaux volants

Les animaux suivants sont des animaux volants, qui attaquent au-dessus des cartes placés en face

| Animal | ❤️ PV | ⚔️ Attaque | 🩸 Coût sang | 🦴 Coût os | Type |
|---|---:|---:|---:|---:|---|
| 🐦 Moineau | 2 | 1 | 1 | 0 | Volant |
| 🐦‍⬛ Corbeau | 3 | 2 | 2 | 0 | Volant |

## 🪨 Cartes Obstacles

| Obstacle | ❤️ PV |
|---|---:|
| 🪨 Rocher | 5 |
| 🌲 Sapin | 3 |

## 📊 Résumé des cartes

| Catégorie | Nombre |
|---|---:|
| 🐾 Animaux terrestres | 11 |
| 🪽 Animaux volants | 2 |
| 🪨 Obstacles | 2 |
| **Total** | **15** |

---

## 🚀 Installation et lancement

### Prérequis

- **Windows ou Linux**
- **IntelliJ 2025.3.2** (ou version compatible)

### Étapes

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/<votre-pseudo>/<nom-du-repo>.git
   ```
2. **Ouvrir le répertoire** dans IntelliJ

3. Puis simplement éxectuer (Flèche verte en haut à droite)

---

## 👥 Auteurs

Projet réalisé par **Maxence** et son binome ([@MarwanAitTamgount](https://github.com/marwaaan212)) dans le cadre de la **SAE21 — BUT Informatique 1ère année**, IUT Robert Schuman, Université de Strasbourg — Session 2026.

