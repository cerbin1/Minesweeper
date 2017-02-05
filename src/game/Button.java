package game;

import javax.swing.JButton;
import java.awt.Color;

import static game.Application.getBombCounterColor;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;

class Button {
    Field field;
    JButton jButton;

    Button(Field field, JButton jButton) {
        this.field = field;
        this.jButton = jButton;
        field.addFloodFillListener(() -> {
            if (field.getNearBombsCounter() != 0) {
                jButton.setForeground(getBombCounterColor(field.getNearBombsCounter()));
                jButton.setText("" + field.getNearBombsCounter());
            }
            jButton.setBackground(Color.darkGray);
        });
    }

    public Field getField() {
        return field;
    }

    public JButton getjButton() {
        return jButton;
    }

    void displayBomb() {
        if (field.isBomb()) {
            jButton.setBackground(field.isFlag() ? GREEN : RED);
            jButton.setForeground(getBombCounterColor(field.getNearBombsCounter()));
        }
    }
}
