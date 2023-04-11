package model.environment;

public class Coordinate {
    int row;
    int column;

    Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    boolean isValid(int mapSize) {
        return row >= 0 && row < mapSize && column >= 0 && column < mapSize;
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
