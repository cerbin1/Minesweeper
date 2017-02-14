package game;

import game.View.ImageCreator;

import javax.swing.*;
import java.awt.*;

import static game.Application.getBombCounterColor;

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
            if (field.getNearBombsCount() != 0) {
                field.setDiscovered(true);
                jButton.setEnabled(false);
                jButton.setBackground(getBombCounterColor(field.getNearBombsCount()));
                jButton.setText(Integer.toString(field.getNearBombsCount()));
            } else {
                jButton.setBackground(Color.darkGray);
            }
        };
    }

    JButton getJButton() {
        return jButton;
    }

    void displayIfBomb() {
        if (field.isBomb() && field.isFlag()) {
            getJButton().setEnabled(false);
            setJButtonImageIcon(new ImageCreator().getFlaggedBomb());
            getJButton().setBackground(Color.GREEN);
        } else if (!field.isBomb() && field.isFlag()) {
            setJButtonImageIcon(new ImageCreator().getMissedFlag());
            getJButton().setBackground(Color.RED);
        } else if (field.isBomb()) {
            setJButtonImageIcon(new ImageCreator().getBomb());
            getJButton().setBackground(Color.RED);
        }
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

    void discover() {
        field.setDiscovered(true);
        getJButton().setEnabled(false);
        jButton.setBackground(getBombCounterColor(field.getNearBombsCount()));
        jButton.setText(Integer.toString(field.getNearBombsCount()));
    }

    boolean hasNearBombs() {
        return field.getNearBombsCount() > 0;
    }
}
