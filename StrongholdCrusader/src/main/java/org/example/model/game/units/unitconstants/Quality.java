package org.example.model.game.units.unitconstants;

public enum Quality {
    ZERO(0),
    EXTREMELY_LOW(1),
    VERY_LOW(2),
    LOW(3),
    AVERAGE(4),
    HIGH(5),
    VERY_HIGH(6),
    EXTREMELY_HIGH(7);

    private final int value;

    Quality(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
