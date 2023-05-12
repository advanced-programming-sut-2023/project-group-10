package org.example.model.game;

import java.util.HashMap;
import java.util.Map;

public enum Item {
    WOOD("wood", new HashMap<>(), 2, 1, false),
    STONE("stone", new HashMap<>(), 4, 1, false),
    IRON("iron", new HashMap<>(), 4, 2, false),
    PITCH("pitch", new HashMap<>(), 4, 2, false),
    //essentials for foods
    WHEAT("wheat", new HashMap<>(), 18, 1, false),
    FLOUR("flour", Map.of(WHEAT, 1), 24, 1, false),
    HOPS("hops", new HashMap<>(), 4, 1, false),
    //foods
    MEAT("meat", new HashMap<>(), 2, 1, true),
    CHEESE("cheese", new HashMap<>(), 2, 1, true),
    APPLE("apple", new HashMap<>(), 2, 1, true),
    BREAD("bread", Map.of(Item.FLOUR, 1), 3, 1, true),
    ALE("ale", Map.of(Item.HOPS, 1), 6, 2, true),
    //animals (shouldn't appear in shop or trade)
    HORSE("horse", new HashMap<>(), 0, 0, false),
    COW("cow", new HashMap<>(), 0, 0, false),
    //weapons
    BOW("bow", Map.of(WOOD, 2), 16, 8, false),
    CROSSBOW("crossbow", Map.of(WOOD, 3), 16, 8, false),
    SPEAR("spear", Map.of(WOOD, 1), 12, 6, false),
    PIKE("pike", Map.of(WOOD, 2), 24, 12, false),
    MACE("mace", Map.of(IRON, 1), 32, 16, false),
    SWORD("swords", Map.of(IRON, 1), 32, 16, false),
    LEATHER_ARMOR("leather armor", Map.of(COW, 1), 24, 12, false),
    METAL_ARMOR("metal armor", Map.of(IRON, 1), 32, 16, false);

    private final String name;
    private final Map<Item, Integer> resourcesNeeded;
    private final double buyPrice;
    private final double sellPrice;
    private final boolean isFood;

    Item(String name, Map<Item, Integer> resourcesNeeded, double buyPrice, double sellPrice, boolean isFood) {
        this.name = name;
        this.resourcesNeeded = resourcesNeeded;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.isFood = isFood;
    }

    public static Item getItemByName(String name) {
        name = name.toLowerCase();
        for (Item item : Item.values())
            if (item.name.replaceAll("_", "").equals(name)) return item;
        return null;
    }

    public static Item[] getWeaponsAndArmors() {
        return new Item[]{BOW, CROSSBOW, SPEAR, PIKE, MACE, SWORD, LEATHER_ARMOR, METAL_ARMOR, HORSE};
    }

    public static Item[] getFoods() {
        return new Item[]{MEAT, CHEESE, APPLE, BREAD, ALE};
    }

    public static Item[] getPrimaryItems() {
        return new Item[]{WOOD, STONE, IRON, PITCH, WHEAT, FLOUR, HOPS};
    }

    public String getName() {
        return name;
    }

    public Map<Item, Integer> getAllResourcesNeeded() {
        return resourcesNeeded;
    }

    public int getAmountOfResourceNeeded(Item resource) {
        return resourcesNeeded.getOrDefault(resource, 0);
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

    public int numberOfItemThatCanBeProduced(Government government) {
        int result = Integer.MAX_VALUE;
        for (Map.Entry<Item, Integer> resource : resourcesNeeded.entrySet())
            result = (int) Math.min(result, government.getItemCount(resource.getKey()) / resource.getValue());
        return result;
    }

    public int tryToProduceThisMany(Government government, int count) {
        count = Math.min(count, numberOfItemThatCanBeProduced(government));
        for (Map.Entry<Item, Integer> resource : resourcesNeeded.entrySet())
            government.changeItemCount(resource.getKey(), -resource.getValue() * count);
        government.changeItemCount(this, count);
        return count;
    }

    public boolean isSellable() {
        return this != COW && this != HORSE;
    }
}
