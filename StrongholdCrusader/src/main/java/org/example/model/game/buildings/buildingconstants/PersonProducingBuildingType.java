package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.Role;

import java.util.HashMap;
import java.util.Map;

public class PersonProducingBuildingType extends BuildingType {
    final MilitaryPersonRole[] producedPersonType;

    static {
        new PersonProducingBuildingType(BuildingTypeName.BARRACKS, defaultHitPoint * 4, 0, (HashMap<Item, Integer>) Map.of(Item.STONE, 15), MilitaryPersonRole.getRolesProducedInBuilding(BuildingTypeName.BARRACKS));
        new PersonProducingBuildingType(BuildingTypeName.MERCENARY_POST, defaultHitPoint * 4, 0, (HashMap<Item, Integer>) Map.of(Item.WOOD, 10), MilitaryPersonRole.getRolesProducedInBuilding(BuildingTypeName.MERCENARY_POST));
        new PersonProducingBuildingType(BuildingTypeName.ENGINEER_GUILD, defaultHitPoint * 4, 100, (HashMap<Item, Integer>) Map.of(Item.WOOD, 10), MilitaryPersonRole.getRolesProducedInBuilding(BuildingTypeName.ENGINEER_GUILD));
    }

    PersonProducingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, HashMap<Item, Integer> resourcesNeeded, MilitaryPersonRole... producedPeopleTypes) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded);
        this.producedPersonType = producedPeopleTypes;
    }

    public Role[] getProducedPersonType() {
        return producedPersonType;
    }
}
