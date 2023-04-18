package org.example.model.game.buildings;

import org.example.model.game.Government;
import org.example.model.game.Item;

import java.util.HashMap;

public class StorageBuilding extends Building {
    private HashMap<Item, Integer> itemsInStock;

    public StorageBuilding(Government government, String buildingType, int hitPoint) {
        super(government, buildingType, hitPoint);
    }

    public HashMap<Item, Integer> getItemsInStock() {
        return itemsInStock;
    }

    public void addItem() {
    }

    public void removeItem() {

    }
}
