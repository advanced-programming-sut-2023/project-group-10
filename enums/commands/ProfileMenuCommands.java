package view.enums.commands

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    LOGIN(""),
    FORGET_PASSWORD("");

    private final String regex;

    ProfileMenuCommands(String regex){
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
