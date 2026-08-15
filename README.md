# Detective Mystery Game

A Java Swing desktop game built for a university Software Design Pattern (SDP) course. Play as a detective investigating a murder — explore rooms, collect evidence, interrogate suspects, and accuse the killer before time runs out.

## Tech Stack
- Java 21
- Java Swing (GUI)
- Maven (build tool)

## Features
- Crime briefing and story intro
- Multiple rooms to explore with collectible evidence
- Suspect interrogation with guilt-based reactions
- Randomly selected culprit each playthrough (replayable)
- Evidence-based clues pointing toward the real culprit
- Score system with penalty for wrong accusations
- Random flavor events while exploring
- Win/Lose screens with full case reveal
- Restart to play a new case

## Design Patterns Used
- **Singleton** — `GameManager` ensures a single shared source of game state across all screens.
- **Factory** — `SuspectFactory`, `RoomFactory`, `EvidenceFactory` centralize object creation for game entities.
- **Command** — `CollectEvidenceCommand`, `InterrogateCommand`, `AccuseCommand` encapsulate player actions as objects.
- **State** — `InvestigatingState`, `GameOverState` represent distinct phases of the game.
- **Observer** — `GameObserver` lets GUI panels (inventory, score, log) auto-update whenever game data changes.

## Project Structure
com.detective/
├── gui/ - Swing screens and panels
├── model/ - Player, Room, Suspect, Evidence
├── manager/ - GameManager (Singleton)
├── command/ - Player action commands
├── factory/ - Object creation factories
├── state/ - Game state classes
├── observer/ - Observer interface
└── util/ - Theming, random events, data setup

## How to Run
1. Clone the repository
2. Open in IntelliJ IDEA as a Maven project
3. Ensure JDK 21 is set as the project SDK
4. Run `Main.java`

## Author
Wasit — 8th Semester, CSE