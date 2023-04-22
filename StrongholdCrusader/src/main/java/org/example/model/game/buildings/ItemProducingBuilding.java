package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.envirnmont.Coordinate;

public class ItemProducingBuilding extends Building {
    private int turnsPassedSinceCreation;

    public ItemProducingBuilding(Coordinate position, Government government, BuildingTypeName buildingType) {
        super(position, government, buildingType);
    }

    public int getTurnsPassedSinceCreation() {
        return turnsPassedSinceCreation;
    }

    public void goToNextTurn() {
        turnsPassedSinceCreation++;
    }

    public void produce() {
        //return number of products made
        ItemProducingBuildingType buildingType = (ItemProducingBuildingType) getBuildingType();
        if (turnsPassedSinceCreation % buildingType.getRate() != 0) return;

        for (Item item : buildingType.getItems())
            item.tryToProduceThisMany(getGovernment(), buildingType.getItemCountProducedPerProduction());
    }
}
