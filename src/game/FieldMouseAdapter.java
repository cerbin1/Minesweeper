package game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;

public class FieldMouseAdapter extends MouseAdapter {

    private final Application app;
    private final Game game;
    private final int x;
    private final int y;

    FieldMouseAdapter(Application app, Game game, int x, int y) {
        this.app = app;
        this.game = game;
        this.x = x;
        this.y = y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Field field = game.getField(x, y);
        JButton button = app.getButton(x, y);

        if (game.isGameDone) {
            app.setStatusText("Rozpocznij nowa gre");
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonClick(field, button);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightButtonClick(field, button);
        }
    }

    private void leftButtonClick(Field field, JButton button) {
        if (field.isDiscovered || field.isFlag) {
            app.setStatusText("pole klikniete juz lub flaga");
            return;
        }
        if (field.isBomb) {
            app.setStatusText("Bomba, przegrales");
            app.displayAllBombs();
        } else {
            app.clearStatusText();
            if (field.numberOfBombsAdjacent > 0) {
                field.isDiscovered = true;
                button.setForeground(app.getBombCounterColor(field.numberOfBombsAdjacent));
                button.setText(Integer.toString(field.getAdjacentBombsCount()));
            } else {
                game.floodFill(x, y);
            }
            if (game.countDiscoveredFields() - game.numberOfBombs == 0) {
                app.setStatusText("Wygrales!");
            }
        }
    }

    private void rightButtonClick(Field field, JButton button) {
        app.clearStatusText();
        if (field.isDiscovered) {
            app.setStatusText("pole juz klikniete");
            return;
        }
        if (field.isFlag) {
            field.isFlag = false;
            button.setText("");
        } else {
            field.isFlag = true;
            button.setText("?");
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setForeground(BLACK);
            if (game.countFlagPoints() == game.numberOfBombs) {
                app.setStatusText("Wygrales!");
            }
        }
    }
}