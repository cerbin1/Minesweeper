package game;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {
    private final Color[] NEAR_BOMBS_COUNTER_COLORS = {
            Color.BLUE, Color.GREEN, Color.RED, Color.MAGENTA, Color.ORANGE, Color.LIGHT_GRAY, Color.YELLOW, Color.PINK
    };

    private final int height;
    private final int width;

    private JLabel textLabel;
    private Game game;
    private JButton[][] buttons;

    private Application(int height, int width, int numberOfBombs) {
        this.height = height;
        this.width = width;
        this.game = new Game(width, height, numberOfBombs);
        textLabel = new JLabel();
    }


    private void createAndShowBoard() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        JPanel outerPanel = new JPanel();
        panel.setLayout(new GridLayout(game.getHeight(), game.getWidth()));
        buttons = createJButtons(width, height);


        textLabel.setText("Zecznij klikac");
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(textLabel, BorderLayout.PAGE_END);
        frame.add(outerPanel);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    private JButton[][] createJButtons(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buttons[i][j] = createSingleJButton(i, j);
            }
        }
        return buttons;
    }

    private JButton createSingleJButton(int x, int y) {
        JButton button = new JButton();
        button.addMouseListener(getMouseAdapter(game.getField(x, y), x, y));
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(50, 50));
        return button;
    }

    private MouseAdapter getMouseAdapter(final Field cell, int x, int y) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (game.isGameDone) {
                    textLabel.setText("Rozpocznij nowa gre");
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    if (cell.isDiscovered || cell.isFlag) {
                        textLabel.setText("pole klikniete juz lub flaga");
                    } else if (cell.isBomb) {
                        textLabel.setText("Bomba, przegrales");
                        displayAllBombs();
                    } else {
                        if (cell.getNumberOfBombsAdjacent() > 0) {
                            textLabel.setText("");
                            buttons[x][y].setForeground(NEAR_BOMBS_COUNTER_COLORS[cell.getNumberOfBombsAdjacent() - 1]);
                            cell.isDiscovered = true;
                            buttons[x][y].setText(Integer.toString(cell.getNumberOfBombsAdjacent()));
                        } else {
                            game.floodFill(x, y, buttons);
                            textLabel.setText("");
                        }
                        if (game.countFieldsDiscovered() - game.getNumberOfBombs() == 0) {
                            textLabel.setText("Wygrales!");
                            displayAllBombs();
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (cell.isDiscovered) {
                        textLabel.setText("pole juz klikniete");
                    } else if (cell.isFlag) {
                        cell.isFlag = false;
                        buttons[x][y].setText("");
                        textLabel.setText("");
                    } else {
                        textLabel.setText("");
                        cell.isFlag = true;
                        buttons[x][y].setText("?");
                        buttons[x][y].setFont(new Font("Arial", Font.BOLD, 20));
                        buttons[x][y].setForeground(Color.BLACK);
                        if (game.countPointsFromFlags() == game.getNumberOfBombs()) {
                            textLabel.setText("Wygrales!");
                            displayAllBombs();
                        }
                    }
                }
            }
        };
    }

    private void displayAllBombs() {
        for (int i = 0; i < game.getWidth(); i++) {
            for (int j = 0; j < game.getHeight(); j++) {
                if (game.getField(i, j).isBomb) {
                    buttons[i][j].setText("x");
                    buttons[i][j].setForeground(Color.DARK_GRAY);
                    if (game.getField(i, j).isFlag) {
                        buttons[i][j].setBackground(Color.GREEN);
                    } else {
                        buttons[i][j].setBackground(Color.red);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Application(10, 10, 10).createAndShowBoard();
            }
        });
    }
}
