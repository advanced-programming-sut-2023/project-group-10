package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignupMenuCommands {
    CREATE_USER("^\\s*user\\s+create.+"),
    USER_LOGIN("^\\s*user\\s+login");

    public final String regex;

    SignupMenuCommands(String regex) {
        this.regex=regex;
    }

    public static Matcher getMatcher(String input, SignupMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
