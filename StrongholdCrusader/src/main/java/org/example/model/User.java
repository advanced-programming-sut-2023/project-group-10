package org.example.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.checkerframework.checker.units.qual.A;

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
    private static final Gson gson = new Gson();


    public User(String username, String password, String nickname, String email,String slogan) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan=slogan;
    }
    public static void addUser(String username, String password, String nickname, String email, String slogan){
        users.add(new User(username,password,nickname,email,slogan));
    }

    public static void loadUsersFromFile() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/UserDatabase.json")));
            ArrayList<User> createdUsers;
            createdUsers = gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());
            if (createdUsers != null) {
                users = createdUsers;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsersToFile() {
        try {
        FileWriter fileWriter = new FileWriter("./src/main/resources/UserDatabase.json");
        fileWriter.write(gson.toJson(users));
        fileWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
        if (users.size() == 0)
            return null;
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    //TODO: discuss the type
    public void setSecurityQuestion(String number, String answer) {
        questionNumber=number;
        questionAnswer=answer;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
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
}
