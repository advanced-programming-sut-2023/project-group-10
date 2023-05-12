package org.example.model;

import com.google.gson.Gson;
import org.example.model.utils.CheckFormatAndEncrypt;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private String questionNumber;
    private String questionAnswer;
    private int highScore;
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
        Stronghold.dataBase.saveUsersToFile();
    }


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
        if (this.highScore <= highScore)
            this.highScore = highScore;
        Stronghold.dataBase.saveUsersToFile();
    }

    public int getRank() {
        ArrayList<User> sorted = sortUsers();
        for(int i = 0; i < sorted.size(); i++){
            if(sorted.get(i).equals(this))
                return i+1;
        }
        return 0;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    private static ArrayList<User> sortUsers() {
        Stronghold.dataBase.loadUsersFromFile();
        ArrayList<User> toBeSorted = new ArrayList<>();
        Collections.copy(toBeSorted, users);

        for(int i = 0; i < toBeSorted.size()-1; i++){
            for(int j = 1; j < toBeSorted.size()-i-1; j++){
                if(toBeSorted.get(j).getHighScore() < toBeSorted.get(j+1).getHighScore())
                    Collections.swap(toBeSorted, j, j+1);
                else if(toBeSorted.get(j).getHighScore() == toBeSorted.get(j+1).getHighScore()){
                    if(toBeSorted.get(j).getNickname().compareTo(toBeSorted.get(j+1).getNickname()) > 0)
                        Collections.swap(toBeSorted, j, j+1);
                }
            }
        }
        return toBeSorted;
    }
}
