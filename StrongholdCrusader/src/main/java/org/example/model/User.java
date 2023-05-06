package org.example.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.utils.CheckFormatAndEncrypt;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private String questionNumber;
    private String questionAnswer;
    private int highScore;
    //I think it should be moved in the game class or whatever
    private static ArrayList<User> users = new ArrayList<>();
    static final Gson gson = new Gson();


    public User(String username, String password, String nickname, String email, String slogan, String questionNumber,
                String securityAnswer) {
        this.username = username;
        this.password = CheckFormatAndEncrypt.encryptString(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.questionNumber = questionNumber;
        this.questionAnswer = CheckFormatAndEncrypt.encryptString(securityAnswer);

    }

    public static void addUser(String username, String password, String nickname, String email, String slogan,
                               String questionNumber, String securityAnswer) {
        Stronghold.dataBase.loadUsersFromFile();
        users.add(new User(username, password, nickname, email, slogan, questionNumber, securityAnswer));
        Stronghold.dataBase.saveUsersToFile();    }



    public static boolean checkSecurityAnswer(String username, String answer) {
        return CheckFormatAndEncrypt.encryptString(answer).equals(getUserByUsername(username).getQuestionAnswer());
    }

    public static void clearUsers() {
        users.clear();
    }

    public static void updateUsers(ArrayList<User> createdUsers) {
        users.addAll(createdUsers);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        Stronghold.dataBase.saveUsersToFile();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        Stronghold.dataBase.saveUsersToFile();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        Stronghold.dataBase.saveUsersToFile();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        Stronghold.dataBase.saveUsersToFile();
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

    public static User getUserByUsername(String username) {
        Stronghold.dataBase.loadUsersFromFile();
        if (users.size() == 0)
            return null;
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static User getUserByEmail(String email) {
        Stronghold.dataBase.loadUsersFromFile();
        if (users.size() == 0)
            return null;
        email = email.toLowerCase();
        for (User user : users) {

            if (user.email.toLowerCase().equals(email))
                return user;
        }
        return null;
    }

    //TODO: discuss the type
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

    public boolean checkPassword(String password) {
        return this.password.equals(CheckFormatAndEncrypt.encryptString(password));
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getRank() {
        return 0;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
