package model.buildings;

public class IndustryBuilding extends Building{
    private int rate;
    private int capacity;

    public IndustryBuilding(String name, int hitPoint, int rate, int capacity){
        super(name, hitPoint);
        this.rate = rate;
        this.capacity = capacity;
    }

    public int getRate() {
        return rate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
