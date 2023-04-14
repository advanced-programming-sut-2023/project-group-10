package model.environment;

import model.buildings.Building;
import model.buildings.FarmBuilding;
import model.droppableitems.Droppable;
import model.government.units.Unit;

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
        if (this.droppable != null || !people.isEmpty()) return false;
        if (droppable instanceof FarmBuilding && !texture.isFertile()) return false;
        if (droppable instanceof Building && !texture.isBuildable()) return false;
        this.droppable = droppable;
        return true;
    }

    public void addPerson(Unit person) {
        //TODO: check signature after designing Person class
        people.add(person);
    }
}