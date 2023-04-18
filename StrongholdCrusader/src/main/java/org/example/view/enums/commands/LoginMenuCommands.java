package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    LOGIN(""),
    FORGET_PASSWORD(""),
    LOGOUT("");

    private final String regex;

    LoginMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, LoginMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
