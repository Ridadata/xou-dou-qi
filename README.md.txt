# 🎮 Xou Dou Qi – Jeu de Jungle (Java + SQLite)

Un mini-projet Java console inspiré du jeu de stratégie chinois **Xou Dou Qi (Jeu de Jungle)**.

## 🛠️ Technologies
- Java 17
- JDBC + SQLite
- Maven

---

## 🔑 Fonctionnalités

- 🔐 Connexion / Inscription de deux joueurs
- ♟️ Jeu en console (commandes : `move R forward`, `move T left`, etc.)
- 🎯 Interface console colorée (pièces, pièges, eau, tanières…)
- 📊 Historique des matchs et statistiques sauvegardés en base SQLite
- 🧠 Affichage des statistiques et de l’historique des parties


---

##  Lancer le projet

### Prérequis :
- JDK 17+ installé
- Maven installé
- Un terminal/console supportant les couleurs ANSI (Windows Terminal, macOS Terminal, etc.)

### Exécution :
```bash
# Compiler le projet
mvn compile

# Lancer le jeu
mvn exec:java -Dexec.mainClass="game.Main"
