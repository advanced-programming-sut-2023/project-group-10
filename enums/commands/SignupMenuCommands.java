package view.enums.commands;

public enum SignupMenuCommands {
    CREATE_USER(""),
    PICK_QUESTION("");

    public final String regex;

    SignupMenuCommands(String regex) {
        this.regex=regex;
    }

}
