package game;

import javax.swing.JButton;
import java.awt.Color;

class Button {
    Field field;
    JButton jButton;

    Button(Field field, JButton jButton, Application application) {
        this.field = field;
        this.jButton = jButton;
        field.addFloodFillListener(() -> {
            if (field.getNearBombsCounter() != 0) {
                jButton.setForeground(application.getBombCounterColor(field.getNearBombsCounter()));
                jButton.setText("" + field.getNearBombsCounter());
            }
            jButton.setBackground(Color.darkGray);
        });
    }
}
