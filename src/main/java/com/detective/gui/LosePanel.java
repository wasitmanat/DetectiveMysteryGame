package com.detective.gui;

import com.detective.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class LosePanel extends JPanel {
    public LosePanel(MainFrame frame, String culprit) {
        setLayout(new BorderLayout());

        JPanel background = UITheme.gradientPanel(new Color(40, 20, 20), new Color(15, 15, 20));
        background.setLayout(new GridBagLayout());

        JLabel label = new JLabel(
                "<html><center>WRONG SUSPECT<br><br>The real killer was " + culprit + ".<br>They got away this time...<br><br>"
                        + "Your progress on this level is saved.</center></html>",
                SwingConstants.CENTER
        );
        label.setFont(new Font("Serif", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton retryButton = UITheme.styledButton("Retry This Case");
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retryButton.addActionListener(e -> frame.retryCurrentLevel());

        JButton exitButton = UITheme.styledButton("Exit to Main Menu");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> frame.showScreen("welcome"));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(label);
        textPanel.add(Box.createVerticalStrut(25));
        textPanel.add(retryButton);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(exitButton);

        background.add(textPanel);
        add(background, BorderLayout.CENTER);
    }
}