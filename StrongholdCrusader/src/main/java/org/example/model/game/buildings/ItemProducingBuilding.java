package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.envirnmont.Coordinate;

import java.util.Map;

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

    public int produce() {
        //return number of products made
        ItemProducingBuildingType buildingType = (ItemProducingBuildingType) getBuildingType();
        if (turnsPassedSinceCreation % buildingType.getRate() == 0) {
            int producibleItemCount = buildingType.getItemCountProducedPerProduction();
            for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeededPerItem().entrySet())
                producibleItemCount = Math.min(producibleItemCount, getGovernment().getItemCount(entry.getKey()) / entry.getValue());
            for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeededPerItem().entrySet())
                getGovernment().changeItemCount(entry.getKey(), -entry.getValue() * producibleItemCount);
            getGovernment().changeItemCount(buildingType.getItem(), producibleItemCount);
            return producibleItemCount;
        } else return 0;
    }
}
