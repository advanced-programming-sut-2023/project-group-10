package model;

public class CastleBuildings extends Buildings{
    private String name;
    private int hitPoint;
    private int fireRange;
    private int defenceRange;
    private int capacity;
    private int cost;
    private int damage;
    private int rate;

    public CastleBuildings(String name, int hitPoint, int fireRange, int defenceRange){
        this.name = name;
        this.hitPoint = hitPoint;
        this.fireRange = fireRange;
        this.defenceRange = defenceRange;
    }

    public CastleBuildings(String name, int hitPoint, int rate){
        this.name = name;
        this.hitPoint = hitPoint;
        this.rate = rate;
    }

    public CastleBuildings(String name, int hitPoint, int capacity, int damage, int cost){
        this.name = name;
        this.hitPoint = hitPoint;
        this.capacity = capacity;
        this.damage = damage;
        this.cost = cost;
    }

    public void repair(){

    }
}
