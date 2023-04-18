package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.Droppable;

public class Building implements Droppable {
    private final Government government;
    private final BuildingType buildingType;
    private int hitPoint;

    public Building(Government government, String buildingType, int hitPoint) {
        this.government = government;
        this.buildingType = BuildingType.getBuildingTypeByName(buildingType);
        this.hitPoint = this.buildingType.getMaxHitPoint();
    }

    public Government getGovernment() {
        return government;
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
}
