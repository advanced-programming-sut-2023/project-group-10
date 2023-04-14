public class IndustryBuildings extends Buildings{
    private String name;
    private int hitPoint;
    private int rate;
    private int capacity;

    public IndustryBuildings(String name, int hitPoint, int rate, int capacity){
        this.name = name;
        this.hitPoint = hitPoint;
        this.rate = rate;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
