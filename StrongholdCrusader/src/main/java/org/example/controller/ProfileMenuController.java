package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ProfileMenuController {
    public static ProfileMenuMessages changeUsername(String username, User currentUser) {
        if (username == null || username.matches("\\s*") || username.length() == 0)
            return ProfileMenuMessages.NO_USERNAME_PROVIDED;

        else if (CheckFormatAndEncrypt.isUsernameFormatInvalid(username))
            return ProfileMenuMessages.INVALID_USERNAME;

        else if (username.equals(currentUser.getUsername()))
            return ProfileMenuMessages.OLD_USERNAME_ENTERED;

        else if (User.getUserByUsername(username) != null)
            return ProfileMenuMessages.USERNAME_EXISTS;

        else {
            if (Stronghold.getLoggedInUserFromFile() != null &&
                    Stronghold.getLoggedInUserFromFile().getUsername().equals(currentUser.getUsername())) {
                currentUser.setUsername(username);
                Stronghold.dataBase.saveUsersToFile();
                Stronghold.addUserToFile(currentUser);
            } else {
                currentUser.setUsername(username);
                Stronghold.dataBase.saveUsersToFile();
            }
            return ProfileMenuMessages.CHANGE_USERNAME_SUCCESSFUL;
        }
    }

    public static void changeNickname(String nickname, User currentUser) {
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(currentUser.getUsername())) {
            currentUser.setNickname(nickname);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(currentUser);
        } else {
            currentUser.setNickname(nickname);
            Stronghold.dataBase.saveUsersToFile();
        }
    }

    public static ProfileMenuMessages changePassword(String oldPassword, String newPassword, User currentUser) {
        if (oldPassword.matches("\\s*") || newPassword.matches("\\s*"))
            return ProfileMenuMessages.NO_PASSWORD_PROVIDED;

        ProfileMenuMessages message = checkPassword(newPassword);
        if (!message.equals(ProfileMenuMessages.STRONG_PASSWORD))
            return message;

        if (!currentUser.getPassword().equals(CheckFormatAndEncrypt.encryptString(oldPassword)))
            return ProfileMenuMessages.INCORRECT_PASSWORD;

        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(currentUser.getUsername())) {
            currentUser.setPassword(newPassword);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(currentUser);
        } else {
            currentUser.setPassword(newPassword);
            Stronghold.dataBase.saveUsersToFile();
        }

        return ProfileMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }

    public static ProfileMenuMessages changeEmail(String email, User currentUser) {
        if (User.getUserByEmail(email) != null)
            return ProfileMenuMessages.EMAIL_EXISTS;
        currentUser.setEmail(email);
        Stronghold.dataBase.saveUsersToFile();
        Stronghold.addUserToFile(currentUser);
        return ProfileMenuMessages.CHANGE_EMAIL_SUCCESSFUL;

    }

    public static void changeSlogan(String slogan, User currentUser) {
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(currentUser.getUsername())) {
            currentUser.setSlogan(slogan);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(currentUser);
        } else {
            currentUser.setSlogan(slogan);
            Stronghold.dataBase.saveUsersToFile();
        }
    }

    public static void changeAvatar(String path, User currentUser) {
        if (Stronghold.getLoggedInUserFromFile() != null &&
                Stronghold.getLoggedInUserFromFile().getUsername().equals(currentUser.getUsername())) {
            currentUser.setAvatar(path);
            Stronghold.dataBase.saveUsersToFile();
            Stronghold.addUserToFile(currentUser);
        } else {
            currentUser.setAvatar(path);
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

    public static User searchUsers (String search) {
        Stronghold.dataBase.saveUsersToFile();
        for(User user : User.getUsers()){
            if(user.getUsername().equals(search))
                return user;
        }
        return null;
    }

    public static ArrayList<User> getFriends (String username) {
        return User.getUserByUsername(username).getFriends();
    }

    public static ArrayList<User> getPendingFriends (String username) {
        return User.getUserByUsername(username).getPendingFriends();
    }

    public static String requestFriend (String owner, String requester) {
        if(User.getUserByUsername(requester).getFriends().size() == 100)
            return "no storage";
        else{
            User ownerUser = User.getUserByUsername(owner);
            ownerUser.addPending(requester);
            System.out.println(ownerUser.getPendingFriends().get(0));
            System.out.println(ownerUser.getPendingFriends().size());
            for(User user : User.getUsers()){
                System.out.println(user.getPendingFriends().size());
                if(user.equals(ownerUser)) System.out.println("yes");
            }
            return "successful";
        }
    }
}
