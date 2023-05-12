package org.example.model.game;

public enum TreeType {
    DESSERT_SHRUB("dessert shrub"),
    CHERRY_PALM("cherry palm"),
    OLIVE_TREE("olive tree"),
    COCONUT_PALM("coconut palm"),
    DATE_PALM("date palm");

    private final String name;
    private final int maxWoodStorage = 50;

    TreeType(String name) {
        this.name = name;
    }

    public static TreeType getTreeTypeByName(String name) {
        name = name.replaceAll("[\\s_-]", "");
        for (TreeType treeType : TreeType.values())
            if (treeType.name.replaceAll("\\s", "").equalsIgnoreCase(name)) return treeType;
        return null;
    }

    public static int getMaxWoodStorage(TreeType treeType) {
        return treeType.maxWoodStorage;
    }

    public String getName() {
        return name.replace("_", " ").toLowerCase();
    }
}