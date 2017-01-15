package game;

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
        Button button = application.getButton(x, y);

        if (e.getButton() == MouseEvent.BUTTON1) {
            leftButtonClick(button);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightButtonClick(button);
        }
    }

    private void leftButtonClick(Button button) {
        if (application.isFirstClick()) {
            if (button.field.isBomb()) {
                System.out.println("losuje bombe nowa");
                game.plantSingleBomb();
                button.field.setBomb(false);
            }
            application.setFirstClick(false);
            game.fillBombsCounters();
        }
        if (button.field.isDiscovered() || button.field.isFlag()) {
            application.setMessageBoxText("Field is already clicked or flagged");
            return;
        }
        if (button.field.isBomb()) {
            application.setMessageBoxText("Boom, you lose!");
            application.displayAllBombs();
        } else {
            application.clearMessageBoxText();
            if (button.field.getNearBombsCounter() > 0) {
                button.field.setDiscovered(true);
                button.jButton.setForeground(application.getBombCounterColor(button.field.getNearBombsCounter()));
                button.jButton.setText(Integer.toString(button.field.getNearBombsCounter()));
            } else {
                game.floodFill(x, y);
            }
            if (game.countDiscoveredFields() - game.numberOfBombs == 0) {
                application.setMessageBoxText("You win!");
            }
        }
    }

    private void rightButtonClick(Button button) {
        if (application.isFirstClick()) {
            application.setFirstClick(false);
            game.fillBombsCounters();
        }
        application.clearMessageBoxText();
        if (button.field.isDiscovered()) {
            application.setMessageBoxText("pole juz klikniete");
            return;
        }
        if (button.field.isFlag()) {
            application.updateAmountOfBombsToFlag(1);
            button.field.setFlag(false);
            button.jButton.setText("");
        } else {
            application.updateAmountOfBombsToFlag(-1);
            button.field.setFlag(true);
            button.jButton.setText("?");
            button.jButton.setFont(new Font("Arial", Font.BOLD, 20));
            button.jButton.setForeground(BLACK);
            if (game.countFlagPoints() == game.numberOfBombs - game.countDiscoveredFields()) {
                application.setMessageBoxText("Wygrales!");
            }
        }
    }
}