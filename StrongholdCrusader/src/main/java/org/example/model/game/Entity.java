package org.example.model.game;

import org.example.model.game.envirnmont.Coordinate;

public abstract class Entity implements Droppable {
    private Coordinate position;
    private final Government government;

    public Entity(Coordinate position, Government government) {
        this.position = position;
        this.government = government;
    }

    public Coordinate getPosition() {
        return position;
    }

    public Government getGovernment() {
        return government;
    }

    public void setPosition(Coordinate newCoordinate) {
        position = newCoordinate;
    }
}
