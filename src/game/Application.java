package game;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Application extends JPanel {

    static boolean[][] createBombsArray() {
        boolean[][] array = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array[i][j] = false;
            }
        }
        return array;
    }

    static void fillBooleanBoardWithBombs(boolean[][] array, int numberOfBombs) {
        for (int i = 0; i < numberOfBombs; i++) {
            while(true) {
                int x = (int)Math.round(Math.random() * 10);
                int y = (int)Math.round(Math.random() * 10);
                if(!array[x][y]) {
                    array[x][y] = true;
                    break;
                }
            }
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 600);
        frame.setVisible(true);

        JPanel panel = new JPanel();

        boolean[][] bombs = createBombsArray();
        fillBooleanBoardWithBombs(bombs, 10);


        Field[][] fields = new Field[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fields[i][j] = new Field(50, 50);
                Field field = fields[i][j];
                if (bombs[i][j]) {
                    field.isBomb = true;
                    System.out.println(Integer.toString(i) + Integer.toString(j));
                }

                field.button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        System.out.println(e.getButton());
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (Field.isGameDone) {
                                System.out.println("Rozpocznij nowa gre");
                            } else {
                                if (field.isBomb) {
                                    System.out.println("Bomba, przegrales");
                                    for (int k = 0; k < 10; k++) {
                                        for (int l = 0; l < 10; l++) {
                                            if (fields[k][l].isBomb) {
                                                fields[k][l].button.setText("x");
                                                fields[k][l].button.setForeground(Color.RED);

                                            }
                                        }
                                    }
                                } else {
                                    field.button.setText("x");
                                    System.out.println("Left mouse clicked");
                                }
                            }
                        }
                        if (Field.isGameDone) {
                            System.out.println("Rozpocznij nowa gre");
                        } else {
                            if (e.getButton() == MouseEvent.BUTTON3) {
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
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
