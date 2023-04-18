package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;

public class AttackingBuilding extends Building {
    public AttackingBuilding(Government government, String buildingType, int hitPoint) {
        super(government, buildingType, hitPoint);
    }

    public void attack(Coordinate coordinate) {
    }
}
