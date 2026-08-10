package com.detective.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Detective Mystery Game", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Investigation");
        startButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(frame, "Enter your detective name:");
            if (name == null || name.trim().isEmpty()) {
                name = "Detective";
            }
            frame.showBriefing(name);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}