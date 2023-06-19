package org.example.model.game.buildings.buildingconstants;

import org.example.model.game.Item;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.Role;

import java.util.Map;

public class PersonProducingBuildingType extends BuildingType {
    final MilitaryPersonRole[] producedPersonType;

    PersonProducingBuildingType(BuildingTypeName name, int maxHitPoint, int buildingCost, Map<Item, Integer> resourcesNeeded, boolean isRepairable, BuildingCategory category, MilitaryPersonRole... producedPeopleTypes) {
        super(name, maxHitPoint, buildingCost, resourcesNeeded, isRepairable, category);
        this.producedPersonType = producedPeopleTypes;
    }

    public static void initializeTypes() {
        new PersonProducingBuildingType(BuildingTypeName.BARRACKS, defaultHitPoint * 4, 0, Map.of(Item.STONE, 15), true, BuildingCategory.CASTLE, MilitaryPersonRole.getRolesProducedInBuilding(BuildingTypeName.BARRACKS));
        new PersonProducingBuildingType(BuildingTypeName.MERCENARY_POST, defaultHitPoint * 4, 0, Map.of(Item.WOOD, 10), true, BuildingCategory.CASTLE, MilitaryPersonRole.getRolesProducedInBuilding(BuildingTypeName.MERCENARY_POST));
        new PersonProducingBuildingType(BuildingTypeName.ENGINEER_GUILD, defaultHitPoint * 4, 100, Map.of(Item.WOOD, 10), true, BuildingCategory.CASTLE, MilitaryPersonRole.getRolesProducedInBuilding(BuildingTypeName.ENGINEER_GUILD));
    }

    public Role[] getProducedPersonType() {
        return producedPersonType;
    }
}
