package model.environment;

public class Map {
    int size;
    Block[][] blocks;

    Coordinate originBlockCoordinate;

    Map(int size) {
        this.size = size;
        blocks = new Block[size][size];
    }

    public Coordinate getTopLeftBlockCoordinate() {
        return null;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public void moveOrigin(int horizontalChange, int verticalChange) {
        originBlockCoordinate.modify(horizontalChange, verticalChange, size);
    }

    public Block getBlockByRowAndColumn(int row, int column) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (i == row && j == column) return blocks[i][j];
        return null;
    }
}
