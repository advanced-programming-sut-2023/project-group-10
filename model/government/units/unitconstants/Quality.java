package model.government.units.unitconstants;

public enum Quality {
    EXTREMELY_LOW(1),
    VERY_LOW(2),
    LOW(3),
    AVERAGE(4),
    HIGH(5),
    VERY_HIGH(6);

    private final int value;

    Quality(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
