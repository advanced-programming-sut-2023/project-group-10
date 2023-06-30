package org.example.connection;

public enum ClientToServerCommands {
    //sign up menu
    GET_DEFAULT_SLOGANS("get default slogans"),
    LIVE_CHECK_USERNAME("live check username", "username"),
    LIVE_CHECK_PASSWORD("live check password", "password"),
    LIVE_CHECK_PASSWORD_CONFIRMATION("live check password confirmation", "password", "password confirmation"),
    RANDOM_PASSWORD("random password"),
    CHECK_SIGNUP_INFO("check sign up info", "username", "password", "password confirmation", "nickname", "email"),
    GET_SECURITY_QUESTIONS("get security questions"),
    GET_CAPTCHA("get captcha"),
    COMPLETE_SIGNUP("complete signup", "username", "password", "nickname", "email", "slogan", "question number", "answer"),
    //login menu
    GET_SECURITY_QUESTION("get security question", "username"),
    TRY_TO_CHANGE_PASSWORD("try to change password", "username", "answer", "new password"),
    LOG_IN("log in", "username", "password"),

    //profile menu
    CHANGE_USERNAME("change username", "new username"),
    CHANGE_PASSWORD("change password", "current password", "new password"),
    CHANGE_NICKNAME("change nickname", "new nickname"),
    CHANGE_EMAIL("change email", "new email"),
    //this should be Handled in another manner/did not figure it out //TODO
    CHANGE_SLOGAN("change slogan", "slogan"),
    CHANGE_AVATAR("change avatar", "new avatar path"),

    //start game menu
    START_NEW_GAME("start game", "players", "keeps x", "keeps y", "colors"),
    CANCEL_START_GAME("cancel start game");
    //customize map


    private final String command;
    private final String[] attributes;

    ClientToServerCommands(String command, String... attributes) {
        this.command = command;
        this.attributes = attributes;
    }

    public String getCommand() {
        return command;
    }

    public static ClientToServerCommands getCommandByString(String command) {
        for (ClientToServerCommands value : values())
            if (value.command.equals(command)) return value;
        return null;
    }
}
