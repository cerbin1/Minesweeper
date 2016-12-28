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

        Field[][] fields = new Field[10][10]; // przykładowy konflikt żebyś se mógł rozwiązać :D

        JPanel panel = new JPanel();

        Game game = new Game();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fields[i][j] = new Field(50, 50);
                Field field = fields[i][j];

                field.button.addMouseListener(getMouseAdapter(fields, game, field));

                panel.add(field.button);
            }
        }
        frame.add(panel);
        game.setBombs(fields, 10);
        game.displayAllBombs(fields);
    }

    private static MouseAdapter getMouseAdapter(final Field[][] fields, final Game game, final Field field) {
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
                        field.isDiscovered = true;
                        field.button.setText(Integer.toString(field.getNumberOfBombsAdjacent()));
                        System.out.println(field.getNumberOfBombsAdjacent());
                        System.out.println("Left mouse clicked");
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    field.isFlag = true;
                    field.button.setText("?");
                    System.out.println("Right mouse clicked");
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
