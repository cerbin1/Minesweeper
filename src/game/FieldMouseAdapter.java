package game;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FieldMouseAdapter extends MouseAdapter {
    private final Application application;
    private final Game game;
    private final int x;
    private final int y;

    FieldMouseAdapter(Application application, int x, int y) {
        this.application = application;
        this.game = application.getGame();
        this.x = x;
        this.y = y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (game.isGameDone) {
            application.setMessageText("Rozpocznij nowa gre");
            return;
        }

        Field field = game.getField(x, y);
        JButton button = application.getButton(x, y);

        if (e.getButton() == MouseEvent.BUTTON1) {
            leftMouseClick(field, button);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightMouseClick(field, button);
        }


    }

    void leftMouseClick(Field field, JButton button) {
        if (field.isDiscovered || field.isFlag) {
            application.setMessageText("Pole klikniete lub flaga");
            return;
        }

        if (field.isBomb) {
            application.setMessageText("Bomba, przegrywasz!");
            application.displayAllBombs();
            game.isGameDone = true;
        } else {
            application.clearMessageBox();
            if (field.getNumberOfBombsAdjacent() > 0) {
                field.isDiscovered = true;
                button.setForeground(application.NEAR_BOMBS_COUNTER_COLORS[field.getNumberOfBombsAdjacent() - 1]);
                button.setText(Integer.toString(field.getNumberOfBombsAdjacent()));
            } else {
                game.floodFill(x, y);
            }
            if (game.countFieldsDiscovered() - game.getNumberOfBombs() == 0) {
                application.setMessageText("Wygrales!");
            }
        }

    }

    void rightMouseClick(Field field, JButton button) {
        application.clearMessageBox();
        if (field.isDiscovered) {
            application.setMessageText("Pole juz klikniete");
            return;
        }
        if (field.isFlag) {
            field.isFlag = false;
            button.setText("");
        } else {
            field.isFlag = true;
            button.setText("?");
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setForeground(Color.BLACK);
        }

        if (game.countPointsFromFlags() == game.getNumberOfBombs()) {
            application.setMessageText("Wygrales");
        }
    }

}