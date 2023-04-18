package org.example.model.game;

public class Moat implements Droppable {
    private boolean isFilled;

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
}
