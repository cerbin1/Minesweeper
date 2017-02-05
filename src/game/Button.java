package game;

import javax.swing.*;
import java.awt.*;

import static game.Application.getBombCounterColor;
import static java.awt.Color.*;

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

    boolean isDiscovered() {
        return field.isDiscovered();
    }

    void toggleFlag() {
        if (isFlag()) {
            setUnflagged();
        } else {
            setFlagged();
        }
    }

    boolean isFlag() {
        return field.isFlag();
    }

    void setUnflagged() {
        field.setFlag(false);
        jButton.setText("");
    }

    void setFlagged() {
        field.setFlag(true);
        jButton.setText("?");
        jButton.setFont(new Font("Arial", Font.BOLD, 20));
        jButton.setForeground(BLACK);
    }

    boolean isBomb() {
        return field.isBomb();
    }

    void setBomb(boolean bomb) {
        field.setBomb(bomb);
    }

    void discover() {
        field.setDiscovered(true);
        jButton.setForeground(getBombCounterColor(field.getNearBombsCounter()));
        jButton.setText(Integer.toString(field.getNearBombsCounter()));
    }
}
