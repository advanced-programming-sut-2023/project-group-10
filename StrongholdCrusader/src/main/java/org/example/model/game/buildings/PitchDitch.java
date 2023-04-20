package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;

public class PitchDitch extends AttackingBuilding{
    public PitchDitch(Coordinate position, Government government) {
        super(position, government, BuildingTypeName.PITCH_DITCH);
    }

    public void setOnFire() {
    }
}