# Detective Mystery Game

A Java Swing desktop game built for a university Software Design Pattern (SDP) course. Play as a detective across a 3-level campaign — explore rooms, collect evidence, interrogate suspects, and accuse the killer before time runs out.

## Tech Stack
- Java 21
- Java Swing (GUI)
- Maven (build tool)

## Features
- Detective profile screen with save/continue support (private per-player save files)
- 3-level campaign, each with a unique case, setting, suspects, and evidence
- Guaranteed different culprit each replay (never repeats the last playthrough's killer)
- Evidence-based clues and suspect interrogation with guilt-weighted reactions
- Suspicion Meter — visual indicator that grows as you investigate each suspect
- One-time hint system per level (costs points)
- 3-minute countdown timer per level with auto-loss on timeout
- Score system with rewards for evidence and correct accusations, penalties for hints and wrong accusations
- Random flavor events while exploring rooms
- Win/Lose screens with full case reveal, Retry/Exit options, and final rank (Rookie to Master Detective)
- Full-screen styled UI across all screens

## Design Patterns Used
- **Singleton** — `GameManager` ensures a single shared source of game state across all screens.
- **Factory** — `SuspectFactory`, `RoomFactory`, `EvidenceFactory` centralize creation of game entities.
- **Command** — `CollectEvidenceCommand`, `InterrogateCommand`, `AccuseCommand` encapsulate player actions as objects.
- **State** — `InvestigatingState`, `GameOverState` represent distinct phases of gameplay.
- **Observer** — `GameObserver` lets GUI panels (inventory, score, log, suspicion bars) auto-update whenever game data changes.

## Project Structure
com.detective/
├── gui/ - Swing screens and panels

├── model/ - Player, Room, Suspect, Evidence

├── manager/ - GameManager (Singleton)

├── command/ - Player action commands

├── factory/ - Object creation factories

├── state/ - Game state classes

├── observer/ - Observer interface

└── util/ - Theming, save system, case data, random events

## How to Run
1. Clone the repository
2. Open in IntelliJ IDEA as a Maven project
3. Ensure JDK 21 is set as the project SDK
4. Run `Main.java`

## Save Data
Player progress is stored locally in a `saves/` folder created next to the project, one file per detective name — allowing multiple players to keep separate, private progress on the same machine.

## Author
Wasit — 8th Semester, CSE


