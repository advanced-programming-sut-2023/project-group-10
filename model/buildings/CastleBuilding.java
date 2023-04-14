package model.buildings;

public class CastleBuilding extends Building{
    private int fireRange;
    private int defenceRange;
    private int capacity;
    private int cost;
    private int damage;
    private int rate;

    public CastleBuilding(String name, int hitPoint, int fireRange, int defenceRange){
        super(name, hitPoint);
        this.fireRange = fireRange;
        this.defenceRange = defenceRange;
    }

    public CastleBuilding(String name, int hitPoint, int rate){
        super(name, hitPoint);
        this.rate = rate;
    }

    public CastleBuilding(String name, int hitPoint, int capacity, int damage, int cost){
        super(name, hitPoint);
        this.capacity = capacity;
        this.damage = damage;
        this.cost = cost;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getDefenceRange() {
        return defenceRange;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getRate() {
        return rate;
    }

    public void repair(){
    }
}
