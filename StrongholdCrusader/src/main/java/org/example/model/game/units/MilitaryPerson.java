package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class MilitaryPerson extends MilitaryUnit {
    public MilitaryPerson(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
    }
}
