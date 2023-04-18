package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;

public class Stairs extends Building {
    public Stairs(Government government, String buildingType, int hitPoint) {
        super(government, buildingType, hitPoint);
    }

    public void makeClimbable(Coordinate coordinate) {
    }
}
