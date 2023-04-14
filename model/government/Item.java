package model.government;

public enum Item {
    //primary resources
    WOOD("wood", 0, 0, 0, 0, false),
    STONE("stone", 0, 0, 0, 0, false),
    IRON("iron", 0, 0, 0, 0, false),
    PITCH("pitch", 0, 0, 0, 0, false),
    //foods
    MEAT("meat", 0, 0, 0, 0, true),
    CHEESE("cheese", 0, 0, 0, 0, true),
    APPLE("apple", 0, 0, 0, 0, true),
    BREAD("bread", 0, 0, 0, 0, true),
    ALE("ale", 0, 0, 0, 0, true),
    //essentials for foods
    WHEAT("wheat", 0, 0, 0, 0, false),
    MILL("mill", 0, 0, 0, 0, false),
    FLOUR("flour", 0, 0, 0, 0, false),
    HOPS("hops", 0, 0, 0, 0, false),
    //weapons
    BOW("bow", 0, 0, 0, 0, false),
    CROSSBOW("crossbow", 0, 0, 0, 0, false),
    SPEAR("spear", 0, 0, 0, 0, false),
    PIKE("pike", 0, 0, 0, 0, false),
    MACE("mace", 0, 0, 0, 0, false),
    SWORDS("swords", 0, 0, 0, 0, false),
    LEATHER_ARMOR("Leather armor", 0, 0, 0, 0, false),
    METAL_ARMOR("metal armor", 0, 0, 0, 0, false);

    private final String name;
    private final int stoneRequired;
    private final int woodRequired;
    private final double buyPrice;
    private final double sellPrice;
    private final boolean isFood;

    Item(String name, int stoneRequired, int woodRequired, double buyPrice, double sellPrice, boolean isFood) {
        this.name = name;
        this.stoneRequired = stoneRequired;
        this.woodRequired = woodRequired;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.isFood = isFood;
    }

    public String getName() {
        return name;
    }

    public int getStoneRequired() {
        return stoneRequired;
    }

    public int getWoodRequired() {
        return woodRequired;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public boolean isFood() {
        return isFood;
    }
}