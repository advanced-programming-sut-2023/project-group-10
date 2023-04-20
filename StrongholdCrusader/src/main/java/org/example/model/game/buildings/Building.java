package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.Droppable;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;

public class Building implements Droppable {
    private Coordinate position;
    private final Government government;
    private final BuildingType buildingType;
    private int hitPoint;

    public Building(Coordinate position, Government government, BuildingTypeName buildingType) {
        this.position = position;
        this.government = government;
        this.buildingType = BuildingType.getBuildingTypeByName(buildingType);
        this.hitPoint = this.buildingType.getMaxHitPoint();
    }

    public Coordinate getPosition() {
        return position;
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
