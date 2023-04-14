package model.government.units;

import model.government.Government;
import model.government.units.SiegeEquipment;

public class PortableShield extends SiegeEquipment {
    public PortableShield(String role, Government government) {
        super(role, government);
    }

    public void defendArmy() {}
}
