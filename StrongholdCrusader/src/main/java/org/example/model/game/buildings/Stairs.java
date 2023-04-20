package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;

public class Stairs extends Building {
    public Stairs(Coordinate position, Government government, String buildingType) {
        super(position, government, BuildingTypeName.STAIRS);
    }

    public void makeClimbable(Coordinate coordinate) {
    }
}
