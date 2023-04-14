package model.droppableitems;

import model.mapDirections;

public class Rock implements Droppable {
    private mapDirections direction;

    public Rock(char direction) {
    }

    public mapDirections getDirection() {
        return direction;
    }
}
