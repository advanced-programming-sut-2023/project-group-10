package org.example.model.game.envirnmont;

import org.example.model.game.Droppable;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.ItemProducingBuilding;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;

import java.util.ArrayList;

public class Block {
    private BlockTexture texture;
    private Droppable droppable;
    private final ArrayList<Unit> units;

    Block(BlockTexture texture) {
        this.texture = texture;
        droppable = null;
        units = new ArrayList<>();
    }

    public BlockTexture getTexture() {
        return texture;
    }

    public Droppable getDroppable() {
        return droppable;
    }

    public Building getBuilding() {
        if (droppable instanceof Building) return ((Building) droppable);
        return null;
    }

    public ArrayList<Unit> getAllUnits() {
        return units;
    }

    public ArrayList<MilitaryUnit> getMilitaryUnits() {
        ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();
        for (Unit unit : units)
            if (unit instanceof MilitaryUnit) militaryUnits.add((MilitaryUnit) unit);
        return militaryUnits;
    }

    public void setTexture(BlockTexture texture) {
        this.texture = texture;
    }

    public void clearBlock() {
        droppable = null;
        units.clear();
    }

    public boolean setDroppable(Droppable droppable) {
        if (this.droppable != null) return false;
        if (!texture.isBuildable()) return false;
        if (!texture.isFertile() && droppable instanceof ItemProducingBuilding && ((ItemProducingBuildingType) ((ItemProducingBuilding) droppable).getBuildingType()).isFarm())
            return false;
        this.droppable = droppable;
        return true;
    }

    public boolean addUnit(Unit unit) {
        if (!texture.isWalkable()) return false;
        units.add(unit);
        return true;
    }

    public boolean removeUnit(Unit unit) {
        return units.remove(unit);
    }
}