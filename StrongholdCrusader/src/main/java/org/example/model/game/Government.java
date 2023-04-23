package org.example.model.game;

import org.example.model.User;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.MilitaryPerson;
import org.example.model.game.units.Unit;
import org.example.model.game.units.unitconstants.RoleName;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private final User owner;
    private final MilitaryPerson lord;
    private final ArrayList<Unit> units = new ArrayList<>();
    private final HashMap<Item, Integer> itemList = new HashMap<>();
    private int popularity;
    private final HashMap<String, Integer> popularityFactors = new HashMap<>();
    private double gold;
    // TODO: check the fields name with Rozhin
    private final Color color;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private final ArrayList<Trade> tradeList = new ArrayList<>();

    public Government(User owner, Color color) {

        this.owner = owner;
        //TODO: set lord's position to keep's position
        this.lord = new MilitaryPerson(new Coordinate(0, 0), RoleName.LORD, this);
        this.popularity = NumericalEnums.INITIAL_POPULARITY_VALUE.getValue();
        this.color = color;
    }

    public User getOwner() {
        return owner;
    }

    public MilitaryPerson getLord() {
        return lord;
    }

    public int getPopularity() {
        return popularity;
    }

    public void changePopularity(int value) {
        popularity += value;
    }

    public void setTaxRate(int value) {
        taxRate = value;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public void addReligion() {
        //TODO: add percentage of blessed units to the mix
        popularityFactors.put("religion", 1);
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public double getGold() {
        return gold;
    }

    public int getTaxRate() {
        return taxRate;
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

    public int getItemCount(Item item) {
        return itemList.getOrDefault(item, 0);
    }

    public void changeGold(double change) {
        gold += change;
    }

    public void addItem(Item item, int amount) {
        if (itemList.containsKey(item))
            itemList.put(item, itemList.get(item) + amount);
        else itemList.put(item, amount);
        gold -= item.getBuyPrice() * amount;

    }

    public void addItem(Trade trade) {
        Item item = trade.getItem();
        if (itemList.containsKey(item))
            itemList.put(item, itemList.get(item) + trade.getAmount());
        else itemList.put(item, trade.getAmount());
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
        gold -= price * amount;
    }

    public void changeItemCount(Item item, int change) {
        itemList.put(item, itemList.getOrDefault(item, 0) + change);
    }


    public void addToTradeList(Trade trade) {
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

}