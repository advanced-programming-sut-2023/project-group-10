package org.example.model.game;

import org.example.model.game.envirnmont.Coordinate;

public class PitchDitch extends Droppable {
    private boolean isOnFire;

    public PitchDitch(Coordinate position, Government government) {
        super(position, government);
        isOnFire = false;
    }

    public boolean isOnFire() {
        return isOnFire;
    }

    public void setOnFire() {
        isOnFire = true;
    }
}