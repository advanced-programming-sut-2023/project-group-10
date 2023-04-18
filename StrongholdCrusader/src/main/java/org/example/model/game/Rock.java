package org.example.model.game;

public class Rock implements Droppable {
    private mapDirections direction;

    public Rock(mapDirections direction) {
    }

    public mapDirections getDirection() {
        return direction;
    }
}
