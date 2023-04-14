public class WeaponBuildings extends Buildings{
    private String name;
    private int hitPoint;
    private int materials;
    private int productionRate;

    public WeaponBuildings(String name, int hitPoint, int materials, int productionRate){
        this.name = name;
        this.hitPoint = hitPoint;
        this.materials = materials;
        this.productionRate = productionRate;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getMaterials() {
        return materials;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public void setMaterials(int materials) {
        this.materials = materials;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }
}
