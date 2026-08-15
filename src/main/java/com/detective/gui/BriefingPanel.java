package com.detective.gui;

import com.detective.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class BriefingPanel extends JPanel {
    private static String getStoryForLevel(int level, String playerName) {
        switch (level) {
            case 1:
                return "Detective " + playerName + ",\n\n" +
                        "Last night, the wealthy Mr. Aldridge was found dead in his mansion. " +
                        "The doors were locked from inside. No forced entry. Three people were in the house that night: " +
                        "Mr. Black, Ms. White, and Dr. Green.\n\n" +
                        "One of them is lying to you. Search the rooms, gather evidence, listen to their alibis carefully - " +
                        "and when you're certain, name the killer.\n\nYou only get one accusation. Choose wisely.";
            case 2:
                return "Detective " + playerName + ",\n\n" +
                        "A blackout hit the corporate tower at exactly 10:00 PM last night. When the lights came back on, " +
                        "an executive was found dead in the boardroom. Security badges, server logs, and a broken badge reader " +
                        "all point to someone inside the building.\n\n" +
                        "Mr. Turner, Ms. Reyes, and Mr. Osei all had access that night. " +
                        "Dig through the evidence, question them carefully, and find the truth.\n\n" +
                        "One accusation. Make it count.";
            case 3:
                return "Detective " + playerName + ",\n\n" +
                        "The final act never finished. A famous actor was found dead backstage moments before curtain call, " +
                        "in front of a full theater. Madame Rosa, Victor Lang, and Elise Moreau were all present that night, " +
                        "each with their own reasons to want the spotlight.\n\n" +
                        "This is your final case, Detective. Everything you've learned comes down to this. " +
                        "Search the stage, the dressing room, and backstage - then name the killer.\n\n" +
                        "The curtain falls either way. Choose wisely.";
            default:
                return "Detective " + playerName + ", a new case awaits. Investigate carefully.";
        }
    }

    public BriefingPanel(MainFrame frame, String playerName, int level) {
        setLayout(new BorderLayout());

        JPanel background = UITheme.gradientPanel(new Color(25, 25, 35), new Color(45, 20, 20));
        background.setLayout(new BorderLayout());

        JLabel title = new JLabel("Level " + level + ": The Case Begins", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));

        JTextArea story = new JTextArea(getStoryForLevel(level, playerName));
        story.setFont(new Font("Serif", Font.PLAIN, 16));
        story.setForeground(new Color(230, 230, 230));
        story.setLineWrap(true);
        story.setWrapStyleWord(true);
        story.setEditable(false);
        story.setOpaque(false);
        story.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton beginButton = UITheme.styledButton("Begin Investigation");
        beginButton.addActionListener(e -> frame.startLevel(level));

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