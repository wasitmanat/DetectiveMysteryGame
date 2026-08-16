package com.detective.util;

import javax.swing.*;
import java.awt.*;

public class UITheme {
    public static final Color BG_DARK = new Color(30, 30, 40);
    public static final Color BG_PANEL = new Color(245, 240, 230);
    public static final Color ACCENT = new Color(178, 34, 34);
    public static final Color TEXT_LIGHT = new Color(230, 230, 230);
    public static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 30);
    public static final Font BODY_FONT = new Font("SansSerif", Font.PLAIN, 21);

    public static JButton styledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(new Color(60, 60, 70));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return btn;
    }

    public static JPanel gradientPanel(Color top, Color bottom) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }
}