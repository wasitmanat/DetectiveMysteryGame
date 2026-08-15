package com.detective.gui;

import com.detective.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class BriefingPanel extends JPanel {
    public BriefingPanel(MainFrame frame, String playerName) {
        setLayout(new BorderLayout());

        JPanel background = UITheme.gradientPanel(new Color(25, 25, 35), new Color(45, 20, 20));
        background.setLayout(new BorderLayout());

        JLabel title = new JLabel("The Case Begins", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JTextArea story = new JTextArea(
                "Detective " + playerName + ",\n\n" +
                        "Last night, the wealthy Mr. Aldridge was found dead in his mansion. " +
                        "The doors were locked from inside. No forced entry. Three people were in the house that night: " +
                        "Mr. Black, Ms. White, and Dr. Green.\n\n" +
                        "One of them is lying to you. Search the rooms, gather evidence, listen to their alibis carefully - " +
                        "and when you're certain, name the killer.\n\n" +
                        "You only get one accusation. Choose wisely."
        );
        story.setFont(new Font("Serif", Font.PLAIN, 16));
        story.setForeground(new Color(230, 230, 230));
        story.setLineWrap(true);
        story.setWrapStyleWord(true);
        story.setEditable(false);
        story.setOpaque(false);
        story.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton beginButton = UITheme.styledButton("Begin Investigation");
        beginButton.addActionListener(e -> frame.launchGame(playerName));

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        bottom.add(beginButton);

        background.add(title, BorderLayout.NORTH);
        background.add(story, BorderLayout.CENTER);
        background.add(bottom, BorderLayout.SOUTH);

        add(background, BorderLayout.CENTER);
    }
}