package org.example.model.game.envirnmont;

import org.example.model.Stronghold;
import org.example.model.game.*;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.ItemProducingBuilding;
import org.example.model.game.buildings.buildingconstants.AttackingBuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;

import java.util.ArrayList;

public class Block {
    private final ArrayList<Unit> units;
    private BlockTexture texture;
    private Droppable droppable;
    private ArrayList<Fire> fires;
    private Government keepGovernment;
    private boolean isIll;
    private Government illnessOwner;

    Block(BlockTexture texture) {
        this.texture = texture;
        droppable = null;
        units = new ArrayList<>();
        fires = new ArrayList<>();
        isIll = false;
        illnessOwner = null;
    }

    public BlockTexture getTexture() {
        return texture;
    }

    public void setTexture(BlockTexture texture) {
        this.texture = texture;
    }

    public Droppable getDroppable() {
        return droppable;
    }

    public Building getBuilding() {
        if (droppable instanceof Building) return ((Building) droppable);
        return null;
    }

    public Government getKeepGovernment() {
        return keepGovernment;
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

    public boolean isKeep() {
        return keepGovernment != null;
    }

    public void setKeep(Government owner) {
        keepGovernment = owner;
    }

    public void clearBlock() {
        droppable = null;
        for (Unit unit : units)
            unit.killMe();
        fires = new ArrayList<>();
    }

    public boolean setDroppable(Droppable droppable) {
        if (droppable == null) {
            this.droppable = null;
            return true;
        }
        if (this.droppable != null) return false;
        if (!texture.isBuildable()) return false;
        if (fires.size() > 0) return false;
        if (droppable instanceof ItemProducingBuilding) {
            ItemProducingBuildingType buildingType = (ItemProducingBuildingType) ((ItemProducingBuilding) droppable).getBuildingType();
            if (buildingType.isFarm() && !texture.isFertile()) return false;
            else if (buildingType.getName() == BuildingTypeName.IRON_MINE && texture != BlockTexture.IRON) return false;
            else if (buildingType.getName() == BuildingTypeName.QUARRY && texture != BlockTexture.BOULDERS)
                return false;
            else if (buildingType.getName() == BuildingTypeName.PITCH_RIG && texture != BlockTexture.OIL) return false;
        }
        this.droppable = droppable;
        return true;
    }

    public boolean isIll() {
        return isIll;
    }

    public void setIll(boolean ill) {
        isIll = ill;
    }

    public Government getIllnessOwner() {
        return illnessOwner;
    }

    public void setIllnessOwner(Government illnessOwner) {
        this.illnessOwner = illnessOwner;
    }


    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public void clearForcesOfGovernment(Government government) {
        ArrayList<Unit> unitsToBeDeleted = new ArrayList<>();
        for (Unit unit : units)
            if (unit instanceof MilitaryUnit && unit.getGovernment() == government) unitsToBeDeleted.add(unit);
        for (Unit militaryUnit : unitsToBeDeleted)
            militaryUnit.killMe();
    }

    public boolean canUnitsGoHere(boolean canGoInEnemyPit) {
        if (!texture.isWalkable()) return false;
        if (droppable == null) return true;
        if (droppable instanceof Tree) return true;
        if (droppable instanceof Rock) return false;
        if (droppable instanceof Moat) return false;
        if (getBuilding().isClimbable()) return true;
        if (!(getBuilding().getBuildingType() instanceof AttackingBuildingType) && getBuilding().getBuildingType().getName() != BuildingTypeName.KILLING_PIT)
            return true;
        return canGoInEnemyPit && getBuilding().getBuildingType().getName() == BuildingTypeName.KILLING_PIT && getBuilding().getGovernment() != Stronghold.getCurrentBattle().getGovernmentAboutToPlay();
    }

    public boolean isBuildable() {
        return droppable == null && !(fires.size() > 0) && texture.isBuildable() && !isKeep();
    }

    public boolean canDigHere() {
        return droppable == null && !(fires.size() > 0) && isBuildable();
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

    public void removeBuilding() {
        if (this.getBuilding() != null) {
            this.setDroppable(null);
        }
    }

    public void removeFires(Battle battle) {
        for (int i = fires.size() - 1; i >= 0; i--) {
            if (!fires.get(i).isStillOn(battle.getTurnsPassed()))
                fires.remove(i);
        }
    }

    public boolean isOnFire() {
        return fires.size() > 0;
    }

    public void setOnFire(Fire fire) {
        fires.add(fire);
    }
}