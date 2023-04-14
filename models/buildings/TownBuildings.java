public class TownBuildings extends Buildings{
    private String name;
    private int hitPoint;

    public TownBuildings(String name, int hitPoint){
        this.name = name;
        this.hitPoint = hitPoint;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }
}
