package org.example.view.enums.commands;

public enum MainMenuCommands {
    LOGOUT("logout"),
    START_GAME(""),
    PROFILE_MENU("");
    public final String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }
}
