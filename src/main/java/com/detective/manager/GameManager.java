package com.detective.manager;

import com.detective.model.Evidence;
import com.detective.model.Player;
import com.detective.model.Room;
import com.detective.model.Suspect;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager instance;

    private Player player;
    private List<Room> rooms;
    private List<Suspect> suspects;
    private List<Evidence> collectedEvidence;
    private List<String> investigationLog;

    private GameManager() {
        rooms = new ArrayList<>();
        suspects = new ArrayList<>();
        collectedEvidence = new ArrayList<>();
        investigationLog = new ArrayList<>();
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
        addLog("Game started for detective " + playerName);
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
        addLog("Collected evidence: " + evidence.getName());
    }

    public List<String> getInvestigationLog() {
        return investigationLog;
    }

    public void addLog(String entry) {
        investigationLog.add(entry);
    }
}