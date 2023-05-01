package org.example.model.game;

public class Tree implements Droppable {
    private final TreeType type;

    public Tree(TreeType type) {
        this.type = type;
    }

    public TreeType getType() {
        return type;
    }
}
