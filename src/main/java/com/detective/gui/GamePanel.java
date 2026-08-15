package com.detective.gui;

import com.detective.command.AccuseCommand;
import com.detective.command.CollectEvidenceCommand;
import com.detective.command.Command;
import com.detective.command.InterrogateCommand;
import com.detective.manager.GameManager;
import com.detective.model.Evidence;
import com.detective.model.Room;
import com.detective.model.Suspect;
import com.detective.observer.GameObserver;
import com.detective.util.RandomEventUtil;
import com.detective.util.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel implements GameObserver {
    private MainFrame frame;
    private JComboBox<String> roomSelector;
    private JLabel roomDescriptionLabel;
    private JPanel evidenceButtonPanel;
    private JPanel suspectButtonPanel;
    private JTextArea logArea;
    private JLabel inventoryLabel;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel timerLabel;
    private JProgressBar evidenceProgress;
    private int totalEvidenceThisCase;
    private Timer countdownTimer;
    private int secondsRemaining = 180;

    private static final Color[] AVATAR_COLORS = {
            new Color(178, 58, 58), new Color(58, 110, 178), new Color(90, 140, 90),
            new Color(160, 110, 60), new Color(130, 80, 150), new Color(70, 130, 130)
    };

    public GamePanel(MainFrame frame) {
        this.frame = frame;
        GameManager.getInstance().addObserver(this);
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(12, 12, 12, 12));
        setBackground(new Color(228, 219, 197));

        totalEvidenceThisCase = countTotalEvidence();

        add(buildHeaderPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        startTimer();
    }

    private int countTotalEvidence() {
        int total = GameManager.getInstance().getCollectedEvidence().size();
        for (Room r : GameManager.getInstance().getRooms()) {
            total += r.getEvidenceList().size();
        }
        return total;
    }

    private void startTimer() {
        secondsRemaining = 180;
        countdownTimer = new Timer(1000, e -> {
            secondsRemaining--;
            updateTimerLabel();
            if (secondsRemaining <= 0) {
                countdownTimer.stop();
                handleTimeUp();
            }
        });
        countdownTimer.start();
    }

    private void updateTimerLabel() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        if (secondsRemaining <= 30) {
            timerLabel.setForeground(new Color(220, 60, 60));
        }
    }

    private void handleTimeUp() {
        JOptionPane.showMessageDialog(this, "Time's up! The trail has gone cold.", "Time Expired", JOptionPane.WARNING_MESSAGE);
        String actualCulprit = "Unknown";
        for (Suspect s : GameManager.getInstance().getSuspects()) {
            if (s.isGuilty()) {
                actualCulprit = s.getName();
                break;
            }
        }
        frame.showResult(false, actualCulprit);
    }

    private void stopTimer() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
    }

    private JPanel buildHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(45, 35, 40));
        header.setBorder(new EmptyBorder(12, 16, 12, 16));

        levelLabel = new JLabel("LEVEL " + GameManager.getInstance().getCurrentLevel() + " OF 3");
        levelLabel.setFont(new Font("Serif", Font.BOLD, 18));
        levelLabel.setForeground(Color.WHITE);

        JPanel rightInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        rightInfo.setOpaque(false);

        timerLabel = new JLabel("Time: 03:00");
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        timerLabel.setForeground(new Color(255, 255, 255));

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(255, 210, 120));

        rightInfo.add(timerLabel);
        rightInfo.add(scoreLabel);

        JPanel roomRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        roomRow.setOpaque(false);
        JLabel roomIcon = new JLabel("Room:");
        roomIcon.setForeground(new Color(220, 210, 200));
        roomSelector = new JComboBox<>();
        for (Room room : GameManager.getInstance().getRooms()) {
            roomSelector.addItem(room.getName());
        }
        roomSelector.setFont(new Font("SansSerif", Font.PLAIN, 13));
        roomSelector.addActionListener(e -> refreshRoom());

        JButton hintButton = new JButton("Use Hint (-15 pts)");
        hintButton.setBackground(new Color(200, 160, 60));
        hintButton.setForeground(Color.WHITE);
        hintButton.setFocusPainted(false);
        hintButton.addActionListener(e -> handleHint());

        roomRow.add(roomIcon);
        roomRow.add(roomSelector);
        roomRow.add(Box.createHorizontalStrut(10));
        roomRow.add(hintButton);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.add(levelLabel, BorderLayout.WEST);
        topRow.add(rightInfo, BorderLayout.EAST);

        JPanel midRow = new JPanel(new BorderLayout());
        midRow.setOpaque(false);
        midRow.setBorder(new EmptyBorder(8, 0, 0, 0));

        roomDescriptionLabel = new JLabel();
        roomDescriptionLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        roomDescriptionLabel.setForeground(new Color(220, 210, 200));

        evidenceProgress = new JProgressBar(0, Math.max(totalEvidenceThisCase, 1));
        evidenceProgress.setStringPainted(true);
        evidenceProgress.setString("Evidence Found");
        evidenceProgress.setForeground(new Color(178, 34, 34));
        evidenceProgress.setPreferredSize(new Dimension(160, 18));

        JPanel roomAndBar = new JPanel(new BorderLayout());
        roomAndBar.setOpaque(false);
        roomAndBar.add(roomRow, BorderLayout.WEST);
        roomAndBar.add(evidenceProgress, BorderLayout.EAST);

        midRow.add(roomAndBar, BorderLayout.NORTH);
        midRow.add(roomDescriptionLabel, BorderLayout.SOUTH);

        header.add(topRow, BorderLayout.NORTH);
        header.add(midRow, BorderLayout.SOUTH);
        return header;
    }

    private void handleHint() {
        if (GameManager.getInstance().isHintUsedThisLevel()) {
            JOptionPane.showMessageDialog(this, "You already used your hint for this case:\n\n" + GameManager.getInstance().getHint(), "Hint", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Use a hint for -15 points?", "Use Hint", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        GameManager.getInstance().useHint();
        JOptionPane.showMessageDialog(this, GameManager.getInstance().getHint(), "Hint", JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel buildCenterPanel() {
        JPanel center = new JPanel(new GridLayout(1, 3, 12, 12));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(12, 0, 12, 0));

        evidenceButtonPanel = new JPanel();
        evidenceButtonPanel.setLayout(new BoxLayout(evidenceButtonPanel, BoxLayout.Y_AXIS));
        JScrollPane evidenceScroll = wrapInCard(evidenceButtonPanel, "Evidence in Room");

        suspectButtonPanel = new JPanel();
        suspectButtonPanel.setLayout(new BoxLayout(suspectButtonPanel, BoxLayout.Y_AXIS));
        JScrollPane suspectScroll = wrapInCard(suspectButtonPanel, "Suspects");

        int colorIndex = 0;
        for (Suspect suspect : GameManager.getInstance().getSuspects()) {
            suspectButtonPanel.add(buildSuspectCard(suspect, AVATAR_COLORS[colorIndex % AVATAR_COLORS.length]));
            suspectButtonPanel.add(Box.createVerticalStrut(8));
            colorIndex++;
        }

        JPanel inventoryInner = new JPanel();
        inventoryInner.setLayout(new BoxLayout(inventoryInner, BoxLayout.Y_AXIS));
        inventoryLabel = new JLabel("<html>(empty)</html>");
        inventoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        inventoryInner.add(inventoryLabel);
        JScrollPane inventoryScroll = wrapInCard(inventoryInner, "Inventory");

        center.add(evidenceScroll);
        center.add(suspectScroll);
        center.add(inventoryScroll);

        refreshRoom();
        return center;
    }

    private JScrollPane wrapInCard(JComponent content, String titleText) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(250, 246, 235));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 120, 90), 1),
                new EmptyBorder(8, 8, 8, 8)
        ));

        JLabel titleLabel = new JLabel(titleText);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        titleLabel.setForeground(new Color(80, 50, 40));
        titleLabel.setBorder(new EmptyBorder(0, 0, 8, 0));

        content.setOpaque(false);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JComponent createAvatar(String name, Color color) {
        String initial = name.replace("Mr.", "").replace("Ms.", "").replace("Dr.", "")
                .replace("Madame", "").trim();
        String letter = initial.isEmpty() ? "?" : initial.substring(0, 1).toUpperCase();

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(letter)) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(letter, x, y);
            }
        };
        avatar.setPreferredSize(new Dimension(48, 48));
        avatar.setMaximumSize(new Dimension(48, 48));
        avatar.setOpaque(false);
        return avatar;
    }

    private JPanel buildSuspectCard(Suspect suspect, Color avatarColor) {
        JPanel card = new JPanel(new BorderLayout(10, 4));
        card.setBackground(new Color(240, 232, 215));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 150, 110), 1),
                new EmptyBorder(8, 8, 8, 8)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        JComponent avatar = createAvatar(suspect.getName(), avatarColor);
        JPanel avatarWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        avatarWrap.setOpaque(false);
        avatarWrap.add(avatar);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(suspect.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel alibi = new JLabel("<html><i>Alibi: " + suspect.getAlibi() + "</i></html>");
        alibi.setFont(new Font("SansSerif", Font.PLAIN, 11));
        alibi.setBorder(new EmptyBorder(2, 0, 6, 0));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        buttonRow.setOpaque(false);
        JButton interrogateBtn = UITheme.styledButton("Interrogate");
        interrogateBtn.addActionListener(e -> handleInterrogate(suspect));

        JButton accuseBtn = new JButton("Accuse");
        accuseBtn.setBackground(new Color(150, 0, 0));
        accuseBtn.setForeground(Color.WHITE);
        accuseBtn.setFocusPainted(false);
        accuseBtn.addActionListener(e -> handleAccuse(suspect));

        buttonRow.add(interrogateBtn);
        buttonRow.add(accuseBtn);

        textPanel.add(nameLabel);
        textPanel.add(alibi);
        textPanel.add(buttonRow);

        card.add(avatarWrap, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(40, 35, 30));
        bottom.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel logTitle = new JLabel("Investigation Log");
        logTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        logTitle.setForeground(new Color(230, 220, 200));
        logTitle.setBorder(new EmptyBorder(0, 0, 6, 0));

        logArea = new JTextArea(6, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(new Color(25, 22, 20));
        logArea.setForeground(new Color(120, 220, 140));
        logArea.setCaretColor(new Color(120, 220, 140));
        logArea.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 70, 60)));

        bottom.add(logTitle, BorderLayout.NORTH);
        bottom.add(scrollPane, BorderLayout.CENTER);
        return bottom;
    }

    private void refreshRoom() {
        evidenceButtonPanel.removeAll();
        int index = roomSelector.getSelectedIndex();
        if (index < 0) return;
        Room room = GameManager.getInstance().getRooms().get(index);
        roomDescriptionLabel.setText(room.getDescription());

        String event = RandomEventUtil.maybeTriggerEvent();
        if (event != null) {
            GameManager.getInstance().addLog("[Event] " + event);
        }

        if (room.getEvidenceList().isEmpty()) {
            JLabel empty = new JLabel("Nothing here anymore.");
            empty.setFont(new Font("SansSerif", Font.ITALIC, 12));
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            evidenceButtonPanel.add(empty);
        }
        for (Evidence evidence : room.getEvidenceList()) {
            JPanel card = new JPanel(new BorderLayout(6, 4));
            card.setBackground(new Color(240, 232, 215));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 150, 110), 1),
                    new EmptyBorder(8, 8, 8, 8)
            ));
            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

            JButton btn = new JButton("Collect: " + evidence.getName());
            JLabel desc = new JLabel("<html><i>" + evidence.getDescription() + "</i></html>");
            desc.setFont(new Font("SansSerif", Font.PLAIN, 11));

            btn.addActionListener(e -> handleCollect(evidence, room));

            card.add(btn, BorderLayout.NORTH);
            card.add(desc, BorderLayout.CENTER);

            evidenceButtonPanel.add(card);
            evidenceButtonPanel.add(Box.createVerticalStrut(8));
        }
        evidenceButtonPanel.revalidate();
        evidenceButtonPanel.repaint();
    }

    private void handleCollect(Evidence evidence, Room room) {
        Command command = new CollectEvidenceCommand(evidence);
        command.execute();
        room.getEvidenceList().remove(evidence);
        JOptionPane.showMessageDialog(
                this,
                "Evidence collected:\n\n" + evidence.getName() + "\n" + evidence.getDescription(),
                "New Clue",
                JOptionPane.INFORMATION_MESSAGE
        );
        refreshRoom();
    }

    private void handleInterrogate(Suspect suspect) {
        InterrogateCommand command = new InterrogateCommand(suspect);
        command.execute();
        JOptionPane.showMessageDialog(
                this,
                "You question " + suspect.getName() + " about their alibi.\n\n" + command.getReaction(),
                "Interrogation",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void handleAccuse(Suspect suspect) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Accuse " + suspect.getName() + "? This ends the investigation.",
                "Confirm Accusation",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        stopTimer();

        String actualCulprit = suspect.getName();
        for (Suspect s : GameManager.getInstance().getSuspects()) {
            if (s.isGuilty()) {
                actualCulprit = s.getName();
                break;
            }
        }

        AccuseCommand command = new AccuseCommand(suspect);
        command.execute();
        frame.showResult(command.isCorrect(), actualCulprit);
    }

    @Override
    public void onGameUpdate() {
        List<Evidence> collected = GameManager.getInstance().getCollectedEvidence();
        StringBuilder sb = new StringBuilder("<html>");
        for (Evidence e : collected) {
            sb.append("&bull; ").append(e.getName()).append("<br>");
        }
        sb.append("</html>");
        inventoryLabel.setText(collected.isEmpty() ? "<html>(empty)</html>" : sb.toString());

        scoreLabel.setText("Score: " + GameManager.getInstance().getPlayer().getScore());

        if (evidenceProgress != null) {
            evidenceProgress.setValue(collected.size());
            evidenceProgress.setString(collected.size() + " / " + totalEvidenceThisCase + " Found");
        }

        logArea.setText(String.join("\n", GameManager.getInstance().getInvestigationLog()));
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}