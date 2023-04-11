package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private HashMap<String, String> passwordRecoveryQuestionAndAnswer;
    private String securityQuestionNumber;
    //I think it should be moved in the game class or whatever
    private final static ArrayList<User> users = new ArrayList<>();

    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
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
    public User getUserByUsername(String username){
        if(users.size()==0)
            return null;
        for (User user : users) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }


    //TODO: discuss the type
    public void setSecurityQuestion(String number, String answer) {
       passwordRecoveryQuestionAndAnswer.put( SecurityQuestion.getQuestionByNumber(number),answer);
       securityQuestionNumber=number;
    }
    public String getSecurityAnswer(){
        return passwordRecoveryQuestionAndAnswer.get(securityQuestionNumber);
    }
    //TODO: Move it to register menu controller


}
