package org.example.model.game.units;

import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;

public class MilitaryPerson extends MilitaryUnit {
    public MilitaryPerson(Coordinate position, RoleName role, Government government) {
        super(position, role, government);
        //deduct resources from government
        deductResourcesFromGovernment(((MilitaryPersonRole) Role.getRoleByName(role)));
    }

    protected void deductResourcesFromGovernment(MilitaryPersonRole role) {
        super.deductResourcesFromGovernment(role);
        for (Item armor : role.getArmors())
            getGovernment().changeItemCount(armor, -1);
        getGovernment().changeItemCount(role.getWeapon(), -1);
    }
}
