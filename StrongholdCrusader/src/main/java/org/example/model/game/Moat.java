package org.example.model.game;

import org.example.model.game.envirnmont.Coordinate;

public class Moat extends Droppable {
    private boolean isFilled;

    public Moat(Coordinate position, Government government) {
        super(position, government);
        this.isFilled = false;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
}
