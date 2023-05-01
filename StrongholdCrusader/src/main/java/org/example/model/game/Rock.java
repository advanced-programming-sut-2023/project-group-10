package org.example.model.game;

public class Rock implements Droppable {
    private final MapDirections direction;

    public Rock(MapDirections direction) {
        this.direction = direction;
    }

    public MapDirections getDirection() {
        return direction;
    }
}
