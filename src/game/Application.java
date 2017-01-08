package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {
    private final int height;
    private final int width;
    private final int numberOfBombs;

    private Game game;
    private JButton[][] buttons;

    private JLabel textLabel = new JLabel();

    private Application(int height, int width, int numberOfBombs) {
        this.height = height;
        this.width = width;
        this.numberOfBombs = numberOfBombs;
        this.game = new Game(numberOfBombs, height, width);

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
        button.addMouseListener(getMouseAdapter(i, j));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        return button;
    }

    private void registerFloodFillListeners() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Field field = game.getField(i, j);
                JButton button = buttons[i][j];
                field.registerFloodFillListener(() -> button.setBackground(Color.darkGray));
            }
        }
    }

    private void displayAllBombs() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                JButton button = buttons[i][j];
                Field field = game.getField(i, j);

                if (field.isBomb) {
                    button.setText("x");
                    button.setForeground(Color.DARK_GRAY);
                    if (field.isFlag) {
                        button.setBackground(Color.GREEN);
                    } else {
                        button.setBackground(Color.red);
                    }

                    button.setText(Integer.toString(field.getAdjacentBombsCount()));
                    button.setForeground(getColorOfNumberOfBombsAdjacentToField(field.numberOfBombsAdjacent));
                }
            }
        }
    }

    private Color getColorOfNumberOfBombsAdjacentToField(int numberOfBombs) {
        Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.LIGHT_GRAY, Color.YELLOW, Color.PINK};
        return colors[numberOfBombs - 1];
    }

    private void createAndShowBoard() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
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
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.pack();

        displayAllBombs();
    }

    private MouseAdapter getMouseAdapter(int x, int y) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Field field = game.getField(x, y);
                JButton button = buttons[x][y];
                if (game.isGameDone) {
                    textLabel.setText("Rozpocznij nowa gre");
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    if (field.isDiscovered || field.isFlag) {
                        textLabel.setText("pole klikniete juz lub flaga = " + field);
                    } else if (field.isBomb) {
                        textLabel.setText("Bomba, przegrales");
                        displayAllBombs();
                    } else {
                        if (field.numberOfBombsAdjacent > 0) {
                            textLabel.setText("");
                            button.setForeground(getColorOfNumberOfBombsAdjacentToField(field.numberOfBombsAdjacent));
                            field.isDiscovered = true;
                            button.setText(Integer.toString(field.getAdjacentBombsCount()));
                        } else {
                            game.floodFill(x, y);
                            textLabel.setText("");
                        }
                        if (game.countDiscoveredFields() - game.numberOfBombs == 0) {
                            textLabel.setText("Wygrales!");
                            displayAllBombs();
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field.isDiscovered) {
                        textLabel.setText("pole juz klikniete");
                    } else if (field.isFlag) {
                        field.isFlag = false;
                        button.setText("");
                        textLabel.setText("");
                    } else {
                        textLabel.setText("");
                        field.isFlag = true;
                        button.setText("?");
                        button.setFont(new Font("Arial", Font.BOLD, 20));
                        button.setForeground(Color.BLACK);
                        if (game.countFlagPoints() == game.numberOfBombs) {
                            textLabel.setText("Wygrales!");
                            displayAllBombs();
                        }
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Application(10, 10, 10).createAndShowBoard();
        });
    }
}
