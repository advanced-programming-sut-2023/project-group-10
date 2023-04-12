package main.java.view.enums.commands;

public enum ShopMenuCommands {
    SELL(""),
    BUY("");
    public final  String  regex;

    ShopMenuCommands(String regex) {
        this.regex=regex;
    }
}
