package com.detective.command;

import com.detective.manager.GameManager;
import com.detective.model.Suspect;
import com.detective.state.GameOverState;

public class AccuseCommand implements Command {
    private Suspect suspect;
    private boolean correct;

    public AccuseCommand(Suspect suspect) {
        this.suspect = suspect;
    }

    public boolean isCorrect() {
        return correct;
    }

    @Override
    public void execute() {
        correct = suspect.isGuilty();
        if (correct) {
            GameManager.getInstance().getPlayer().addScore(50);
            GameManager.getInstance().addLog("Correct accusation: " + suspect.getName());
        } else {
            GameManager.getInstance().addLog("Wrong accusation: " + suspect.getName());
        }
        GameManager.getInstance().setCurrentState(new GameOverState());
    }
}