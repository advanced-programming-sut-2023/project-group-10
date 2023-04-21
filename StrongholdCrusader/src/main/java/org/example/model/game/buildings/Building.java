package org.example.model.game.buildings;

import org.example.model.game.Droppable;
import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;

public class Building extends Droppable {
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

    public boolean isClimbable() {
        return climbable;
    }

    public void setClimbable(boolean climbable) {
        this.climbable = climbable;
    }
}
