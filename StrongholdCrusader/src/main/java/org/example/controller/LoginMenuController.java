package org.example.controller;

import org.example.connection.Connection;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.LoginMenuMessages;

public class LoginMenuController {
    public static LoginMenuMessages login(String username, String password, boolean stayLoggedIn) {
        if (User.getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;

        if (!User.getUserByUsername(username).checkPassword(password))
            return LoginMenuMessages.WRONG_PASSWORD;

        if (stayLoggedIn) {
            Stronghold.addUserToFile(User.getUserByUsername(username));
        }
        else
            Stronghold.addUserToFile(User.getUserByUsername(null));

        Stronghold.setCurrentUser(User.getUserByUsername(username));
        return LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    public static LoginMenuMessages login(String username, String password) {
        if (User.getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;

        if (!User.getUserByUsername(username).checkPassword(password))
            return LoginMenuMessages.WRONG_PASSWORD;
        return LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    public static LoginMenuMessages forgetPassword(String username, String answer, String newPassword) {
        Stronghold.dataBase.loadUsersFromFile();
        if (!User.checkSecurityAnswer(username, answer))
            return LoginMenuMessages.SECURITY_ANSWER_WRONG;

        LoginMenuMessages message = checkPassword(newPassword);
        if (!message.equals(LoginMenuMessages.STRONG_PASSWORD))
            return message;

        User.getUserByUsername(username).setPassword(newPassword);
        Stronghold.dataBase.saveUsersToFile();
        return LoginMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }

    private static LoginMenuMessages checkPassword(String newPassword) {
        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("short password"))
            return LoginMenuMessages.SHORT_PASSWORD;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no lowercase letter"))
            return LoginMenuMessages.NO_LOWERCASE_LETTER;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no uppercase letter"))
            return LoginMenuMessages.NO_UPPERCASE_LETTER;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no number"))
            return LoginMenuMessages.NO_NUMBER;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no special character"))
            return LoginMenuMessages.NO_SPECIAL_CHARACTER;

        else return LoginMenuMessages.STRONG_PASSWORD;
    }
}
