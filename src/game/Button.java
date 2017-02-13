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
                field.setDiscovered(true);
                jButton.setEnabled(false);
                jButton.setBackground(getBombCounterColor(field.getNearBombsCounter()));
                jButton.setText(Integer.toString(field.getNearBombsCounter()));
            } else {
                jButton.setBackground(Color.darkGray);
            }
        };
    }

    JButton getJButton() {
        return jButton;
    }

    void displayBomb() {
        if (field.isBomb()) {
            jButton.setBackground(field.isFlag() ? GREEN : RED);
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
        jButton.setForeground(new Color(239, 144, 35));
    }

    boolean isBomb() {
        return field.isBomb();
    }

    void setBomb(boolean bomb) {
        field.setBomb(bomb);
    }

    void discover() {
        Font font = new Font("Arial", 0, 30);
        jButton.setMargin(new Insets(0, 0, 0, 0));
        field.setDiscovered(true);
        getJButton().setEnabled(false);
        jButton.setFont(font);
        jButton.setBackground(getBombCounterColor(field.getNearBombsCounter()));
        jButton.setText(Integer.toString(field.getNearBombsCounter()));

    }

    boolean hasNearBombs() {
        return field.getNearBombsCounter() > 0;
    }
}
