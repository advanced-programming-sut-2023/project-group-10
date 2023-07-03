package org.example.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private String questionNumber;
    private String questionAnswer;
    private String avatar;
    private int highScore;
    private boolean isOnline;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getAvatar() {
        return Objects.requireNonNull(User.class.getResource(this.avatar)).toString();
    }

    public int getHighScore() {
        return highScore;
    }

    public void setSecurityQuestion(String number, String answer) {
        questionNumber = number;
        questionAnswer = answer;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static User getUserFromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public static ArrayList getSortedUsersFromJson(String json) {
        return new Gson().fromJson(json, ArrayList.class);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) return false;
        return ((User) obj).getUsername().equals(username);
    }
}
