public class FarmBuildings extends Buildings{
    private int rate;
    private int maxRate;

    public FarmBuildings(String name, int hitPoint, int rate) {
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
