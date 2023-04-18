package org.example.model.game;

public enum NumericalEnums {
    INITIAL_POPULARITY_VALUE(100),
    SPEED_COEFFICIENT(3);
    private final int value;

    NumericalEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
