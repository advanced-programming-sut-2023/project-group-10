package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class PortableShield extends SiegeEquipment {
    public PortableShield(Coordinate position, RoleName role, Government government, Coordinate currentLocation) {
        super(position, role, government, currentLocation);
    }

    public void defendArmy() {
    }
}