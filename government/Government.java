package model.government;

import model.Trade;
import model.User;
import model.NumericalEnums;
import model.environment.Coordinate;
import model.government.people.MilitaryUnit;
import model.government.people.Role;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {

    private User owner;
    private MilitaryUnit lord;
    private final HashMap<Role, Integer> peopleList = new HashMap<>();
    private final HashMap<Item, Integer> itemList = new HashMap<>();
    private int popularity;
    private HashMap<String, Boolean> popularityFactors = new HashMap<>();
    private int popularity;
    private double gold;
    // TODO: check the fields name with Rozhin
    private final Color color;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private ArrayList<Trade> tradeList = new ArrayList<>();

    public Government(User owner, Color color) {

        this.owner = owner;
        lord = new MilitaryUnit("lord", this);
        this.popularity = NumericalEnums.INITIAL_POPULARITY_VALUE.getValue();
        this.color = color;
    }

    public User getOwner() {
        return owner;
    }

    public MilitaryUnit getLord() {
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

    public void religion() {
        popularityFactors.put("religion", true);
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

    public HashMap<Role, Integer> getPeopleList() {
        return peopleList;
    }

    public void addPerson(String role, Coordinate target) {
    }

    public void addItem(Item item, int itemCount) {
        if (itemList.containsKey(item))
            itemList.put(item, itemList.get(item) + itemCount);
        else itemList.put(item, itemCount);
    }

    public void sellItem(Item item, int amount) {
        if (itemList.get(item) >= amount)
            itemList.put(item, itemList.get(item) - amount);
        else itemList.remove(item);
    }

    public void addToTradeList(Trade trade) {
    }

    public ArrayList<Trade> getTradeList() {
        return tradeList;
    }


    public HashMap<String, Boolean> getPopularityFactors() {
        return popularityFactors;
    }
}