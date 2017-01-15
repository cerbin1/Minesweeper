package game;

import javax.swing.JButton;

class Button {
    private Field field;
    private JButton jButton;

    Button(Field field, JButton jButton) {
        this.field = field;
        this.jButton = jButton;
    }

    Field getField() {
        return field;
    }

    JButton getjButton() {
        return jButton;
    }
}
