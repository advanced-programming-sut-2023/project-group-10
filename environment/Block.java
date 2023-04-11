package model.environment;

import java.util.ArrayList;

public class Block {
    private BlockTexture texture;
    private Building building;
    private ArrayList<Person> people;

    Block(BlockTexture texture, int x, int y) {
        this.texture = texture;
        building = null;
        this.people = new ArrayList<>();
    }

    public BlockTexture getTexture() {
        return texture;
    }

    public Building getBuilding() {
        return building;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setTexture(BlockTexture texture) {
        this.texture = texture;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
