package org.example.model.game.envirnmont;

import org.example.model.Stronghold;
import org.example.model.game.Droppable;
import org.example.model.game.Government;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.ItemProducingBuilding;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;

import java.util.ArrayList;

public class Block {
    private BlockTexture texture;
    private Droppable droppable;
    private final ArrayList<Unit> units;
    private boolean onFire;

    Block(BlockTexture texture) {
        this.texture = texture;
        droppable = null;
        units = new ArrayList<>();
        onFire = false;
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

    public ArrayList<Unit> getUnitsByGovernment(Government government) {
        ArrayList<Unit> result = new ArrayList<>();
        for (Unit unit : units)
            if (unit.getGovernment() == government) result.add(unit);
        return result;
    }

    public ArrayList<MilitaryUnit> getAllMilitaryUnits() {
        ArrayList<MilitaryUnit> militaryUnits = new ArrayList<>();
        for (Unit unit : units)
            if (unit instanceof MilitaryUnit) militaryUnits.add((MilitaryUnit) unit);
        return militaryUnits;
    }

    public ArrayList<MilitaryUnit> getMilitaryUnitsByGovernment(Government government) {
        ArrayList<MilitaryUnit> result = new ArrayList<>();
        for (MilitaryUnit militaryUnit : getAllMilitaryUnits())
            if (militaryUnit.getGovernment() == government) result.add(militaryUnit);
        return result;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setTexture(BlockTexture texture) {
        this.texture = texture;
    }

    public void clearBlock() {
        droppable = null;
        for (Unit unit : units)
            unit.deleteUnitFromGovernmentAndMap();
        setOnFire(false);
    }

    //TODO: change if's to handle rock, pond, tree
    public boolean setDroppable(Droppable droppable) {
        if (this.droppable != null) return false;
        if (!texture.isBuildable()) return false;
        if (onFire) return false;
        if (!texture.isFertile() && droppable instanceof ItemProducingBuilding && ((ItemProducingBuildingType) ((Building) droppable).getBuildingType()).isFarm())
            return false;
        this.droppable = droppable;
        return true;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public boolean addUnit(Unit unit) {
        if (!canUnitsGoHere(false)) return false;
        units.add(unit);
        return true;
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public void clearForces() {
        ArrayList<Unit> militaryUnits = new ArrayList<>();
        for (Unit unit : units)
            if (unit instanceof MilitaryUnit) militaryUnits.add(unit);
        for (Unit militaryUnit : militaryUnits)
            militaryUnit.deleteUnitFromGovernmentAndMap();
    }

    public boolean canUnitsGoHere(boolean canGoInEnemyPit) {
        if (!texture.isWalkable()) return false;
        if (droppable == null) return true;
        //moat, rock, tree aren't passable
        if (!(droppable instanceof Building)) return false;
        if (getBuilding().isClimbable()) return true;
        return canGoInEnemyPit && getBuilding().getBuildingType().getName() == BuildingTypeName.KILLING_PIT && getBuilding().getGovernment() != Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
    }

    public boolean canDigHere() {
        return droppable == null && !onFire && texture.isBuildable();
    }

    public ArrayList<Unit> getAllAttackableEnemyUnits(Government government) {
        ArrayList<Unit> result = new ArrayList<>();
        for (Unit unit : units)
            if (unit.getGovernment() != government && unit.isAttackable()) result.add(unit);
        return result;
    }

    public ArrayList<MilitaryUnit> getSelectableMilitaryUnitsByGovernment(Government government) {
        ArrayList<MilitaryUnit> units = getMilitaryUnitsByGovernment(government);
        for (int i = 0; i < units.size(); ) {
            if (!units.get(i).isSelectable()) units.remove(i);
            else i++;
        }
        return units;
    }
}