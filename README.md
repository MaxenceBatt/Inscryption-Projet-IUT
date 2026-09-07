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
