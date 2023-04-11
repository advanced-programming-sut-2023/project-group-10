package model.government;

import model.NumericalEnums;
import model.User;

import java.util.HashMap;

public class Government {
    private User owner;
    private int popularity;
    private int gold;
    // TODO: check the fields name with Rozhin
    private int stone;
    private int wood;
    private HashMap<Food,Integer> foodList=new HashMap<Food, Integer>();
    private int foodRate;
    private int taxRate;
    private int fearRate;

    public Government(User owner) {
        this.owner = owner;
        this.popularity= NumericalEnums.INITIAL_POPULARITY_VALUE.getValue();
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

    public int getGold() {
        return gold;
    }

    public int getTaxRate() {
        return taxRate;
    }
    public void addFood(Food food, int foodCount){
        if(foodList.containsKey(food)){
            foodList.put(food,foodList.get(food)+foodCount);
            return;
        }
        foodList.put(food,foodCount);

    }
    public void sellFood(Food food, int amount){
        if(foodList.get(food) > amount){
            foodList.put(food,foodList.get(food)-amount);
            return;
        }
        foodList.remove(food);
    }

}
