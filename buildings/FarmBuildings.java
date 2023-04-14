public class FarmBuildings extends Buildings{
    private String name;
    private int hitPoint;
    private int rate;

    public FarmBuildings(String name, int hitPoint, int rate) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getRate() {
        return rate;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
