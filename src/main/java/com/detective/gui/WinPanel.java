package com.detective.gui;

import com.detective.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
    public WinPanel(MainFrame frame, String culprit, int levelJustWon, boolean hasNextLevel) {
        setLayout(new BorderLayout());

        JPanel background = UITheme.gradientPanel(new Color(20, 40, 25), new Color(15, 15, 20));
        background.setLayout(new GridBagLayout());

        int score = com.detective.manager.GameManager.getInstance().getPlayer().getScore();
        String rank = getRank(score);

        String message;
        if (hasNextLevel) {
            message = "<html><center>LEVEL " + levelJustWon + " SOLVED<br><br>"
                    + culprit + " was the killer.<br>Your progress has been saved.<br><br>"
                    + "Ready for Level " + (levelJustWon + 1) + "?</center></html>";
        } else {
            message = "<html><center>CASE CLOSED - CAMPAIGN COMPLETE<br><br>"
                    + culprit + " was the killer.<br>You solved all 3 cases, Detective.<br><br>"
                    + "Final Score: " + score + "<br>Rank: " + rank + "</center></html>";
        }

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 22));
        label.setForeground(Color.WHITE);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(label);
        textPanel.add(Box.createVerticalStrut(30));

        if (hasNextLevel) {
            JButton nextButton = UITheme.styledButton("Continue to Level " + (levelJustWon + 1));
            nextButton.addActionListener(e -> frame.goToNextLevel());
            nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(nextButton);
        } else {
            JButton restartButton = UITheme.styledButton("Start New Campaign");
            restartButton.addActionListener(e -> frame.showScreen("welcome"));
            restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(restartButton);
        }

        background.add(textPanel);
        add(background, BorderLayout.CENTER);
    }

    private String getRank(int score) {
        if (score >= 180) return "Master Detective";
        if (score >= 120) return "Skilled Detective";
        if (score >= 60) return "Rookie Detective";
        return "Lucky Guesser";
    }
}