package org.example.model;

import com.google.gson.Gson;

import java.util.Objects;
import java.util.Random;

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

    public static User getUserFromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }
}
