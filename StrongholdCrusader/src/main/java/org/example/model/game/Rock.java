package org.example.model.game;

public class Rock implements Droppable {
    private final RockType rockType;

    public Rock(RockType rockType) {
        this.rockType = rockType;
    }

    public RockType getRockType() {
        return rockType;
    }
}