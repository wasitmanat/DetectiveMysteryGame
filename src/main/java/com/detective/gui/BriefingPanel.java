package com.detective.gui;

import javax.swing.*;
import java.awt.*;

public class BriefingPanel extends JPanel {
    public BriefingPanel(MainFrame frame, String playerName) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("The Case Begins", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));

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
        story.setLineWrap(true);
        story.setWrapStyleWord(true);
        story.setEditable(false);
        story.setOpaque(false);
        story.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton beginButton = new JButton("Begin Investigation");
        beginButton.addActionListener(e -> frame.launchGame(playerName));

        JPanel bottom = new JPanel();
        bottom.add(beginButton);

        add(title, BorderLayout.NORTH);
        add(story, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}