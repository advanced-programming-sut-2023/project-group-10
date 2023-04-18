package org.example.model.game.buildings;

import org.example.model.game.Government;

public class PitchDitch extends AttackingBuilding{
    public PitchDitch(Government government, String buildingType, int hitPoint) {
        super(government, buildingType, hitPoint);
    }

    public void setOnFire() {
    }
}