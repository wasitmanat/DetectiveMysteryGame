package com.detective.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel container;

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

    public void addScreen(JPanel panel, String name) {
        container.add(panel, name);
    }
}