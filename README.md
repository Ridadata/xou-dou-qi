# 🎮 Xou Dou Qi – Jeu de Jungle (Java Console + SQLite)

Ce projet est un **jeu de stratégie en console**, inspiré du jeu chinois "Xou Dou Qi" (ou Jungle).  
Il a été développé dans le cadre d’un mini-projet en Java, avec gestion des utilisateurs et historique des parties grâce à une base de données SQLite intégrée.

---

## 🧠 Objectifs pédagogiques

- Maîtriser la programmation orientée objet en Java
- Utiliser une base de données embarquée avec JDBC/SQLite
- Appliquer la logique de jeu, les déplacements et règles complexes
- Améliorer l'expérience utilisateur via une interface console colorée
- Structurer un projet Java avec Maven

---

## 🚀 Fonctionnalités principales

-  **Connexion / Inscription** des joueurs
-  **Jeu tour par tour en console**, avec commandes textuelles (`move R forward`, `show`, etc.)
-  **Plateau de jeu coloré** (pièces rouges/bleues, eau, pièges, tanières)
-  **Règles officielles** du jeu respectées (puissance des pièces, zones spéciales)
-  **Base de données SQLite intégrée** :
  - Enregistrement des comptes joueurs
  - Historique des matchs (joueurs, gagnant, date)
  - Statistiques : victoires, défaites, égalités
-  Interface 100% en console, sans framework graphique

---

##  Technologies utilisées

- **Java 17**
- **JDBC (Java Database Connectivity)**
- **SQLite**
- **Maven** pour la gestion du projet
- **Terminal ANSI** pour les couleurs (compatible Linux/macOS/Windows Terminal)

---

## 📂 Structure du projet (simplifiée)

📦 XouDouQi
├── 📁 src
│   └── 📁 main
│       └── 📁 java
│           └── 📁 game
│               ├── Main.java
│               ├── Game.java
│               ├── Player.java
│               └── Piece.java + sous-classes (Rat, Lion, etc.)...
├── .gitignore
├── pom.xml
└── README.md


---

##  Instructions pour exécuter le jeu

### 1. Prérequis
- Java JDK 17 ou supérieur
- Maven installé
- Console compatible ANSI (pour les couleurs)

### 2. Compilation et lancement

```bash
# Compilation du projet
mvn compile

# Lancement de l'application
mvn exec:java -Dexec.mainClass="game.Main"

