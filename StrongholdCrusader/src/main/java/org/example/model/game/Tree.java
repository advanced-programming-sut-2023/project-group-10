package org.example.model.game;

public class Tree implements Droppable {
    private final TreeType type;
    private int woodStorage;

    public Tree(TreeType type) {
        this.type = type;
        this.woodStorage = type.getMaxWoodStorage();
    }

    public TreeType getType() {
        return type;
    }

    public int getWoodStorage() {
        return woodStorage;
    }

    public void reduceWoodStorage(int amount) {
        this.woodStorage -= amount * NumericalEnums.WOOD_REDUCTION_RATE.getValue();
    }
}
