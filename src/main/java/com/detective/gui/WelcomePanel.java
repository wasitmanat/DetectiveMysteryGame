package com.detective.gui;

import com.detective.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JPanel background = UITheme.gradientPanel(new Color(20, 20, 30), new Color(60, 20, 25));
        background.setLayout(new GridBagLayout());

        JLabel title = new JLabel("DETECTIVE MYSTERY");
        title.setFont(new Font("Serif", Font.BOLD, 40));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Three cases. One detective. Can you solve them all?");
        subtitle.setFont(new Font("Serif", Font.ITALIC, 16));
        subtitle.setForeground(new Color(200, 200, 200));

        JButton startButton = UITheme.styledButton("Start Investigation");
        startButton.addActionListener(e -> frame.showProfile());

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(subtitle);
        textPanel.add(Box.createVerticalStrut(30));
        textPanel.add(startButton);

        background.add(textPanel);
        add(background, BorderLayout.CENTER);
    }
}