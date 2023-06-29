package org.example.connection;

public enum ClientToServerCommands {
    //sign up menu
    SIGN_UP("sign up", "username", "password", "password confirmation", "nickname", "email", "slogan"),
    SECURITY_QUESTION("security question", "question number", "answer", "answer confirmation", "username",
            "password", "password confirmation", "nickname", "email", "slogan"),
    GO_TO_LOGIN_MENU("login menu"),
    //login menu
    LOG_IN("log in", "username", "password", "stay logged in"),
    FORGOT_PASSWORD("forgot password", "username", "answer", "password"),
    GO_TO_SIGN_UP_MENU("sign up menu"),
    //main menu
    LOGOUT("logout"),
    GO_TO_PROFILE_MENU("profile menu"),
    START_GAME_MENU("start game menu"),
    GO_TO_CHAT_MENU("chat menu"),
    //profile menu
    GO_TO_MAIN_MENU("main menu"),

    //start game menu
    START_NEW_GAME("start game","players","keeps x","keeps y","colors"),
    CANCEL_START_GAME("cancel start game");
    //customize map




    private final String command;
    private final String[] attributes;

    ClientToServerCommands(String command, String... attributes) {
        this.command = command;
        this.attributes = attributes;
    }

    }
