package com.detective.command;

import com.detective.manager.GameManager;
import com.detective.model.Suspect;

import java.util.Random;

public class InterrogateCommand implements Command {
    private static final Random random = new Random();

    private static final String[] GUILTY_RESPONSES = {
            "...Their eyes dart away. They seem nervous.",
            "They hesitate before answering. Something isn't right.",
            "Their story doesn't quite add up when you press further."
    };

    private static final String[] INNOCENT_RESPONSES = {
            "They answer calmly and confidently.",
            "They seem genuinely upset about the situation, but not evasive.",
            "Nothing about their reaction seems suspicious."
    };

    private Suspect suspect;
    private String reaction;

    public InterrogateCommand(Suspect suspect) {
        this.suspect = suspect;
    }

    public String getReaction() {
        return reaction;
    }

    @Override
    public void execute() {
        String[] pool = suspect.isGuilty() ? GUILTY_RESPONSES : INNOCENT_RESPONSES;
        reaction = pool[random.nextInt(pool.length)];
        GameManager.getInstance().addLog("Interrogated " + suspect.getName() + ": " + reaction);
    }
}