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
        setBackground(new Color(245, 240, 230));

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildCenterPanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildTopPanel() {
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        instructionsLabel = new JLabel(
                "<html><b>How to play:</b> Pick a room, click evidence to collect it. "
                        + "Interrogate suspects for hints, then accuse when you're ready. "
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
        suspectButtonPanel.setBorder(BorderFactory.createTitledBorder("Suspects"));

        for (Suspect suspect : GameManager.getInstance().getSuspects()) {
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
            row.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

            JLabel nameLabel = new JLabel(suspect.getName());
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

            JLabel alibi = new JLabel("<html><i>Alibi: " + suspect.getAlibi() + "</i></html>");
            alibi.setBorder(BorderFactory.createEmptyBorder(2, 0, 5, 0));

            JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            JButton interrogateBtn = new JButton("Interrogate");
            interrogateBtn.addActionListener(e -> handleInterrogate(suspect));

            JButton accuseBtn = new JButton("Accuse");
            accuseBtn.setForeground(new Color(150, 0, 0));
            accuseBtn.addActionListener(e -> handleAccuse(suspect));

            buttonRow.add(interrogateBtn);
            buttonRow.add(accuseBtn);

            row.add(nameLabel);
            row.add(alibi);
            row.add(buttonRow);
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

        String event = RandomEventUtil.maybeTriggerEvent();
        if (event != null) {
            GameManager.getInstance().addLog("[Event] " + event);
        }

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

        logArea.setText(String.join("\n", GameManager.getInstance().getInvestigationLog()));
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}