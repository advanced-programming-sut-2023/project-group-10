package org.example.controller;

import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.SignupMenuMessages;

public class SignupMenuController {
    private static void createUser(String securityQuestionNumber, String securityAnswer,
                                   String username, String password, String nickname, String slogan, String email) {
        User.addUser(username, password, nickname, email, slogan, securityQuestionNumber, securityAnswer);
    }

    public static SignupMenuMessages createUser(String username, String password, String passwordConfirmation, String email, String nickname) {
        if (CheckFormatAndEncrypt.isUsernameFormatInvalid(username)) return SignupMenuMessages.INVALID_USERNAME_FORMAT;
        if (!CheckFormatAndEncrypt.checkNicknameFormat(nickname)) return SignupMenuMessages.INVALID_NICKNAME_FORMAT;
        if (User.getUserByUsername(username) != null) return SignupMenuMessages.USER_EXISTS;
        if (!password.equals("random") && CheckFormatAndEncrypt.isPasswordWeak(password))
            return SignupMenuMessages.WEAK_PASSWORD;
        if (!password.equals("random") && !password.equals(passwordConfirmation))
            return SignupMenuMessages.WRONG_PASSWORD_CONFIRMATION;
        if (User.getUserByEmail(email) != null) return SignupMenuMessages.EMAIL_EXISTS;
        if (CheckFormatAndEncrypt.isEmailFormatInvalid(email)) return SignupMenuMessages.INVALID_EMAIL_FORMAT;
        return SignupMenuMessages.SHOW_QUESTIONS;
    }

    public static String suggestNewUsername(String username) {
        for (int i = 1; ; i++)
            if (User.getUserByUsername(username + i) == null) return username + i;
    }

    public static SignupMenuMessages pickSecurityQuestionAndCreateUser(String questionNumber, String answer, String answerConfirmation,
                                                                       String username, String password, String nickname, String slogan, String email) {
        if (Integer.parseInt(questionNumber) > 3 || Integer.parseInt(questionNumber) < 1)
            return SignupMenuMessages.NUMBER_OUT_OF_BOUNDS;
        if (!answer.equals(answerConfirmation)) return SignupMenuMessages.REENTER_ANSWER;
        createUser(questionNumber, answer, username, password, nickname, slogan, email);
        return SignupMenuMessages.SUCCESS;
    }
}