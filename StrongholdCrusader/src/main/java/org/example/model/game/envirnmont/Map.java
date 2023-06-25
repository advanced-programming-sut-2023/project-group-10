package org.example.model.game.envirnmont;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class Map {
    int size;
    private final Block[][] blocks;
    private ExtendedBlock[][] blocksGraphics;
    private final HashMap<Polygon, Coordinate> polygonCoordinateMap = new HashMap<>();
    Coordinate topLeftBlockCoordinate;

    public Map(int size) {
        this.size = size;
        blocks = new Block[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                blocks[i][j] = new Block(BlockTexture.EARTH);
    }

    public int getSize() {
        return size;
    }


    public ExtendedBlock[][] getBlocksGraphics() {
        return blocksGraphics;
    }

    public void setBlocksGraphics(ExtendedBlock[][] blocksGraphics) {
        this.blocksGraphics = blocksGraphics;
    }

    public HashMap<Polygon, Coordinate> getPolygonCoordinateMap() {
        return polygonCoordinateMap;
    }

    public ExtendedBlock getExtendedBlockByRowAndColumn(Coordinate position) {
        if(!isIndexInBounds(position)) return null;
        return blocksGraphics[position.row][position.column];
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

    public ArrayList<Coordinate> findPath(Coordinate startCoordinate, Coordinate endCoordinate) {
        return findPath(new Node(startCoordinate), new Node(endCoordinate));
    }

    public ArrayList<Coordinate> findPath(Node start, Node end) {
        LinkedList<Node> queue = new LinkedList<>();
        boolean[][] visited = new boolean[size][size];
        queue.add(start);
        visited[start.coordinate.row][start.coordinate.column] = true;
        Node currentNode;
        while (!queue.isEmpty()) {
            currentNode = queue.pollFirst();
            for (Node neighbor : currentNode.getNeighbors(this, end.coordinate)) {
                if (visited[neighbor.coordinate.row][neighbor.coordinate.column]) continue;
                visited[neighbor.coordinate.row][neighbor.coordinate.column] = true;
                queue.add(neighbor);
                neighbor.previousNode = currentNode;
                if (neighbor.coordinate.equals(end.coordinate)) {
                    end = neighbor;
                    queue.clear();
                    break;
                }
            }
        }
        return traceRoute(end);
    }

    private ArrayList<Coordinate> traceRoute(Node end) {
        if (end.previousNode == null) return null;
        ArrayList<Coordinate> path = new ArrayList<>();
        Node node = end;
        while (node.previousNode != null) {
            path.add(node.coordinate);
            node = node.previousNode;
        }
        Collections.reverse(path);
        if (path.size() == 0) return null;
        return path;
    }
}
