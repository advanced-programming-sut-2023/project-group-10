package main.java.view.enums.commands;

public enum ShopMenuCommands {
    SELL(""),
    BUY("");
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
