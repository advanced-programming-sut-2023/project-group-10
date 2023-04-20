package org.example.controller;

import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.SignupMenuMessages;

public class SignupMenuController {
    public static SignupMenuMessages createUser(String username, String password, String passwordConfirmation, String email,
                                                String nickname, String slogan) {
       ;


        return null;
    }

    private static SignupMenuMessages createUserErrors(String username, String password, String passwordConfirmation, String email,
                                                String nickname, String slogan){
        if (username == null || password == null || email == null || nickname == null)
            return SignupMenuMessages.EMPTY_FIELD;
        if (!password.equals("random") && passwordConfirmation == null)
            return SignupMenuMessages.EMPTY_FIELD;
        //TODO: handle empty slogan
        if(!CheckFormatAndEncrypt.isUsernameFormatValid(username))
            return SignupMenuMessages.INVALID_USERNAME_FORMAT;
        if(User.getUserByUsername(username) != null)
            return SignupMenuMessages.USER_EXISTS;
        if(password.equals("random"))
            return SignupMenuMessages.RANDOM_PASSWORD;
        else if(!CheckFormatAndEncrypt.isPasswordStrong(password))
            return SignupMenuMessages.WEAK_PASSWORD;
        if(!password.equals(passwordConfirmation))
            return SignupMenuMessages.INCORRECT_PASSWORD_CONFIRM;
        if(User.getUserByEmail(email) != null)
            return SignupMenuMessages.EMAIL_EXISTS;
        if(!CheckFormatAndEncrypt.isEmailFormatValid(email))
            return SignupMenuMessages.INVALID_EMAIL_FORMAT;
        return SignupMenuMessages.SHOW_QUESTIONS;
    }
    //TODO: does it work properly?

    public static String suggestNewUsername(String username) {
        for (int i=1;i<=User.getUsers().size();i++){
            if(User.getUserByUsername(username+(i)) == null){
                username=username.concat(Integer.toString(i));
            }
        }
        return null;
    }

    public static String showQuestions() {
        return null;
    }

    public static SignupMenuMessages pickSecurityQuestion(String questionNumber, String answer, String answerConfirmation) {
        return null;
    }
}
