package org.example.model.game.envirnmont;

public class Map {
    int size;
    Block[][] blocks;
    Coordinate topLeftBlockCoordinate;

    public Map(int size) {
        this.size = size;
        blocks = new Block[size][size];
        //TODO: make templates
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                blocks[i][j] = new Block(BlockTexture.EARTH);
    }

    public int getSize() {
        return size;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public Block getBlockByRowAndColumn(int row, int column) {
        return getBlockByRowAndColumn(new Coordinate(row, column));
    }

    public Block getBlockByRowAndColumn(Coordinate position) {
        if (!isIndexInBounds(position)) return null;
        else return blocks[position.row][position.column];
    }

    public Coordinate getTopLeftBlockCoordinate() {
        return topLeftBlockCoordinate;
    }

    public void setOrigin(Coordinate coordinate, int NumberOfBlocksInARow, int NumberOfBlocksInAColumn) {
        int topLeftBlockRow = coordinate.row - NumberOfBlocksInAColumn / 2 + 1;
        topLeftBlockRow = Math.min(size - NumberOfBlocksInAColumn, Math.max(0, topLeftBlockRow));
        int topLeftBlockColumn = coordinate.column - NumberOfBlocksInARow / 2 + 1;
        topLeftBlockColumn = Math.min(size - NumberOfBlocksInARow, Math.max(0, topLeftBlockColumn));
        topLeftBlockCoordinate = new Coordinate(topLeftBlockRow, topLeftBlockColumn);
    }

    public void moveOrigin(int horizontalChange, int verticalChange) {
        topLeftBlockCoordinate.modify(horizontalChange, verticalChange, size);
    }

    public boolean setTextureSingleBlock(BlockTexture texture, int row, int column) {
        if (!isIndexInBounds(row) || !isIndexInBounds(column)) return false;
        blocks[row][column].setTexture(texture);
        return true;
    }

    public boolean setTextureRectangleOfBlocks(BlockTexture texture, int row1, int column1, int row2, int column2) {
        if (!isIndexInBounds(row1) || !isIndexInBounds(column1) || !isIndexInBounds(row2) || !isIndexInBounds(column2))
            return false;
        for (int i = row1; i <= row2; i++)
            for (int j = column1; j <= column2; j++)
                blocks[i][j].setTexture(texture);
        return true;
    }

    public boolean clearBlock(int row, int column) {
        if (!isIndexInBounds(row) || !isIndexInBounds(column)) return false;
        blocks[row][column].clearBlock();
        return true;
    }

    public boolean isIndexInBounds(int index) {
        return index >= 0 && index < size;
    }

    public boolean isIndexInBounds(Coordinate position) {
        return isIndexInBounds(position.row) && isIndexInBounds(position.column);
    }
}
