package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.LoginMenuMessages;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.util.Scanner;

//TODO all functions needed to be checked with Mehrazin

public class ProfileMenuController {
    public static ProfileMenuMessages changeUsername(String username) {
        if (username.matches("\\s*"))
            return ProfileMenuMessages.NO_USERNAME_PROVIDED;

        if (CheckFormatAndEncrypt.isUsernameFormatInvalid(username))
            return ProfileMenuMessages.INVALID_USERNAME;

        if (User.getUserByUsername(username) != null)
            return ProfileMenuMessages.USERNAME_EXISTS;

        else {
            Stronghold.getCurrentUser().setUsername(username);
            User.saveUsersToFile();
            return ProfileMenuMessages.CHANGE_USERNAME_SUCCESSFUL;
        }
    }

    public static ProfileMenuMessages changeNickname(String nickname) {
        if (nickname.matches("\\s*"))
            return ProfileMenuMessages.NO_NICKNAME_PROVIDED;

        if (!CheckFormatAndEncrypt.checkNicknameFormat(nickname))
            return ProfileMenuMessages.INVALID_NICKNAME;

        else {
            Stronghold.getCurrentUser().setNickname(nickname);
            return ProfileMenuMessages.CHANGE_NICKNAME_SUCCESSFUL;
        }
    }

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword) {
        //TODO add incorrect captcha

        if (oldPassword.matches("\\s*") || newPassword.matches("\\s*"))
            return ProfileMenuMessages.NO_PASSWORD_PROVIDED;

        ProfileMenuMessages message = checkPassword(newPassword);
        if(!message.equals(ProfileMenuMessages.STRONG_PASSWORD))
            return message;

        if (!Stronghold.getCurrentUser().getPassword().equals(oldPassword))
            return ProfileMenuMessages.INCORRECT_PASSWORD; //TODO check with Mehrazin

        while (oldPassword.equals(newPassword)) {
            System.out.println("Please enter a new password!");
            newPassword = new Scanner(System.in).nextLine();
        }

        Stronghold.getCurrentUser().setPassword(newPassword);
        User.saveUsersToFile();
        return ProfileMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }

    public static ProfileMenuMessages changeEmail(String email) {
        if (email.matches("\\s*"))
            return ProfileMenuMessages.NO_EMAIL_PROVIDED;

        if (CheckFormatAndEncrypt.isEmailFormatInvalid(email))
            return ProfileMenuMessages.INVALID_EMAIL;

        if (User.getUserByEmail(email) != null)
            return ProfileMenuMessages.EMAIL_EXISTS;

        else {
            Stronghold.getCurrentUser().setEmail(email);
            User.saveUsersToFile();
            return ProfileMenuMessages.CHANGE_EMAIL_SUCCESSFUL;
        }
    }

    public static ProfileMenuMessages changeSlogan(String slogan) {
        if (slogan.matches("\\s*"))
            return ProfileMenuMessages.NO_SLOGAN_PROVIDED;

        else {
            Stronghold.getCurrentUser().setSlogan(slogan);
            User.saveUsersToFile();
            return ProfileMenuMessages.CHANGE_SLOGAN_SUCCESSFUL;
        }
    }

    private static ProfileMenuMessages checkPassword (String newPassword){
        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("short password"))
            return ProfileMenuMessages.SHORT_PASSWORD;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no lowercase letter"))
            return ProfileMenuMessages.NO_LOWERCASE_LETTER;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no uppercase letter"))
            return ProfileMenuMessages.NO_UPPERCASE_LETTER;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no number"))
            return ProfileMenuMessages.NO_NUMBER;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no special character"))
            return ProfileMenuMessages.NO_SPECIAL_CHARACTER;

        else return ProfileMenuMessages.STRONG_PASSWORD;
    }
}
