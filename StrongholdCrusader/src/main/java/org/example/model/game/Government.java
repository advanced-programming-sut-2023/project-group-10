package org.example.model.game;

import javafx.scene.paint.Color;
import org.example.model.User;
import org.example.model.game.buildings.Building;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.buildings.buildingconstants.StorageBuildingType;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.RoleName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Government {
    private final User owner;
    private final ArrayList<Unit> units = new ArrayList<>();
    private final HashMap<Item, Double> itemList = new HashMap<>();
    private final HashMap<String, Integer> popularityFactors = new HashMap<>();
    private final Color color;
    private final ArrayList<Trade> tradeList = new ArrayList<>();
    private final Coordinate keep;
    private ArrayList<Building> buildings = new ArrayList<>();
    private int popularity;
    private double gold;
    private int foodRate;
    private int excessFood;
    private int taxRate;
    private int fearRate;

    public Government(User owner, Color color, Coordinate keep) {
        this.owner = owner;
        this.popularity = NumericalEnums.INITIAL_POPULARITY_VALUE.getValue();
        this.color = color;
        this.keep = keep;
        for (Item item : Item.values())
            itemList.put(item, 0.0);
        foodRate = -2;
        popularityFactors.put("Food", -2);
        taxRate = 0;
        popularityFactors.put("Tax", 0);
        fearRate = 0;
        popularityFactors.put("Fear", 0);
        popularityFactors.put("Religion", 0);
    }

    public Coordinate getKeep() {
        return keep;
    }

    public User getOwner() {
        return owner;
    }

    public MilitaryPerson getLord() {
        return (MilitaryPerson) units.get(0);
    }

    public int getPopularity() {
        return popularity;
    }

    public void changePopularity(int value, String factor) {
        popularityFactors.put(factor, value);
        popularity += value;
    }

    public void addReligion() {
        popularityFactors.put("Religion", 1);
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
        popularityFactors.put("Food", popularityFactors.get("Food") + foodRate);
    }

    public int getExcessFood() {
        return excessFood;
    }

    public void setExcessFood(int excessFood) {
        this.excessFood = excessFood;
    }

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
        popularityFactors.put("Tax", popularityFactors.get("Tax") + taxRate);
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public boolean deleteUnit(Unit unit) {
        return units.remove(unit);
    }

    public Double getItemCount(Item item) {
        return itemList.getOrDefault(item, (double) 0);
    }

    public void changeGold(double change) {
        gold += change;
    }

    public void addItem(Item item, int amount) {
        if (itemList.containsKey(item))
            itemList.put(item, itemList.get(item) + amount);
        else itemList.put(item, (double) amount);

        gold -= item.getBuyPrice() * amount;

    }

    public void addItem(Trade trade) {
        Item item = trade.getItem();
        if (itemList.containsKey(item))
            itemList.put(item, itemList.get(item) + trade.getAmount());
        else itemList.put(item, (double) trade.getAmount());
        gold -= trade.getPrice() * trade.getAmount();

    }

    public void reduceItem(Trade trade) {
        Item item = trade.getItem();
        if (itemList.get(item) >= trade.getAmount())
            itemList.put(item, itemList.get(item) - trade.getAmount());
        else itemList.remove(item);
        gold += trade.getPrice() * trade.getAmount();

    }

    public void reduceItem(Item item, int amount, double price) {
        if (itemList.get(item) >= amount)
            itemList.put(item, itemList.get(item) - amount);
        else itemList.remove(item);
        gold += price * amount;
    }

    public void changeItemCount(Item item, double change) {
        itemList.put(item, itemList.getOrDefault(item, (double) 0) + change);
    }

    public void addToTradeList(Trade trade) {
        tradeList.add(trade);
    }

    public ArrayList<Trade> getTradeList() {
        return tradeList;
    }

    public HashMap<String, Integer> getPopularityFactors() {
        return popularityFactors;
    }

    public Trade getTradeFromTradeList(String id) {
        for (Trade trade : tradeList) {
            if (trade.getId().equals(id))
                return trade;
        }
        return null;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
        popularityFactors.put("Fear", popularityFactors.get("Fear") + fearRate);
    }

    public HashMap<Item, Double> getFoodList() {
        HashMap<Item, Double> foodList = new HashMap<>();
        for (Map.Entry<Item, Double> list : itemList.entrySet()) {
            if (list.getKey().isFood())
                foodList.put(list.getKey(), list.getValue());
        }
        return foodList;
    }

    public void getBuildings(org.example.model.game.envirnmont.Map gameMap) {
        buildings = new ArrayList<>();
        for (int i = 0; i < gameMap.getSize(); i++) {
            for (int j = 0; j < gameMap.getSize(); j++) {
                if (gameMap.getBlockByRowAndColumn(i, j).getBuilding().getGovernment().equals(this)) {
                    buildings.add(gameMap.getBlockByRowAndColumn(i, j).getBuilding());
                }
            }
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }


    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public boolean deleteBuilding(Building building) {
        return buildings.remove(building);
    }

    public HashMap<Item, Double> getItemList() {
        return itemList;
    }

    public boolean equals(Government government) {
        return government.getOwner().getUsername().equals(this.getOwner().getUsername());
    }

    public int getPeasantsCount() {
        return getPeasants().size();
    }

    public ArrayList<Unit> getPeasants() {
        ArrayList<Unit> peasant = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getRole().equals(Role.getRoleByName(RoleName.PEASANT)))
                peasant.add(unit);
        }
        return peasant;
    }

    public int getCitizensCounts() {
        int citizens = 0;
        for (Unit unit : units) {
            if (!(unit instanceof MilitaryUnit))
                citizens++;
        }
        return citizens;

    }

    public double calculateTax() {
        if (taxRate == 0)
            return 0;
        double rate = (0.6 + (Math.abs(taxRate) - 1) * 0.2);
        if (taxRate > 0)
            return rate;
        else
            return -rate;

    }

    public void changeExcessFood(int change) {
        excessFood += change;
    }

    private int getTotalItemCount(String type) {
        int count = 0;
        for (Map.Entry<Item, Double> entry : itemList.entrySet())
            if (entry.getKey().isFood() && type.equals("food") ||
                    Arrays.asList(Item.getPrimaryItems()).contains(entry.getKey()) && type.equals("primary") ||
                    Arrays.asList(Item.getWeaponsAndArmors()).contains(entry.getKey()) && type.equals("weapon"))
                count += entry.getValue();
        return count;
    }

    private int getStorageSpace(BuildingTypeName storageName) {
        int storageSpace = 0;
        for (Building building : buildings)
            if (building.getBuildingType().getName() == storageName)
                storageSpace += ((StorageBuildingType) building.getBuildingType()).getCapacity();
        return storageSpace;
    }

    public int getStorageSpaceForItem(Item item) {
        if (!item.isSellable()) return Integer.MAX_VALUE;
        if (item.isFood()) return getStorageSpace(BuildingTypeName.GRANARY) - getTotalItemCount("food");
        if (Arrays.asList(Item.getPrimaryItems()).contains(item))
            return getStorageSpace(BuildingTypeName.STOCKPILE) - getTotalItemCount("primary");
        return getStorageSpace(BuildingTypeName.ARMOURY) - getTotalItemCount("weapon");
    }
}