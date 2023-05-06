package org.example.model.game;

public enum TreeType {
    DESSERT_SHRUB("dessert shrub", NumericalEnums.INITIAL_TREE_WOODS.getValue()),
    CHERRY_PALM("cherry palm", NumericalEnums.INITIAL_TREE_WOODS.getValue()),
    OLIVE_TREE("olive tree", NumericalEnums.INITIAL_TREE_WOODS.getValue()),
    COCONUT_PALM("coconut palm", NumericalEnums.INITIAL_TREE_WOODS.getValue()),
    DATE_PALM("date palm", NumericalEnums.INITIAL_TREE_WOODS.getValue());

    private final String name;
    private final int maxWoodStorage;

    TreeType(String name, int maxWoodStorage) {
        this.name = name;
       this.maxWoodStorage = maxWoodStorage;
    }

    public static TreeType getTreeTypeByName(String name) {
        for (TreeType treeType : TreeType.values())
            if (treeType.name.equals(name)) return treeType;
        return null;
    }

    public static int getMaxWoodStorage(TreeType treeType) {
        return treeType.maxWoodStorage;
    }

    public String getName() {
        return name.replace("_"," ").toLowerCase();
    }
}