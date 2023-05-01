package org.example.model.game;

import org.example.model.game.envirnmont.Coordinate;

public class Rock implements Droppable {
    private final mapDirections direction;

    public Rock(mapDirections direction) {
        this.direction = direction;
    }

    public mapDirections getDirection() {
        return direction;
    }
}
