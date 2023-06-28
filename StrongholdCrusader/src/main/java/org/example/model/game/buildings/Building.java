package org.example.model.game.buildings;

import org.example.model.Stronghold;
import org.example.model.game.Entity;
import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;

public class Building extends Entity {
    private final BuildingType buildingType;
    private int hitPoint;

    private boolean climbable;

    public Building(Coordinate position, Government government, BuildingTypeName buildingType) {
        super(position, government);
        this.buildingType = BuildingType.getBuildingTypeByName(buildingType);
        this.hitPoint = this.buildingType.getMaxHitPoint();
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void changeHitPoint(int change) {
        hitPoint += change;
    }

    public boolean isClimbable() {
        return climbable;
    }

    public void setClimbable(boolean climbable) {
        this.climbable = climbable;
    }

    public boolean addToGovernmentAndBlock() {
        if (!Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(this.getPosition()).setDroppable(this))
            return false;
        this.getGovernment().addBuilding(this);
        return true;
    }

    public void deleteBuildingFromMapAndGovernment() {
        this.getGovernment().deleteBuilding(this);
        Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(this.getPosition()).setDroppable(null);
        Stronghold.getCurrentBattle().getBattleMap().getExtendedBlockByRowAndColumn(this.getPosition()).removeBuilding();
    }

    @Override
    public void setGovernment(Government government) {
        this.getGovernment().deleteBuilding(this);
        super.setGovernment(government);
        this.getGovernment().addBuilding(this);
    }
}
