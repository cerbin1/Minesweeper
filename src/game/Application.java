package game;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {
    private final static Color[] BOMBS_COUNTER_COLORS = {
            BLUE, GREEN, RED, MAGENTA, ORANGE, LIGHT_GRAY, YELLOW, PINK
    };

    private final int height;
    private final int width;

    private Game game;
    private JButton[][] buttons;

    private JLabel textLabel = new JLabel();

    private Application(int height, int width, int numberOfBombs) {
        this.height = height;
        this.width = width;
        this.game = GameFactory.create(width, height, numberOfBombs);

        this.buttons = createJButtons(height, width);
        registerFloodFillListeners();
    }

    private JButton[][] createJButtons(int height, int width) {
        JButton[][] buttons = new JButton[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
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

    private void registerFloodFillListeners() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Field field = game.getField(i, j);
                JButton button = buttons[i][j];
                field.registerFloodFillListener(() -> {
                    if (field.numberOfBombsAdjacent == 0) {
                        button.setBackground(Color.darkGray);
                    } else {
                        button.setText("" + field.numberOfBombsAdjacent);
                    }
                });
            }
        }
    }

    void displayAllBombs() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = buttons[i][j];
                Field field = game.getField(i, j);

                if (field.isBomb) {
                    button.setBackground(field.isFlag ? GREEN : RED);
                    button.setForeground(getBombCounterColor(field.numberOfBombsAdjacent));
                }
            }
        }
    }

    Color getBombCounterColor(int numberOfBombs) {
        return BOMBS_COUNTER_COLORS[numberOfBombs - 1];
    }

    private void createAndShowBoard() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);

        textLabel.setText("Zecznij klikac");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(game.height, game.width));
        for (int i = 0; i < game.height; i++) {
            for (int j = 0; j < game.width; j++) {
                panel.add(buttons[i][j]);
            }
        }

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(textLabel, BorderLayout.PAGE_END);

        frame.add(outerPanel);
        frame.pack();
    }

    JButton getButton(int x, int y) {
        return buttons[x][y];
    }

    void setStatusText(String text) {
        textLabel.setText(text);
    }

    void clearStatusText() {
        textLabel.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Application(10, 10, 10).createAndShowBoard());
    }

    Game getGame() {
        return game;
    }
}
