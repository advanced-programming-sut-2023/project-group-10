public class FoodProcessingBuildings extends Buildings{
    private int rate;

    public FoodProcessingBuildings(String name, int hitPoint, int rate){
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
