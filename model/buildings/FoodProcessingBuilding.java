package model.buildings;

public class FoodProcessingBuilding extends Building{
    private int rate;

    public FoodProcessingBuilding(String name, int hitPoint, int rate){
        super(name, hitPoint);
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
