package model;

public enum NumericalEnums {
    INITIAL_POPULARITY_VALUE(100);
    private final int value;

    NumericalEnums(int value) {
        this.value=value;
    }
    public int getValue(){
        return value;
    }
}
