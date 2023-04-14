//package model;

public class CastleBuildings extends Buildings{
    private int fireRange;
    private int defenceRange;
    private int capacity;
    private int cost;
    private int damage;
    private int rate;

    public CastleBuildings(String name, int hitPoint, int fireRange, int defenceRange){
        super(name, hitPoint);
        this.fireRange = fireRange;
        this.defenceRange = defenceRange;
    }

    public CastleBuildings(String name, int hitPoint, int rate){
        super(name, hitPoint);
        this.rate = rate;
    }

    public CastleBuildings(String name, int hitPoint, int capacity, int damage, int cost){
        super(name, hitPoint);
        this.capacity = capacity;
        this.damage = damage;
        this.cost = cost;
    }

    public void repair(){

    }
}
