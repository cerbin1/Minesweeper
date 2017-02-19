package game;

import game.view.ImageRepository;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.*;

class Button {
    private final static Color[] BOMBS_COUNTER_COLORS = {
            BLUE, GREEN, RED, MAGENTA, ORANGE, LIGHT_GRAY, YELLOW, PINK};

    private final Field field;
    private final JButton jButton;

    Button(Field field, JButton jButton) {
        this.field = field;
        this.jButton = jButton;
        field.setFloodFillListener(createFloodFillListener());
    }

    private FloodFillListener createFloodFillListener() {
        return () -> {
            if (field.getNearBombsCount() == 0) {
                discoverButtonWithoutNearBombs();
            } else {
                discoverButtonWithNearBombs();
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
        setJButtonImageIcon(new ImageRepository().getFlaggedBomb());
        jButton.setBackground(GREEN);
    }

    private boolean isBombMissed() {
        return !field.isBomb() && field.isFlag();
    }

    private void setBombMissedLook() {
        setJButtonImageIcon(new ImageRepository().getMissedFlag());
        jButton.setBackground(RED);
    }

    private void setBombLook() {
        setJButtonImageIcon(new ImageRepository().getBomb());
    }

    private void setJButtonImageIcon(Image image) {
        ImageIcon icon = new ImageIcon(image);
        jButton.setIcon(icon);
        jButton.setDisabledIcon(icon);
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
        setJButtonImageIcon(new ImageRepository().getEmptyFlag());
        jButton.setBackground(null);
    }

    private void setFlagged() {
        field.setFlag(true);
        setJButtonImageIcon(new ImageRepository().getFlag());
        jButton.setBackground(new Color(240, 208, 132));
    }

    boolean isBomb() {
        return field.isBomb();
    }

    void unmarkAsBomb() {
        field.setBomb(false);
    }

    void discoverButtonWithNearBombs() {
        field.discover();
        jButton.setEnabled(false);
        int nearBombsCount = field.getNearBombsCount();
        jButton.setBackground(getBombCounterColor(nearBombsCount));
        jButton.setText(Integer.toString(nearBombsCount));
        jButton.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    private Color getBombCounterColor(int bombsCount) {
        return BOMBS_COUNTER_COLORS[bombsCount - 1];
    }

    boolean hasBombsNear() {
        return field.getNearBombsCount() > 0;
    }
}
