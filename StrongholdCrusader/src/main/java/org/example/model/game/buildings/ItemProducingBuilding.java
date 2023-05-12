package org.example.model.game.buildings;

import org.example.model.Stronghold;
import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.Tree;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.ItemProducingBuildingType;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;

public class ItemProducingBuilding extends Building {
    private final int turnIndicator;

    public ItemProducingBuilding(Coordinate position, Government government, BuildingTypeName buildingType) {
        super(position, government, buildingType);
        turnIndicator = Stronghold.getCurrentBattle().getTurnsPassed() % ((ItemProducingBuildingType) getBuildingType()).getRate();
    }

    public void produce() {
        ItemProducingBuildingType buildingType = (ItemProducingBuildingType) getBuildingType();
        if (Stronghold.getCurrentBattle().getTurnsPassed() % buildingType.getRate() != turnIndicator) return;
        for (Item item : buildingType.getItems()) {
            int possibleToProduce = Math.min(buildingType.getItemCountProducedPerProduction(), item.numberOfItemThatCanBeProduced(getGovernment()));
            int storageSpace = getGovernment().getStorageSpaceForItem(item);
            if (item.isFood() && storageSpace < possibleToProduce)
                getGovernment().changeExcessFood(possibleToProduce - storageSpace);
            if (item == Item.WOOD) {
                Coordinate closestTreePosition = this.findClosestTreePosition();
                if (closestTreePosition == null) continue;
                Tree closestTree = (Tree) Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(closestTreePosition).getDroppable();
                closestTree.reduceWoodStorage(Math.min(possibleToProduce, storageSpace));
                if (closestTree.getWoodStorage() <= 0)
                    Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(closestTreePosition).setDroppable(null);
            }
            item.tryToProduceThisMany(getGovernment(), Math.min(possibleToProduce, storageSpace));
        }
    }

    private Coordinate findClosestTreePosition() {
        Map map = Stronghold.getCurrentBattle().getBattleMap();
        int minDistance = Integer.MAX_VALUE;
        Coordinate closestTreePosition = null;
        for (int i = 0; i < map.getSize(); i++)
            for (int j = 0; j < map.getSize(); j++)
                if (map.getBlockByRowAndColumn(i, j).getDroppable() instanceof Tree && new Coordinate(i, j).getDistanceFrom(getPosition()) < minDistance) {
                    closestTreePosition = new Coordinate(i, j);
                    minDistance = closestTreePosition.getDistanceFrom(getPosition());
                }
        return closestTreePosition;
    }
}
