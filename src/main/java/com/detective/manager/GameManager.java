package com.detective.manager;

import com.detective.model.Evidence;
import com.detective.model.Player;
import com.detective.model.Room;
import com.detective.model.Suspect;
import com.detective.observer.GameObserver;
import com.detective.state.GameState;
import com.detective.state.InvestigatingState;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager instance;

    private Player player;
    private List<Room> rooms;
    private List<Suspect> suspects;
    private List<Evidence> collectedEvidence;
    private List<String> investigationLog;
    private List<GameObserver> observers;
    private boolean gameOver;

    private GameManager() {
        rooms = new ArrayList<>();
        suspects = new ArrayList<>();
        collectedEvidence = new ArrayList<>();
        investigationLog = new ArrayList<>();
        observers = new ArrayList<>();
        gameOver = false;
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
        addLog("Game started for detective " + playerName);
        notifyObservers();
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

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onGameUpdate();
        }
    }

    private GameState currentState = new InvestigatingState();

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState state) {
        this.currentState = state;
        notifyObservers();
    }
}