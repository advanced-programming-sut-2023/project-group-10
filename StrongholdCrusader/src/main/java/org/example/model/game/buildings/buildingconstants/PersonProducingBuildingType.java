package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;
import org.example.model.game.units.unitconstants.Role;

import java.util.HashMap;

public class PersonProducingBuildingType extends BuildingType {
    final Role[] producedPersonType;

    static {

    }

    PersonProducingBuildingType(String name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, Role...producedPeopleTypes) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.producedPersonType = producedPeopleTypes;
    }

    public Role[] getProducedPersonType() {
        return producedPersonType;
    }
}
