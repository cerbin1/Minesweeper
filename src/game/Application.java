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

        JPanel panel = new JPanel();

        Field[][] fields = new Field[10][10];
        Game game = new Game();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fields[i][j] = new Field(50, 50);
                Field field = fields[i][j];

                field.button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) { // można by wydzielić metodę która zwraca mouseAdaptera
                        System.out.println(e.getButton());
                        if (e.getButton() == MouseEvent.BUTTON1) { // czemu tu jest najpierw button, potem isGameDone
                            if (Field.isGameDone) {
                                System.out.println("Rozpocznij nowa gre");
                            } else {
                                if (field.isBomb) {
                                    System.out.println("Bomba, przegrales");
                                    game.displayAllBombs(fields);
                                } else {
                                    field.button.setText(Integer.toString(field.getNumberOfBombsAdjacent()));
                                    System.out.println(field.getNumberOfBombsAdjacent());
                                    System.out.println("Left mouse clicked");
                                }
                            }
                        }
                        if (Field.isGameDone) {
                            System.out.println("Rozpocznij nowa gre");
                        } else {
                            if (e.getButton() == MouseEvent.BUTTON3) { // a tu najpierw isGameDone a potem button? ;|
                                field.button.setText("?");
                                System.out.println("Right mouse clicked");
                            }
                        }
                    }
                });

                panel.add(field.button);
            }
        }
        frame.add(panel);
        game.setBombs(fields, 10);
        game.displayAllBombs(fields);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
