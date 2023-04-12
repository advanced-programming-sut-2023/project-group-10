package main.java.model.government;

import main.java.model.Color;
import main.java.model.Item;
import main.java.model.Trade;
import main.java.model.User;
import model.NumericalEnums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Government {

    private User owner;
    private int popularity;
    private double gold;
    // TODO: check the fields name with Rozhin
    private int stone;
    private int wood;
    private Color color;
    private HashMap<Item,Integer> foodList=new HashMap<Item, Integer>();
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private  ArrayList<Trade> tradeList=new ArrayList<>();

    public Government(User owner, Color color) {

        this.owner = owner;
        this.popularity= NumericalEnums.INITIAL_POPULARITY_VALUE.getValue();
        this.color=color;
    }
    public User getOwner() {
        return owner;
    }

    public int getPopularity() {
        return popularity;
    }

    public void changePopularity(int value) {
        popularity += value;
    }
    public void setTaxRate(int value){
        taxRate=value;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
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
    public void addFood(Item food, int foodCount){
        if(foodList.containsKey(food)){
            foodList.put(food,foodList.get(food)+foodCount);
            return;
        }
        foodList.put(food,foodCount);

    }
    public void sellFood(Item food, int amount){
        if(foodList.get(food) > amount){
            foodList.put(food,foodList.get(food)-amount);
            return;
        }
        foodList.remove(food);
    }

    public void addToTradeList(Trade trade){

    }

    public ArrayList<Trade> getTradeList() {
        return tradeList;
    }

}
