package org.example.model.game.envirnmont;

public class Coordinate {
    public int row;
    public int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    void modify(int horizontalChange, int verticalChange, int size) {
        row += verticalChange;
        row = keepCoordinateInBounds(row, size);
        column += horizontalChange;
        column = keepCoordinateInBounds(column, size);
    }

    private int keepCoordinateInBounds(int coordinate, int limit) {
        if (coordinate < 0) coordinate = 0;
        else if (coordinate > limit - 1) coordinate = limit - 1;
        return coordinate;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate)) return false;
        return row == ((Coordinate) obj).row && column == ((Coordinate) obj).column;
    }
}