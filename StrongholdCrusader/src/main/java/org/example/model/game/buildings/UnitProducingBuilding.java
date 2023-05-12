package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.PersonProducingBuildingType;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;

import java.util.Arrays;

public class UnitProducingBuilding extends Building {
    public UnitProducingBuilding(Coordinate position, Government government, BuildingTypeName buildingType) {
        super(position, government, buildingType);
    }

    public int produce(MilitaryPersonRole militaryPersonRole, int count) {
        PersonProducingBuildingType buildingType = (PersonProducingBuildingType) getBuildingType();
        if (!Arrays.asList(buildingType.getProducedPersonType()).contains(militaryPersonRole)) return 0;
        // no units are produced if it's impossible to produce <count> units
        if (count > militaryPersonRole.numberOfUnitsThatCanBeSpawned(getGovernment())) return 0;
        militaryPersonRole.tryToProduceThisMany(getGovernment(), getPosition(), count);
        return count;
    }
}
