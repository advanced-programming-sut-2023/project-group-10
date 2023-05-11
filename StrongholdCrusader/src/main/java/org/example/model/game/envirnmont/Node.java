package org.example.model.game.envirnmont;

import java.util.ArrayList;

public class Node {
    public Coordinate coordinate;
    public Node previousNode;
    public boolean visited;

    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
        previousNode=null;
        visited=false;
    }

    public ArrayList<Node> getNeighbors(Map map) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int[] verticalChange = {-1, 1, 0, 0};
        int[] horizontalChange = {0, 0, -1, 1};
        Coordinate potentialNeighborCoordinate;
        for (int i = 0; i < 4; i++) {
            potentialNeighborCoordinate = new Coordinate(coordinate.row + verticalChange[i], coordinate.column + horizontalChange[i]);
            if (!map.isIndexInBounds(potentialNeighborCoordinate)) continue;
            if (map.getBlockByRowAndColumn(potentialNeighborCoordinate).canUnitsGoHere(true))
                neighbors.add(new Node(potentialNeighborCoordinate));
        }
        return neighbors;
    }
}
