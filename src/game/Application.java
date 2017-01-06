package game;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {
    private void createAndShowBoard() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Field[][] fields = new Field[Game.height][Game.width];

        JPanel panel = new JPanel();
        JPanel outerPanel = new JPanel();
        panel.setLayout(new GridLayout(Game.height, Game.width));
        JLabel textLabel = new JLabel();

        Game game = new Game();

        for (int i = 0; i < Game.height; i++) {
            for (int j = 0; j < Game.width; j++) {
                Field field = new Field(50, 50);
                field.button.addMouseListener(getMouseAdapter(fields, game, field, i, j, textLabel));
                field.button.setFont(new Font("Arial", Font.BOLD, 20));

                panel.add(field.button);
                fields[i][j] = field;
            }
        }

        textLabel.setText("Zecznij klikac");
        outerPanel.setLayout(new BorderLayout());
        outerPanel.add(panel, BorderLayout.CENTER);
        outerPanel.add(textLabel, BorderLayout.PAGE_END);
        frame.add(outerPanel);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.pack();
        game.setBombs(fields, Game.numberOfBombs);
        game.displayAllBombs(fields);//TODO after tests delete this
    }

    private MouseAdapter getMouseAdapter(final Field[][] fields, final Game game, final Field field, int i, int j, JLabel label) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Game.isGameDone) {
                    label.setText("Rozpocznij nowa gre");
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    if (field.isDiscovered || field.isFlag) {
                        label.setText("pole klikniete juz lub flaga");
                    } else if (field.isBomb) {
                        label.setText("Bomba, przegrales");
                        game.displayAllBombs(fields);
                    } else {
                        if (field.numberOfBombsAdjacent > 0) {
                            label.setText("");
                            field.button.setForeground(game.getColorOfNumberOfBombsAdjacentToField(field.numberOfBombsAdjacent));
                            field.isDiscovered = true;
                            field.button.setText(Integer.toString(field.getNumberOfBombsAdjacent()));
                        } else {
                            game.floodFill(i, j, fields);
                            label.setText("");
                        }
                        if (game.countFieldsDiscovered(fields) - Game.numberOfBombs == 0) {
                            label.setText("Wygrales!");
                            game.displayAllBombs(fields);
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field.isDiscovered) {
                        label.setText("pole juz klikniete");
                    } else if (field.isFlag) {
                        field.isFlag = false;
                        field.button.setText("");
                        label.setText("");
                    } else {
                        label.setText("");
                        field.isFlag = true;
                        field.button.setText("?");
                        field.button.setFont(new Font("Arial", Font.BOLD, 20));
                        field.button.setForeground(Color.BLACK);
                        if (game.countPointsFromFlags(fields) == Game.numberOfBombs) {
                            label.setText("Wygrales!");
                            game.displayAllBombs(fields);
                            //TODO add Game.isGameDone = true;
                        }
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Application().createAndShowBoard();
            }
        });
    }
}
