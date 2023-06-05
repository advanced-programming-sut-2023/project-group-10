package org.example.view;

public class DataBank {
    private static String username;
    private static String password;
    private static String nickname;
    private static String email;
    private static String slogan;

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getEmail() {
        return email;
    }

    public static String getSlogan() {
        return slogan;
    }

    public static void setUsername(String username) {
        DataBank.username = username;
    }

    public static void setPassword(String password) {
        DataBank.password = password;
    }

    public static void setNickname(String nickname) {
        DataBank.nickname = nickname;
    }

    public static void setEmail(String email) {
        DataBank.email = email;
    }

    public static void setSlogan(String slogan) {
        DataBank.slogan = slogan;
    }
}
