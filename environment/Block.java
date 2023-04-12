package model.environment;

import model.droppableitems.Droppable;
import model.people.Person;

import java.util.ArrayList;

public class Block {
    private BlockTexture texture;
    private Droppable droppable;
    private final ArrayList<Person> people;

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

    public Person getUnit() {
        if ((droppable instanceof Person)) return ((Person) droppable);
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
        if (droppable instanceof Farm && !texture.isFertile()) return false;
        if (droppable instanceof Building && !texture.isBuildable()) return false;
        this.droppable = droppable;
        return true;
    }

    public void addPerson(Person person) {
        //TODO: check signature after designing Person class
        people.add(person);
    }
}