🏴‍☠️ Treasure Hunt Game

Course: Object Oriented Programming Using Java (24CSH2114)
Institution: Saintgits College of Engineering

📖 Overview

The Treasure Hunt Game is an interactive desktop application developed using JavaFX.
Players must locate a hidden treasure inside a grid-based map while avoiding traps and racing against time.

This project demonstrates advanced OOP concepts, event-driven programming, UI/UX development, JavaFX scene management, and custom logic implementation without external game engines.

⚙️ Technical Highlights & Algorithms
1. 🧠 Smart Hint Algorithm (Manhattan Distance)

Instead of random clues, the game uses a proximity system powered by Manhattan Distance:|x1 - x2| + |y1 - y2|

How it works:

Calculates the distance between the clicked cell and the treasure
Compares with the previous move to determine:
“Warmer” → Getting closer
“Colder” → Moving away
Also checks adjacent cells to display trap warnings

2. 💾 Persistent Leaderboard (Java File I/O)

A competitive scoreboard is maintained using flat-file storage.

Key Implementations:
Writes player score + completion time to topScores.txt
ScoreEntry implements Comparable with custom sorting:
Higher Score → Higher Rank
If equal score: Lower Time → Higher Rank
Ensures fair and consistent ranking across sessions

3. 🧩 MVC-Inspired Architecture

The application is designed using a clean modular structure:

Model
ScoreEntry
Cell

View
JavaFX pages (Home, Game Grid, Scoreboard, Instructions, Credits)

Controller
TreasureHuntGame
Handles game state, timer, trap logic, hints, scoring, and navigation

This separation improves readability, maintenance, and scalability.

🎮 Gameplay Features

🔹 Dynamic Grid (5×5)
Treasure and traps are randomized each session (java.util.Random)
~15% trap probability

🔹 Scoring System (Risk vs Reward)
💰 Treasure Found → +20 points
💥 Trap Hit → –5 points
❓ Empty Cell → –1 point

🔹 Tactical Advanced Hint
Activated only when score ≤ 5
Reveals exact directional offset to treasure
(e.g., “2 steps down, 1 step left”)

🔹 Timer Mechanic
60-second countdown
Built using JavaFX Timeline
Game ends when the timer hits zero

🛠️ Tech Stack

Language: Java (JDK 8+)
UI Toolkit: JavaFX
Styles: Embedded CSS for UI components
Storage: Text file (topScores.txt)

📂 Project Structure

The codebase is organized into modular classes for clarity and maintainability:
src/
├── TreasureHuntGame.java     # Main controller: Grid, clicks, scoring, state
├── RegularHintGenerator.java # Hint logic (Manhattan distance, trap detection)
├── ScoreBoard.java           # Reads/Writes scores (File I/O)
├── ScoreEntry.java           # Player score model + sorting logic
├── GameTimer.java            # Reusable countdown timer utility
├── Cell.java                 # Grid cell component (Button + metadata)
├── HomePage.java             # Main Menu UI
└── Main.java                 # Application entry point (launches UI)
