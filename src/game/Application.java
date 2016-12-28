package game;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 600);
        frame.setVisible(true);

        Field[][] fields = new Field[10][10];

        JPanel panel = new JPanel();

        Game game = new Game();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fields[i][j] = new Field(50, 50);
                Field field = fields[i][j];

                field.button.addMouseListener(getMouseAdapter(fields, game, field, i, j));

                panel.add(field.button);
            }
        }
        frame.add(panel);
        game.setBombs(fields, 10);
        game.displayAllBombs(fields);
    }

    private static MouseAdapter getMouseAdapter(final Field[][] fields, final Game game, final Field field, int i, int j) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getButton());
                if (Field.isGameDone) {
                    System.out.println("Rozpocznij nowa gre");
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    if (field.isDiscovered || field.isFlag) {
                        System.out.println("pole klikniete juz lub flaga");
                    } else if (field.isBomb) {
                        System.out.println("Bomba, przegrales");
                        game.displayAllBombs(fields);
                    } else {
                        if (field.numberOfBombsAdjacent > 0) {
                            field.isDiscovered = true;
                            field.button.setText(Integer.toString(field.getNumberOfBombsAdjacent()));
                        } else {
                            game.floodFill(i, j, fields);
                        }
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field.isDiscovered) {
                        System.out.println("pole juz klikniete");
                    } else if(field.isFlag) {
                        field.isFlag = false;
                        field.button.setText("");
                    }
                    else {
                        field.isFlag = true;
                        field.button.setText("?");
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
