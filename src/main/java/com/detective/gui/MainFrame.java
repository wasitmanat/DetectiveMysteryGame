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

    public MainFrame() {
        setTitle("Detective Mystery Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        WelcomePanel welcomePanel = new WelcomePanel(this);
        container.add(welcomePanel, "welcome");

        WinPanel winPanel = new WinPanel(this);
        container.add(winPanel, "win");

        LosePanel losePanel = new LosePanel(this);
        container.add(losePanel, "lose");

        add(container);
        cardLayout.show(container, "welcome");
    }

    public void showScreen(String name) {
        cardLayout.show(container, name);
    }

    public void startGame(String playerName) {
        GameDataInitializer.initialize();
        GameManager.getInstance().startNewGame(playerName);
        GameManager.getInstance().setCurrentState(new InvestigatingState());

        gamePanel = new GamePanel(this);
        container.add(gamePanel, "game");
        showScreen("game");
    }

    public void restartGame() {
        container.remove(gamePanel);
        startGame(GameManager.getInstance().getPlayer().getName());
    }
}