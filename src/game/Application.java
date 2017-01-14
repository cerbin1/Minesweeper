package game;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import static java.awt.Color.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {
    private final static Color[] BOMBS_COUNTER_COLORS = {
            BLUE, GREEN, RED, MAGENTA, ORANGE, LIGHT_GRAY, YELLOW, PINK
    };

    private final int width;
    private final int height;

    private Game game;
    private JButton[][] buttons;

    private boolean firstClick = true;

    private JLabel messageBox;
    private JLabel bombsCounter;

    private int bombsLeftToFlagCounter;

    private Application(int width, int height, int numberOfBombs) {
        this.width = width;
        this.height = height;
        this.game = GameFactory.create(width, height, numberOfBombs);

        this.buttons = createJButtons(width, height);
        setFloodFillListeners();
        bombsLeftToFlagCounter = numberOfBombs;
    }

    private JButton[][] createJButtons(int width, int height) {
        JButton[][] buttons = new JButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buttons[i][j] = createSingleJButton(i, j);
            }
        }
        return buttons;
    }

    private JButton createSingleJButton(int i, int j) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.addMouseListener(new FieldMouseAdapter(this, i, j));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        return button;
    }

    private void setFloodFillListeners() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Field field = game.getField(i, j);
                JButton button = buttons[i][j];
                field.addFloodFillListener(() -> {
                    if (field.getNearBombsCounter() == 0) {
                        button.setBackground(Color.darkGray);
                    } else {
                        button.setForeground(getBombCounterColor(field.getNearBombsCounter()));
                        button.setText("" + field.getNearBombsCounter());
                    }
                });
            }
        }
    }

    void displayAllBombs() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JButton button = buttons[i][j];
                Field field = game.getField(i, j);

                if (field.isBomb()) {
                    button.setBackground(field.isFlag() ? GREEN : RED);
                    button.setForeground(getBombCounterColor(field.getNearBombsCounter()));
                }
            }
        }
    }

    Color getBombCounterColor(int numberOfBombs) {
        return BOMBS_COUNTER_COLORS[numberOfBombs - 1];
    }

    private void createAndShowBoard() {
        JFrame frame = createFrame("Minesweeper");

        messageBox = createDefaultTextLabel("TextLabel", "Start clicking");
        bombsCounter = createDefaultTextLabel("BombsCounter", "Bombs left: " + bombsLeftToFlagCounter);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(game.width, game.height));
        for (int i = 0; i < game.width; i++) {
            for (int j = 0; j < game.height; j++) {
                panel.add(buttons[i][j]);
            }
        }

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(bombsCounter, BorderLayout.PAGE_START);
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(messageBox, BorderLayout.PAGE_END);

        frame.add(outerPanel);
        frame.pack();
    }

    private JLabel createDefaultTextLabel(String name, String defaultText) {
        JLabel textLabel = new JLabel(name, SwingConstants.CENTER);
        textLabel.setText(defaultText);
        textLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        return textLabel;
    }

    private JFrame createFrame(String frameName) {
        JFrame frame = new JFrame(frameName);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        return frame;
    }

    JButton getButton(int x, int y) {
        return buttons[x][y];
    }

    void setStatusText(String text) {
        messageBox.setText(text);
    }

    void clearMessageBox() {
        messageBox.setText("");
    }

    void updateBombsCounterText(int number) {
        bombsLeftToFlagCounter += number;
        bombsCounter.setText("Bombs left: " + bombsLeftToFlagCounter);
        if (bombsLeftToFlagCounter < 0) {
            messageBox.setText("Some flags are wrong!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Application(10, 10, 10).createAndShowBoard());
    }

    Game getGame() {
        return game;
    }

    boolean isFirstClick() {
        return firstClick;
    }

    void setFirstClick(boolean firstClick) {
        this.firstClick = firstClick;
    }
}
