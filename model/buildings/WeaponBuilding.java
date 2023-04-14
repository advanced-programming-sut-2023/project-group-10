package model.buildings;

public class WeaponBuilding extends Building{
    private int materials;
    private int productionRate;

    public WeaponBuilding(String name, int hitPoint, int materials, int productionRate){
        super(name, hitPoint);
        this.materials = materials;
        this.productionRate = productionRate;
    }

    public int getMaterials() {
        return materials;
    }

    public int getProductionRate() {
        return productionRate;
    }

    public void setMaterials(int materials) {
        this.materials = materials;
    }

    public void setProductionRate(int productionRate) {
        this.productionRate = productionRate;
    }
}
