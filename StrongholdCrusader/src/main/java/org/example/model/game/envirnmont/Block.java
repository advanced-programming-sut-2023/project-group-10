package org.example.model.game.envirnmont;

import org.example.model.game.Droppable;
import org.example.model.game.buildings.Building;
import org.example.model.game.units.Unit;

import java.util.ArrayList;

public class Block {
    private BlockTexture texture;
    private Droppable droppable;
    private final ArrayList<Unit> people;

    Block(BlockTexture texture) {
        this.texture = texture;
        droppable = null;
        people = new ArrayList<>();
    }

    public BlockTexture getTexture() {
        return texture;
    }

    public Building getBuilding() {
        if (droppable instanceof Building) return ((Building) droppable);
        return null;
    }

    public Unit getPerson() {
        if ((droppable instanceof Unit)) return ((Unit) droppable);
        return null;
    }

    public void setTexture(BlockTexture texture) {
        this.texture = texture;
    }

    public void clearBlock() {
        droppable = null;
    }

    public boolean setDroppable(Droppable droppable) {
        return true;
    }

    public void addPerson(Unit person) {
        //TODO: check signature after designing Person class
        people.add(person);
    }
}