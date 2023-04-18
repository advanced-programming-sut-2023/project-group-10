package org.example.model.game.buildings;

import org.example.model.game.Government;

public class ProducingBuilding extends Building {
    public ProducingBuilding(Government government, String buildingType, int hitPoint) {
        super(government, buildingType, hitPoint);
    }

    public void produce() {
    }
}
