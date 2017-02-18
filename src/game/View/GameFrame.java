package game.View;

import game.GameStateListener;
import game.Mediator;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GameFrame implements GameStateListener {
    private JFrame frame;
    private JLabel messageBox, bombsLeftLabel;

    public GameFrame(Mediator mediator) {
        initializeComponents(mediator);
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    private void initializeComponents(Mediator mediator) {
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setResizable(true);
        messageBox = createDefaultTextLabel("TextLabel", "Start clicking");
        bombsLeftLabel = createDefaultTextLabel("BombsCounter", "");
        JPanel panel = createJPanelWithJButtons(mediator);
        JPanel outerPanel = createOuterPanel(panel);
        frame.add(outerPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private JLabel createDefaultTextLabel(String name, String defaultText) {
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setText(defaultText);
        label.setBorder(createEmptyBorder(15, 15, 15, 15));
        label.setFont(new Font("Arial", Font.BOLD, 20));
        return label;
    }

    private JPanel createJPanelWithJButtons(Mediator mediator) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(mediator.getWidth(), mediator.getHeight()));
        for (int i = 0; i < mediator.getWidth(); i++) {
            for (int j = 0; j < mediator.getHeight(); j++) {
                panel.add(mediator.getJButton(i, j));
            }
        }
        return panel;
    }

    private JPanel createOuterPanel(JPanel innerPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(bombsLeftLabel, BorderLayout.PAGE_START);
        panel.add(innerPanel, BorderLayout.CENTER);
        panel.add(messageBox, BorderLayout.PAGE_END);
        return panel;
    }

    private void playSound() {
        try {
            String soundPath = "C:\\Users\\bartek\\Desktop\\Projekty\\Minesweeper\\resource\\bip.wav";
            InputStream inputStream = new FileInputStream(soundPath);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @Override
    public void bombsCountChanged(int bombsCount) {
        bombsLeftLabel.setText("Bombs left: " + bombsCount);
    }

    @Override
    public void gameFinished(boolean win) {
        if (win) {
            messageBox.setText("You win!");
        } else {
            messageBox.setText("Boom! You lose!");
            playSound();
        }
    }

    @Override
    public void displayMessage(String text) {
        messageBox.setText(text);
    }

    @Override
    public void clearMessage() {
        messageBox.setText("");
    }
}
