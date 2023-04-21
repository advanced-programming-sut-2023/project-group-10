package org.example.model.game;

import org.example.model.game.envirnmont.Coordinate;

public class Rock extends Droppable {
    private final mapDirections direction;

    public Rock(Coordinate position, Government government, mapDirections direction) {
        super(position, government);
        this.direction = direction;
    }

    public mapDirections getDirection() {
        return direction;
    }
}
