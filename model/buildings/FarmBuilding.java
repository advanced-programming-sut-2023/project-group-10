package model.buildings;

public class FarmBuilding extends Building {
    private int rate;
    private int maxRate;

    public FarmBuilding(String name, int hitPoint, int rate) {
        super(name, hitPoint);
        this.rate = rate;
        this.maxRate = rate;
    }

    public int getMaxRate(){
        return maxRate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
