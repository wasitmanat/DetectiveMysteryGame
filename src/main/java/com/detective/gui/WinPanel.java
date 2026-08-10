package com.detective.gui;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
    public WinPanel(MainFrame frame, String culprit) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel(
                "<html><center>Case Solved!<br><br>" + culprit + " was the killer.<br>Justice has been served.</center></html>",
                SwingConstants.CENTER
        );
        label.setFont(new Font("Serif", Font.BOLD, 22));
        add(label, BorderLayout.CENTER);

        JButton restartButton = new JButton("Start New Case");
        restartButton.addActionListener(e -> frame.showScreen("welcome"));
        JPanel bottom = new JPanel();
        bottom.add(restartButton);
        add(bottom, BorderLayout.SOUTH);
    }
}