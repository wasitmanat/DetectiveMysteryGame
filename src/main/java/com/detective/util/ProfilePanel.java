package com.detective.gui;

import com.detective.util.SaveManager;
import com.detective.util.UITheme;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private JLabel statusLabel;

    public ProfilePanel(MainFrame frame) {
        setLayout(new BorderLayout());

        JPanel background = UITheme.gradientPanel(new Color(20, 20, 30), new Color(45, 25, 40));
        background.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("🕵");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Detective Profile");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Enter your name to begin or continue your investigation.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(210, 210, 210));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField(18);
        nameField.setMaximumSize(new Dimension(250, 32));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(255, 210, 120));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField.addCaretListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty() && SaveManager.hasSave(name)) {
                int[] saved = SaveManager.loadProgress(name);
                statusLabel.setText("📁 Saved case found: Level " + saved[0] + " | Score: " + saved[1]);
            } else {
                statusLabel.setText(" ");
            }
        });

        JButton continueButton = UITheme.styledButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a detective name.");
                return;
            }
            frame.handleNameEntry(name);
        });

        nameField.addActionListener(e -> continueButton.doClick());

        card.add(icon);
        card.add(Box.createVerticalStrut(5));
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(20));
        card.add(nameField);
        card.add(Box.createVerticalStrut(8));
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(15));
        card.add(continueButton);

        background.add(card);
        add(background, BorderLayout.CENTER);
    }
}