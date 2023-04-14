
package model.government;

import model.Trade;
import model.User;
import model.NumericalEnums;
import model.environment.Coordinate;
import model.government.units.MilitaryPerson;
import model.government.units.Unit;

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
    private ArrayList<Trade> tradeList = new ArrayList<>();

    public Government(User owner, Color color) {

        this.owner = owner;
        this.lord = new MilitaryPerson("lord", this);
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

    public void addPerson(String role, Coordinate target) {
    }

    public int getItemCount(Item item) {
        return itemList.getOrDefault(item, 0);
    }

    public void buyItem(Item item, int itemCount) {
        if (itemList.containsKey(item))
            itemList.put(item, itemList.get(item) + itemCount);
        else itemList.put(item, itemCount);
    }

    public void sellItem(Item item, int amount) {
        if (itemList.get(item) >= amount)
            itemList.put(item, itemList.get(item) - amount);
        else itemList.remove(item);
    }

    public void tradeItem(Trade trade) {

    }

    public void addToTradeList(Trade trade) {
    }

    public ArrayList<Trade> getTradeList() {
        return tradeList;
    }


    public HashMap<String, Integer> getPopularityFactors() {
        return popularityFactors;
    }
}