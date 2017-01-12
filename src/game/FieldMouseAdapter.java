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
        this.game = application.getGame();
        this.x = x;
        this.y = y;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (game.isGameDone()) {
            application.setStatusText("Rozpocznij nowa gre");
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
        if (game.isFirstClick()) {
            if (field.isBomb()) {
                game.plantSingleBomb();
                field.setBomb(false);
                game.setFirstClick(false);
            }
        }
        if (field.isDiscovered() || field.isFlag()) {
            application.setStatusText("pole klikniete juz lub flaga");
            return;
        }
        if (field.isBomb()) {
            application.setStatusText("Bomba, przegrales");
            application.displayAllBombs();
        } else {
            application.clearStatusText();
            if (field.getNearBombsCounter() > 0) {
                field.setDiscovered(true);
                button.setForeground(application.getBombCounterColor(field.getNearBombsCounter()));
                button.setText(Integer.toString(field.getNearBombsCounter()));
            } else {
                game.floodFill(x, y);
            }
            if (game.countDiscoveredFields() - game.numberOfBombs == 0) {
                application.setStatusText("Wygrales!");
            }
        }
    }

    private void rightButtonClick(Field field, JButton button) {
        game.setFirstClick(false);
        application.clearStatusText();
        if (field.isDiscovered()) {
            application.setStatusText("pole juz klikniete");
            return;
        }
        if (field.isFlag()) {
            field.setFlag(false);
            button.setText("");
        } else {
            field.setFlag(true);
            button.setText("?");
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setForeground(BLACK);
            if (game.countFlagPoints() == game.numberOfBombs) {
                application.setStatusText("Wygrales!");
            }
        }
    }
}