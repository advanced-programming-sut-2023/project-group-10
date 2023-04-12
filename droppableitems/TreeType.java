package model.droppableitems;

public enum TreeType {
    DESSERT_SHRUB("dessert shrub"),
    CHERRY_PALM("cherry palm"),
    OLIVE_TREE("olive tree"),
    COCONUT_PALM("coconut palm"),
    DATE_PALM("date palm");

    private final String name;

    TreeType(String name) {
        this.name = name;
    }

    public static TreeType getTreeTypeByName(String name) {
        return TreeType.valueOf(name);
    }
}