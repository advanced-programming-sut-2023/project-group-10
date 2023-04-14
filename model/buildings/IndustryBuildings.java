public class IndustryBuildings extends Buildings{
    private int rate;
    private int capacity;

    public IndustryBuildings(String name, int hitPoint, int rate, int capacity){
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
