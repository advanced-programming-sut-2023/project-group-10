package org.example.view;


import org.example.model.User;

// TODO: move to client or remove if not needed?
public class DataBank {
    private static User loggedInUser;
    private static String username;
    private static String password;
    private static String nickname;
    private static String email;
    private static String slogan;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DataBank.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataBank.password = password;
    }

    public static String getNickname() {
        return nickname;
    }

    public static void setNickname(String nickname) {
        DataBank.nickname = nickname;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        DataBank.email = email;
    }

    public static String getSlogan() {
        return slogan;
    }

    public static void setSlogan(String slogan) {
        DataBank.slogan = slogan;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }


}
