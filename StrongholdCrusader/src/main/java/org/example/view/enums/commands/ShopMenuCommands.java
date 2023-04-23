package org.example.view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    SELL("^\\s*sell\\s*.+"),
    SHOW_LIST("^\\s*show\\s+price\\s+list\\s*$"),
    BUY("^\\s*buy\\s*.+"), BACK("\\s*back\\s*");
    public final  String  regex;

    ShopMenuCommands(String regex) {
        this.regex=regex;
    }

    public static Matcher getMatcher(String input, ShopMenuCommands command){
        Matcher matcher = Pattern.compile(command.regex).matcher(input);
        if(matcher.matches()) return matcher;
        else return null;
    }
}
