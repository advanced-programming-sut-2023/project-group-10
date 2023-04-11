package model;

public enum Food {
    APPLE("apple",0,0),
    BREAD("bread",0,0),
    CHEESE("cheese",0,0),
    MEAT("meat",0,0);

    private final String name;
    private final int sellPrice;
    private final int buyPrice;

     Food(String name, int sellPrice, int buyPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }


    public String getName() {
        return name;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }
    public Food getFoodByName(String name){
        for (Food food : Food.values()) {
            if(food.getName().equals(name))
                return food;

        }
        return null;
    }
}
