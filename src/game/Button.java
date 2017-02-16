package game;

import game.View.ImageCreator;

import javax.swing.*;
import java.awt.Color;
import java.awt.*;

import static game.Application.getBombCounterColor;
import static java.awt.Color.*;

class Button {
    private final Field field;
    private final JButton jButton;

    Button(Field field, JButton jButton) {
        this.field = field;
        this.jButton = jButton;
        field.setFloodFillListener(createFloodFillListener());
    }

    private FloodFillListener createFloodFillListener() {
        return () -> {
            if (0 != field.getNearBombsCount()) {
                discoverButtonWithNearBombs();
            } else {
                discoverButtonWithoutNearBombs();
            }
        };
    }

    private void discoverButtonWithoutNearBombs() {
        jButton.setBackground(darkGray);
        jButton.setEnabled(false);
    }

    JButton getJButton() {
        return jButton;
    }

    void displayBomb() {
        if (isBombHit()) {
            setBombHitLook();
        } else if (isBombMissed()) {
            setBombMissedLook();
        } else if (field.isBomb()) {
            setBombLook();
        }
        jButton.setEnabled(false);
    }

    private boolean isBombHit() {
        return field.isBomb() && field.isFlag();
    }

    private void setBombHitLook() {
        setJButtonImageIcon(new ImageCreator().getFlaggedBomb());
        getJButton().setBackground(GREEN);
    }

    private boolean isBombMissed() {
        return !field.isBomb() && field.isFlag();
    }

    private void setBombMissedLook() {
        setJButtonImageIcon(new ImageCreator().getMissedFlag());
        getJButton().setBackground(RED);
    }

    private void setBombLook() {
        setJButtonImageIcon(new ImageCreator().getBomb());
    }

    private void setJButtonImageIcon(Image image) {
        jButton.setIcon(new ImageIcon(image));
        jButton.setDisabledIcon(new ImageIcon(image));
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
        setJButtonImageIcon(new ImageCreator().getEmptyFlag());
        jButton.setBackground(null);
    }

    private void setFlagged() {
        field.setFlag(true);
        setJButtonImageIcon(new ImageCreator().getFlag());
        jButton.setBackground(new Color(240, 208, 132));
    }

    boolean isBomb() {
        return field.isBomb();
    }

    void setBomb(boolean bomb) {
        field.setBomb(bomb);
    }

    void discoverButtonWithNearBombs() {
        field.setDiscovered(true);
        getJButton().setEnabled(false);
        jButton.setBackground(getBombCounterColor(field.getNearBombsCount()));
        jButton.setText(Integer.toString(field.getNearBombsCount()));
    }

    boolean hasNearBombs() {
        return field.getNearBombsCount() > 0;
    }
}
