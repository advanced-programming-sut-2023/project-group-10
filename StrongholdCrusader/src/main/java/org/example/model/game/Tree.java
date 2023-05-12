package org.example.model.game;

public class Tree implements Droppable {
    private final TreeType type;
    private int woodStorage;

    public Tree(TreeType type) {
        this.type = type;
        this.woodStorage = TreeType.getMaxWoodStorage(this.type);
    }

    public TreeType getType() {
        return type;
    }

    public int getWoodStorage() {
        return woodStorage;
    }

    public void reduceWoodStorage(int amount) {
        this.woodStorage -= TreeType.getMaxWoodStorage(this.type) * amount * NumericalEnums.WOOD_REDUCTION_RATE.getValue();
    }
}
