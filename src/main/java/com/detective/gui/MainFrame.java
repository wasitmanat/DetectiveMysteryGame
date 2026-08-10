package com.detective.gui;

import com.detective.manager.GameManager;
import com.detective.state.InvestigatingState;
import com.detective.util.GameDataInitializer;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;
    private GamePanel gamePanel;
    private BriefingPanel briefingPanel;

    public MainFrame() {
        setTitle("Detective Mystery Game");
        setSize(800, 600);
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

    public void showBriefing(String playerName) {
        if (briefingPanel != null) {
            container.remove(briefingPanel);
        }
        briefingPanel = new BriefingPanel(this, playerName);
        container.add(briefingPanel, "briefing");
        showScreen("briefing");
    }

    public void launchGame(String playerName) {
        GameDataInitializer.initialize();
        GameManager.getInstance().startNewGame(playerName);
        GameManager.getInstance().setCurrentState(new InvestigatingState());

        if (gamePanel != null) {
            container.remove(gamePanel);
        }
        gamePanel = new GamePanel(this);
        container.add(gamePanel, "game");
        showScreen("game");
    }

    public void showResult(boolean won, String actualCulprit) {
        container.removeAll();
        container.add(new WelcomePanel(this), "welcome");
        JPanel resultPanel = won ? new WinPanel(this, actualCulprit) : new LosePanel(this, actualCulprit);
        container.add(resultPanel, "result");
        showScreen("result");
    }

    public void restartGame() {
        GameManager.getInstance().getPlayer();
        String name = GameManager.getInstance().getPlayer().getName();
        launchGame(name);
    }
}