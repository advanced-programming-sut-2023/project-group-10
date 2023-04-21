package org.example.model.game;

import org.example.model.game.envirnmont.Coordinate;

public class Tree extends Droppable {
    private final TreeType type;

    public Tree(Coordinate position, Government government, TreeType type) {
        super(position, government);
        this.type = type;
    }

    public TreeType getType() {
        return type;
    }
}
