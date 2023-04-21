package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.PersonProducingBuildingType;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;

import java.util.Arrays;

public class UnitProducingBuilding extends Building {
    public UnitProducingBuilding(Coordinate position, Government government, BuildingTypeName buildingType) {
        super(position, government, buildingType);
    }

    public int produce(MilitaryPersonRole militaryPersonRole, int count) {
        //returns number of units made (tries to make the specified amount but might fail)
        PersonProducingBuildingType buildingType = (PersonProducingBuildingType) getBuildingType();
        if (!Arrays.asList(buildingType.getProducedPersonType()).contains(militaryPersonRole)) return 0;
        int producibleUnitCount = Math.min(count, militaryPersonRole.numberOfUnitsThatCanBeSpawned(getGovernment()));
        for (int i = 0; i < producibleUnitCount; i++)
            new MilitaryPerson(getPosition(), militaryPersonRole.getName(), getGovernment());
        return producibleUnitCount;
    }
}
