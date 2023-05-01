package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.Moat;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.RoleName;

public class MilitaryPerson extends MilitaryUnit {
    Moat moatAboutToBeDug;
    public MilitaryPerson(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        moatAboutToBeDug=null;
    }

    public Moat getMoatAboutToBeDug() {
        return moatAboutToBeDug;
    }

    public void setMoatAboutToBeDug(Moat moatAboutToBeDug) {
        this.moatAboutToBeDug = moatAboutToBeDug;
    }
}
