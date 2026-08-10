package com.detective.gui;

import com.detective.command.AccuseCommand;
import com.detective.command.CollectEvidenceCommand;
import com.detective.command.Command;
import com.detective.manager.GameManager;
import com.detective.model.Evidence;
import com.detective.model.Room;
import com.detective.model.Suspect;
import com.detective.observer.GameObserver;

import javax.swing.*;
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
    private JLabel instructionsLabel;

    public GamePanel(MainFrame frame) {
        this.frame = frame;
        GameManager.getInstance().addObserver(this);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildTopPanel() {
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        instructionsLabel = new JLabel(
                "<html><b>How to play:</b> Pick a room, click evidence to collect it. "
                        + "Check suspect alibis, then click a suspect's name when you're ready to accuse. "
                        + "Only one suspect is guilty!</html>"
        );
        instructionsLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 8, 5));

        JPanel roomRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        roomSelector = new JComboBox<>();
        for (Room room : GameManager.getInstance().getRooms()) {
            roomSelector.addItem(room.getName());
        }
        roomSelector.addActionListener(e -> refreshRoom());

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        roomRow.add(new JLabel("Room:"));
        roomRow.add(roomSelector);
        roomRow.add(Box.createHorizontalStrut(30));
        roomRow.add(scoreLabel);

        roomDescriptionLabel = new JLabel();
        roomDescriptionLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        roomDescriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        top.add(instructionsLabel);
        top.add(roomRow);
        top.add(roomDescriptionLabel);
        return top;
    }

    private JPanel buildCenterPanel() {
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));

        evidenceButtonPanel = new JPanel();
        evidenceButtonPanel.setLayout(new BoxLayout(evidenceButtonPanel, BoxLayout.Y_AXIS));
        evidenceButtonPanel.setBorder(BorderFactory.createTitledBorder("Evidence in Room"));

        suspectButtonPanel = new JPanel();
        suspectButtonPanel.setLayout(new BoxLayout(suspectButtonPanel, BoxLayout.Y_AXIS));
        suspectButtonPanel.setBorder(BorderFactory.createTitledBorder("Suspects (click name = accuse)"));
        for (Suspect suspect : GameManager.getInstance().getSuspects()) {
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
            JButton btn = new JButton("Accuse: " + suspect.getName());
            btn.addActionListener(e -> handleAccuse(suspect));
            JLabel alibi = new JLabel("<html><i>Alibi: " + suspect.getAlibi() + "</i></html>");
            alibi.setBorder(BorderFactory.createEmptyBorder(2, 5, 10, 5));
            row.add(btn);
            row.add(alibi);
            suspectButtonPanel.add(row);
        }

        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));
        inventoryLabel = new JLabel("<html>(empty)</html>");
        inventoryLabel.setVerticalAlignment(SwingConstants.TOP);
        inventoryPanel.add(inventoryLabel, BorderLayout.NORTH);

        center.add(evidenceButtonPanel);
        center.add(suspectButtonPanel);
        center.add(inventoryPanel);

        refreshRoom();
        return center;
    }

    private JPanel buildBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout());
        logArea = new JTextArea(6, 40);
        logArea.setEditable(false);
        bottom.add(new JScrollPane(logArea), BorderLayout.CENTER);
        bottom.setBorder(BorderFactory.createTitledBorder("Investigation Log"));
        return bottom;
    }

    private void refreshRoom() {
        evidenceButtonPanel.removeAll();
        int index = roomSelector.getSelectedIndex();
        if (index < 0) return;
        Room room = GameManager.getInstance().getRooms().get(index);
        roomDescriptionLabel.setText(room.getDescription());

        if (room.getEvidenceList().isEmpty()) {
            evidenceButtonPanel.add(new JLabel("  Nothing here anymore."));
        }
        for (Evidence evidence : room.getEvidenceList()) {
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
            JButton btn = new JButton("Collect: " + evidence.getName());
            btn.addActionListener(e -> handleCollect(evidence, room));
            JLabel desc = new JLabel("<html><i>" + evidence.getDescription() + "</i></html>");
            desc.setBorder(BorderFactory.createEmptyBorder(2, 5, 10, 5));
            row.add(btn);
            row.add(desc);
            evidenceButtonPanel.add(row);
        }
        evidenceButtonPanel.revalidate();
        evidenceButtonPanel.repaint();
    }

    private void handleCollect(Evidence evidence, Room room) {
        Command command = new CollectEvidenceCommand(evidence);
        command.execute();
        room.getEvidenceList().remove(evidence);
        refreshRoom();
    }

    private void handleAccuse(Suspect suspect) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Accuse " + suspect.getName() + "? This ends the investigation.",
                "Confirm Accusation",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        AccuseCommand command = new AccuseCommand(suspect);
        command.execute();
        if (command.isCorrect()) {
            frame.showScreen("win");
        } else {
            frame.showScreen("lose");
        }
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

        logArea.setText(String.join("\n", GameManager.getInstance().getInvestigationLog()));
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}