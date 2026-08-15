package com.detective.gui;

import com.detective.manager.GameManager;
import com.detective.state.InvestigatingState;
import com.detective.util.GameDataInitializer;
import com.detective.util.SaveManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;
    private GamePanel gamePanel;
    private BriefingPanel briefingPanel;
    private ProfilePanel profilePanel;

    public MainFrame() {
        setTitle("Detective Mystery Game");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        WelcomePanel welcomePanel = new WelcomePanel(this);
        container.add(welcomePanel, "welcome");

        add(container);
        cardLayout.show(container, "welcome");
    }

    public void showScreen(String name) {
        cardLayout.show(container, name);
    }

    public void showProfile() {
        if (profilePanel != null) {
            container.remove(profilePanel);
        }
        profilePanel = new ProfilePanel(this);
        container.add(profilePanel, "profile");
        showScreen("profile");
    }

    public void handleNameEntry(String playerName) {
        if (SaveManager.hasSave(playerName)) {
            int[] saved = SaveManager.loadProgress(playerName);
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Welcome back, Detective " + playerName + "!\nYou have saved progress at Level " + saved[0] + ".\n\nContinue your saved case?",
                    "Saved Progress Found",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                GameManager.getInstance().loadExistingProgress(playerName, saved[0], saved[1]);
                showBriefing(playerName, saved[0]);
                return;
            } else {
                SaveManager.deleteSave(playerName);
            }
        }
        GameManager.getInstance().startNewGame(playerName);
        showBriefing(playerName, 1);
    }

    public void showBriefing(String playerName, int level) {
        if (briefingPanel != null) {
            container.remove(briefingPanel);
        }
        briefingPanel = new BriefingPanel(this, playerName, level);
        container.add(briefingPanel, "briefing");
        showScreen("briefing");
    }

    public void startLevel(int level) {
        GameManager.getInstance().setCurrentLevel(level);
        GameDataInitializer.initializeLevel(level);
        GameManager.getInstance().setCurrentState(new InvestigatingState());

        if (gamePanel != null) {
            container.remove(gamePanel);
        }
        gamePanel = new GamePanel(this);
        container.add(gamePanel, "game");
        gamePanel.onGameUpdate();
        showScreen("game");
    }

    public void retryCurrentLevel() {
        int level = GameManager.getInstance().getCurrentLevel();
        startLevel(level);
    }

    public void showResult(boolean won, String actualCulprit) {
        String playerName = GameManager.getInstance().getPlayer().getName();
        int score = GameManager.getInstance().getPlayer().getScore();
        int level = GameManager.getInstance().getCurrentLevel();

        container.removeAll();
        container.add(new WelcomePanel(this), "welcome");

        if (won) {
            if (level < 3) {
                SaveManager.saveProgress(playerName, level + 1, score);
                JPanel resultPanel = new WinPanel(this, actualCulprit, level, true);
                container.add(resultPanel, "result");
            } else {
                SaveManager.deleteSave(playerName);
                JPanel resultPanel = new WinPanel(this, actualCulprit, level, false);
                container.add(resultPanel, "result");
            }
        } else {
            SaveManager.saveProgress(playerName, level, score);
            JPanel resultPanel = new LosePanel(this, actualCulprit);
            container.add(resultPanel, "result");
        }
        showScreen("result");
    }

    public void goToNextLevel() {
        String playerName = GameManager.getInstance().getPlayer().getName();
        int nextLevel = GameManager.getInstance().getCurrentLevel() + 1;
        showBriefing(playerName, nextLevel);
    }

    public void restartGame() {
        String playerName = GameManager.getInstance().getPlayer() != null
                ? GameManager.getInstance().getPlayer().getName() : "Detective";
        showBriefing(playerName, 1);
    }
}