package com.detective.manager;

import com.detective.model.Evidence;
import com.detective.model.Player;
import com.detective.model.Room;
import com.detective.model.Suspect;
import com.detective.observer.GameObserver;
import com.detective.state.GameState;
import com.detective.state.InvestigatingState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    private static GameManager instance;

    private Player player;
    private List<Room> rooms;
    private List<Suspect> suspects;
    private List<Evidence> collectedEvidence;
    private List<String> investigationLog;
    private List<GameObserver> observers;
    private boolean gameOver;
    private int currentLevel;
    private GameState currentState = new InvestigatingState();
    private String currentHint;
    private boolean hintUsedThisLevel;
    private Map<Integer, String> lastCulpritByLevel;
    private Map<String, Integer> suspicionPoints;

    private GameManager() {
        rooms = new ArrayList<>();
        suspects = new ArrayList<>();
        collectedEvidence = new ArrayList<>();
        investigationLog = new ArrayList<>();
        observers = new ArrayList<>();
        gameOver = false;
        currentLevel = 1;
        lastCulpritByLevel = new HashMap<>();
        suspicionPoints = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void startNewGame(String playerName) {
        this.player = new Player(playerName);
        this.collectedEvidence.clear();
        this.investigationLog.clear();
        this.gameOver = false;
        this.currentLevel = 1;
        addLog("Game started for detective " + playerName);
        notifyObservers();
    }

    public void loadExistingProgress(String playerName, int level, int score) {
        this.player = new Player(playerName);
        this.player.addScore(score);
        this.collectedEvidence.clear();
        this.investigationLog.clear();
        this.gameOver = false;
        this.currentLevel = level;
        addLog("Welcome back, Detective " + playerName + ". Resuming Level " + level + ".");
        notifyObservers();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Suspect> getSuspects() {
        return suspects;
    }

    public List<Evidence> getCollectedEvidence() {
        return collectedEvidence;
    }

    public void collectEvidence(Evidence evidence) {
        collectedEvidence.add(evidence);
        player.addScore(10);
        addLog("Collected evidence: " + evidence.getName());
        notifyObservers();
    }

    public List<String> getInvestigationLog() {
        return investigationLog;
    }

    public void addLog(String entry) {
        investigationLog.add(entry);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        notifyObservers();
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState state) {
        this.currentState = state;
        notifyObservers();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onGameUpdate();
        }
    }

    public void setHint(String hint) {
        this.currentHint = hint;
        this.hintUsedThisLevel = false;
    }

    public String getHint() {
        return currentHint;
    }

    public boolean isHintUsedThisLevel() {
        return hintUsedThisLevel;
    }

    public void useHint() {
        hintUsedThisLevel = true;
        player.addScore(-15);
        addLog("Used a hint (-15 points)");
        notifyObservers();
    }

    public String getLastCulprit(int level) {
        return lastCulpritByLevel.get(level);
    }

    public void setLastCulprit(int level, String name) {
        lastCulpritByLevel.put(level, name);
    }

    public void resetSuspicion() {
        suspicionPoints.clear();
    }

    public void addSuspicion(String suspectName, int amount) {
        suspicionPoints.put(suspectName, suspicionPoints.getOrDefault(suspectName, 0) + amount);
        notifyObservers();
    }

    public int getSuspicion(String suspectName) {
        return suspicionPoints.getOrDefault(suspectName, 0);
    }
}