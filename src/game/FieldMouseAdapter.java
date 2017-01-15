package game;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;

class FieldMouseAdapter extends MouseAdapter {
    private final Application application;
    private final Game game;
    private final int x;
    private final int y;

    FieldMouseAdapter(Application application, int x, int y) {
        this.application = application;
        game = application.getGame();
        this.x = x;
        this.y = y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (game.isGameDone()) {
            application.setMessageBoxText("Start new game");
            return;
        }

        Field field = game.getField(x, y);
        JButton button = application.getButton(x, y);

        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonClick(field, button);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightButtonClick(field, button);
        }
    }

    private void leftButtonClick(Field field, JButton button) {
        if (application.isFirstClick()) {
            if (field.isBomb()) {
                System.out.println("losuje bombe nowa");
                game.plantSingleBomb();
                field.setBomb(false);
            }
            application.setFirstClick(false);
            game.fillBombsCounters();
        }
        if (field.isDiscovered() || field.isFlag()) {
            application.setMessageBoxText("Field is already clicked or flagged");
            return;
        }
        if (field.isBomb()) {
            application.setMessageBoxText("Boom, you lose!");
            application.displayAllBombs();
        } else {
            application.clearMessageBoxText();
            if (field.getNearBombsCounter() > 0) {
                field.setDiscovered(true);
                button.setForeground(application.getBombCounterColor(field.getNearBombsCounter()));
                button.setText(Integer.toString(field.getNearBombsCounter()));
            } else {
                game.floodFill(x, y);
            }
            if (game.countDiscoveredFields() - game.numberOfBombs == 0) {
                application.setMessageBoxText("You win!");
            }
        }
    }

    private void rightButtonClick(Field field, JButton button) {
        if (application.isFirstClick()) {
            application.setFirstClick(false);
            game.fillBombsCounters();
        }
        application.clearMessageBoxText();
        if (field.isDiscovered()) {
            application.setMessageBoxText("pole juz klikniete");
            return;
        }
        if (field.isFlag()) {
            application.updateAmountOfBombsToFlag(1);
            field.setFlag(false);
            button.setText("");
        } else {
            application.updateAmountOfBombsToFlag(-1);
            field.setFlag(true);
            button.setText("?");
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setForeground(BLACK);
            if (game.countFlagPoints() == game.numberOfBombs - game.countDiscoveredFields()) {
                application.setMessageBoxText("Wygrales!");
            }
        }
    }
}