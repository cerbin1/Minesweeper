package game;

import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Field {
    static boolean isGameDone = false;
    boolean isBomb;
    JButton button;

    public Field(int buttonWidth, int buttonHeight) {
        button = new JButton();
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        isBomb = false;
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getButton());
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (Field.isGameDone) {
                        System.out.println("Rozpocznij nowa gre");
                    } else {
                        if (isBomb) {
                            System.out.println("Bomba, przegrales");
                        } else {
                            button.setText("x");
                            System.out.println("Left mouse clicked");
                        }
                    }
                }
                if (Field.isGameDone) {
                    System.out.println("Rozpocznij nowa gre");
                } else {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        button.setText("?");
                        System.out.println("Right mouse clicked");
                    }
                }
            }
        });
    }
}



