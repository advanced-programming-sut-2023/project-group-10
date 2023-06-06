package org.example.model.game;

import java.util.LinkedHashMap;

public enum TreeType {
    DESERT_SHRUB1("dessert shrub 1", 0, "desert-shrub1.png"),
    DESERT_SHRUB2("dessert shrub 2", 0, "desert-shrub2.png"),
    DESERT_SHRUB3("dessert shrub 3", 0, "desert-shrub3.png"),
    DESERT_SHRUB4("dessert shrub 4", 0, "desert-shrub4.png"),
    DESERT_SHRUB5("dessert shrub 5", 0, "desert-shrub5.png"),
    DESERT_SHRUB6("dessert shrub 6", 0, "desert-shrub6.png"),
    CHERRY_PALM("cherry palm", 50, "cherry-palm.png"),
    OLIVE_TREE("olive tree", 50, "olive-tree.png"),
    COCONUT_PALM("coconut palm", 50, "coconut-palm.png"),
    DATE_PALM("date palm", 50, "date-palm.png");

    private static final String treeListAssetsFolderPath = TreeType.class.getResource("/images/plants/list").toExternalForm();
    private final String name;
    private final int maxWoodStorage;
    private final String listAssetFileName;

    TreeType(String name, int maxWoodStorage, String listAssetFileName) {
        this.name = name;
        this.maxWoodStorage = maxWoodStorage;
        this.listAssetFileName = listAssetFileName;
    }

    public static String getTreeListAssetsFolderPath() {
        return treeListAssetsFolderPath;
    }

    public static TreeType getTreeTypeByName(String name) {
        for (TreeType treeType : TreeType.values())
            if (treeType.name.equals(name)) return treeType;
        return null;
    }

    public static LinkedHashMap<String, String> getItemNameFileNameMap() {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        for (TreeType value : values())
            result.put(value.getName(), value.getListAssetFileName());
        return result;
    }

    public String getName() {
        return name.replace("_", " ").toLowerCase();
    }

    public int getMaxWoodStorage() {
        return maxWoodStorage;
    }

    public String getListAssetFileName() {
        return listAssetFileName;
    }
}