package org.example.model.game.buildings;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.envirnmont.Coordinate;

public class ItemProducingBuilding extends Building {
    private final int turnIndicator;

    public ItemProducingBuilding(Coordinate position, Government government, BuildingTypeName buildingType) {
        super(position, government, buildingType);
        turnIndicator = Stronghold.getCurrentBattle().getTurnsPassed() % ((ItemProducingBuildingType) getBuildingType()).getRate();
    }

    public void produce() {
        ItemProducingBuildingType buildingType = (ItemProducingBuildingType) getBuildingType();
        if (Stronghold.getCurrentBattle().getTurnsPassed() % buildingType.getRate() != turnIndicator) return;
        for (Item item : buildingType.getItems())
            item.tryToProduceThisMany(getGovernment(), buildingType.getItemCountProducedPerProduction());
    }
}
