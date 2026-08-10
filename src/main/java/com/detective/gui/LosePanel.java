package com.detective.gui;

import javax.swing.*;
import java.awt.*;

public class LosePanel extends JPanel {
    public LosePanel(MainFrame frame, String culprit) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel(
                "<html><center>Wrong Suspect!<br><br>The real killer was " + culprit + ".<br>They got away this time...</center></html>",
                SwingConstants.CENTER
        );
        label.setFont(new Font("Serif", Font.BOLD, 22));
        add(label, BorderLayout.CENTER);

        JButton restartButton = new JButton("Try a New Case");
        restartButton.addActionListener(e -> frame.showScreen("welcome"));
        JPanel bottom = new JPanel();
        bottom.add(restartButton);
        add(bottom, BorderLayout.SOUTH);
    }
}