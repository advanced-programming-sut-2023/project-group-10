package org.example.connection;

public enum ServerToClientCommands {
    UNAUTHORIZED_REQUEST("unauthorized request"),
    CONNECTION_TIMED_OUT("connection timed out"),
    //sign up menu
    DEFAULT_SLOGANS("default slogans", "message"),
    USERNAME_CHECK("username check", "message"),
    PASSWORD_CHECK("password check", "message"),
    PASSWORD_CONFIRMATION_CHECK("password confirmation check", "message"),
    EMAIL_CHECK("email check", "message"),
    NICKNAME_CHECK("nickname check", "message"),
    RANDOM_PASSWORD("random password", "password"),
    RANDOM_SLOGAN("random slogan", "slogan"),
    CAN_GO_TO_SECURITY_QUESTIONS("can go to security questions"),
    SECURITY_QUESTIONS("security questions", "message"),
    GET_CAPTCHA("get captcha", "number"),
    SUCCESSFUL_SIGNUP("successful sign up"),
    INITIALIZE_COMPLETE("initialize complete"),
    SEND_LOGGED_IN_USER("send logged in user", "user object"),
    //login menu
    GET_SECURITY_QUESTION("get security question", "is username valid", "message"),
    TRY_TO_CHANGE_PASSWORD("try to change password", "is successful"),
    LOGIN("login", "user object", "message"),
    //main menu
    LOGGED_OUT("log out"),
    START_GAME("start game", "message"),
    //profile menu
    FAILED_CHANGE("failed change", "message"),
    SUCCESSFUL_CHANGE("successful change", "message"),
    SORTED_USERS("sorted users", "array list"),
    CONNECTION("connection", "state"),
    ALL_CONNECTIONS("all connections", "array list"),
    LAST_VISIT("last visit", "time"),
    GET_SEARCHED_USERS("get searched users", "users"),
    FRIENDS_LIST("friends list", "friends"),
    //chat menu
    CAN_CREATE_CHAT("can create chat", "state"),
    GET_CHAT_LIST("get chat list", "chats"),
    GET_CHAT_MESSAGES("get chat messages", "messages"),
    AUTO_UPDATE_CHAT_MESSAGES("auto update chat messages", "messages"),
    IS_ADMIN("is admin", "state"),
    CAN_UPDATE_MESSAGE("can update message", "state"),
    ADD_MEMBER_RESULT("add member result", "message"),
    //lobby
    GROUP_CREATED("group created", "group object"),
    GROUP_LIST_REFRESHED("group list refreshed", "groups"),
    GROUP_INFO_REFRESHED("group info refreshed", "group object"),
    GROUP_TIMED_OUT("group timed out", "group object"),
    Group_IS_COMPLETE("group is complete");

    private final String command;
    private final String[] attributes;

    ServerToClientCommands(String command, String... attributes) {
        this.command = command;
        this.attributes = attributes;
    }

    public String getCommand() {
        return command;
    }

    public static ServerToClientCommands getCommandByString(String command) {
        for (ServerToClientCommands value : values())
            if (value.command.equals(command)) return value;
        return null;
    }
}
