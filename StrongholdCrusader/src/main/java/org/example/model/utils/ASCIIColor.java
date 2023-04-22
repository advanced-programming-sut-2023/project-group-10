package org.example.model.utils;

public enum ASCIIColor {
    RESET("\033[0m"),
    BLACK_BACKGROUND("\033[40m"),
    RED_BACKGROUND("\033[41m"),
    GREEN_BACKGROUND("\033[42m"),
    YELLOW_BACKGROUND("\033[43m"),
    BLUE_BACKGROUND("\033[44m"),
    PURPLE_BACKGROUND("\033[45m"),
    CYAN_BACKGROUND("\033[46m"),
    WHITE_BACKGROUND("\033[47m");

    private final String code;

    ASCIIColor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
