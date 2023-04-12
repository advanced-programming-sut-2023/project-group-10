package model.droppableitems;

public class Rock implements Droppable {
    private final char direction;

    public Rock(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }
}
