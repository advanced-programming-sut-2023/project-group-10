package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class Tunneler extends MilitaryUnit {
    public Tunneler(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
    }

    public void digTunnelHere(Coordinate target) {
    }
}
