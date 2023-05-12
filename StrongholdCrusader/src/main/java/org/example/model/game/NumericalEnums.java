package org.example.model.game;

public enum NumericalEnums {
    INITIAL_POPULARITY_VALUE(100),
    SPEED_COEFFICIENT(1),
    RANGE_COEFFICIENT(3),
    DAMAGE_COEFFICIENT(10),
    WOOD_REDUCTION_RATE(2),
    SCORE_CONSTANT(150),
    PEASANT_PRODUCTION_RATE(5);
    private final int value;

    NumericalEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
