package com.detective.gui;

import com.detective.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
    public WinPanel(MainFrame frame, String culprit) {
        setLayout(new BorderLayout());

        JPanel background = UITheme.gradientPanel(new Color(20, 40, 25), new Color(15, 15, 20));
        background.setLayout(new GridBagLayout());

        JLabel label = new JLabel(
                "<html><center>CASE SOLVED<br><br>" + culprit + " was the killer.<br>Justice has been served.</center></html>",
                SwingConstants.CENTER
        );
        label.setFont(new Font("Serif", Font.BOLD, 24));
        label.setForeground(Color.WHITE);

        JButton restartButton = UITheme.styledButton("Start New Case");
        restartButton.addActionListener(e -> frame.showScreen("welcome"));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(label);
        textPanel.add(Box.createVerticalStrut(30));
        textPanel.add(restartButton);

        background.add(textPanel);
        add(background, BorderLayout.CENTER);
    }
}