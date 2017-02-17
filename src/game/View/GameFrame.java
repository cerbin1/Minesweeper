package game.View;

import game.Mediator;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GameFrame implements GameStateListener {
    private JLabel messageBox, bombsLeftLabel;

    public GameFrame(Mediator mediator) {
        initializeComponents(mediator);
    }

    private void initializeComponents(Mediator mediator) {
        JFrame frame = createMainJFrame();
        messageBox = createDefaultTextLabel("TextLabel", "Start clicking");
        bombsLeftLabel = createDefaultTextLabel("BombsCounter", "");
        JPanel panel = createJPanelWithJButtons(mediator);
        JPanel outerPanel = createOuterPanel(panel);
        frame.add(outerPanel);
        frame.pack();
    }

    private JFrame createMainJFrame() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        return frame;
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
        panel.add(messageBox, BorderLayout.PAGE_START);
        panel.add(innerPanel, BorderLayout.CENTER);
        panel.add(bombsLeftLabel, BorderLayout.PAGE_END);
        return panel;
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
