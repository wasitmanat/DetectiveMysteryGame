package com.detective.gui;

import javax.swing.*;
import java.awt.*;

public class LosePanel extends JPanel {
    public LosePanel(MainFrame frame) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Wrong suspect! The real culprit got away.", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart Game");
        restartButton.addActionListener(e -> frame.restartGame());
        JPanel bottom = new JPanel();
        bottom.add(restartButton);
        add(bottom, BorderLayout.SOUTH);
    }
}