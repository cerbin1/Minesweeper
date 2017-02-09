package game;

import javax.swing.*;
import java.awt.*;

import static game.Application.getBombCounterColor;
import static java.awt.Color.*;

class Button {
    private final Field field;
    private final JButton jButton;

    Button(Field field, JButton jButton) {
        this.field = field;
        this.jButton = jButton;
        field.addFloodFillListener(createFloodFillListener());
    }

    private FloodFillListener createFloodFillListener() {
        return () -> {
            if (field.getNearBombsCounter() != 0) {
                jButton.setForeground(getBombCounterColor(field.getNearBombsCounter()));
                jButton.setText("" + field.getNearBombsCounter());
            }
            jButton.setBackground(Color.darkGray);
        };
    }

    JButton getJButton() {
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

    private void setUnflagged() {
        field.setFlag(false);
        jButton.setText("");
    }

    private void setFlagged() {
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

    boolean hasNearBombs() {
        return field.getNearBombsCounter() > 0;
    }
}
