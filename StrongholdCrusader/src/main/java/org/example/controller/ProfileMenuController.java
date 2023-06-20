package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.util.Scanner;

public class ProfileMenuController {
    public static ProfileMenuMessages changeUsername(String username) {
        if (username == null || username.matches("\\s*") || username.length() == 0)
            return ProfileMenuMessages.NO_USERNAME_PROVIDED;

        else if (CheckFormatAndEncrypt.isUsernameFormatInvalid(username))
            return ProfileMenuMessages.INVALID_USERNAME;

        else if (username.equals(Stronghold.getCurrentUser().getUsername()))
            return ProfileMenuMessages.OLD_USERNAME_ENTERED;

        else if (User.getUserByUsername(username) != null)
            return ProfileMenuMessages.USERNAME_EXISTS;

        else {
            if (Stronghold.getLoggedInUserFromFile() != null &&
                    Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername())) {
                Stronghold.getCurrentUser().setUsername(username);
                Stronghold.dataBase.saveUsersToFile();
                Stronghold.addUserToFile(Stronghold.getCurrentUser());
            } else {
                Stronghold.getCurrentUser().setUsername(username);
                Stronghold.dataBase.saveUsersToFile();
            }
            return ProfileMenuMessages.CHANGE_USERNAME_SUCCESSFUL;
        }
    }

    public static void changeNickname(String nickname) {
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername())) {
            Stronghold.getCurrentUser().setNickname(nickname);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(Stronghold.getCurrentUser());
        } else {
            Stronghold.getCurrentUser().setNickname(nickname);
            Stronghold.dataBase.saveUsersToFile();
        }
    }

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword) {

        if (oldPassword.matches("\\s*") || newPassword.matches("\\s*"))
            return ProfileMenuMessages.NO_PASSWORD_PROVIDED;

        ProfileMenuMessages message = checkPassword(newPassword);
        if (!message.equals(ProfileMenuMessages.STRONG_PASSWORD))
            return message;

        if (!Stronghold.getCurrentUser().getPassword().equals(oldPassword))
            return ProfileMenuMessages.INCORRECT_PASSWORD;

        while (oldPassword.equals(newPassword)) {
            System.out.println("Please enter a new password!");
            newPassword = new Scanner(System.in).nextLine();
        }
        if (Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername())) {
            Stronghold.getCurrentUser().setPassword(newPassword);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(Stronghold.getCurrentUser());
        } else {
            Stronghold.getCurrentUser().setPassword(newPassword);
            Stronghold.dataBase.saveUsersToFile();
        }

        return ProfileMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }

    public static void changeEmail(String email) {
        if (Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername())) {
            Stronghold.getCurrentUser().setEmail(email);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(Stronghold.getCurrentUser());
        } else {
            Stronghold.getCurrentUser().setEmail(email);
            Stronghold.dataBase.saveUsersToFile();

        }
    }

    public static void changeSlogan(String slogan) {
        if (Stronghold.getLoggedInUserFromFile().getUsername().equals(Stronghold.getCurrentUser().getUsername())) {
            Stronghold.getCurrentUser().setSlogan(slogan);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(Stronghold.getCurrentUser());
        } else {
            Stronghold.getCurrentUser().setSlogan(slogan);
            Stronghold.dataBase.saveUsersToFile();

        }
    }

    private static ProfileMenuMessages checkPassword(String newPassword) {
        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("short password"))
            return ProfileMenuMessages.SHORT_PASSWORD;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no lowercase letter"))
            return ProfileMenuMessages.NO_LOWERCASE_LETTER;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no uppercase letter"))
            return ProfileMenuMessages.NO_UPPERCASE_LETTER;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no number"))
            return ProfileMenuMessages.NO_NUMBER;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no special character"))
            return ProfileMenuMessages.NO_SPECIAL_CHARACTER;

        else return ProfileMenuMessages.STRONG_PASSWORD;
    }
}
