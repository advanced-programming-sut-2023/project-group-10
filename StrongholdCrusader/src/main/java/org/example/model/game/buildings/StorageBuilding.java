package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.Item;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;

import java.util.HashMap;

public class StorageBuilding extends Building {
    private HashMap<Item, Integer> itemsInStock;

    public StorageBuilding(Coordinate position, Government government, BuildingTypeName buildingType) {
        super(position, government, buildingType);
    }

    public HashMap<Item, Integer> getItemsInStock() {
        return itemsInStock;
    }

    public void addItem() {
    }

    public void removeItem() {

    }
}
