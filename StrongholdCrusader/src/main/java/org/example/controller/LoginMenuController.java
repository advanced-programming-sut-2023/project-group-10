package org.example.controller;

import org.example.model.User;
import org.example.view.enums.messages.LoginMenuMessages;

public class LoginMenuController {
    public static LoginMenuMessages login(String username, String password){
        if(User.getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_NOT_EXIST;

        if(!User.getUserByUsername(username).getPassword().equals(password))
            return LoginMenuMessages.WRONG_PASSWORD;

        //login user
        return null;
    }

    public static LoginMenuMessages forgetPassword(String username, String answer){
        if(!User.getUserByUsername(username).getQuestionAnswer().equals(answer))
            return LoginMenuMessages.SECURITY_ANSWER_WRONG;

        else return null;
    }
}