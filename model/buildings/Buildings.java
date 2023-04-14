public class Buildings {

    protected String name;
    protected int hitPoint;

    public Buildings(String name, int hitPoint){
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

    public void finalizeBuild(Government government, int woodCost, int stoneCost,
                              int goldCost, int popularityIncrease){

    }
}
