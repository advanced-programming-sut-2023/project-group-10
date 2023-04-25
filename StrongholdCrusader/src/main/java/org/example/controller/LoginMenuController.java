package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.LoginMenuMessages;

public class LoginMenuController {
    public static LoginMenuMessages login(String username, String password, boolean stayLoggedIn) {
        if (User.getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_NOT_EXIST;
        //TODO: a function for checking password exists in user,Mehrazin would probably modifies it, but try to use that function
        //modified by Mehrazin
        if (!User.getUserByUsername(username).checkPassword(password))
            return LoginMenuMessages.WRONG_PASSWORD;
        // not sure about it TODO: check
        if (stayLoggedIn)
            Stronghold.addUserToFile(User.getUserByUsername(username));
        else
            Stronghold.addUserToFile(User.getUserByUsername(null));

        //login user
        Stronghold.setCurrentUser(User.getUserByUsername(username));
        return LoginMenuMessages.LOGIN_SUCCESSFUL;
    }

    public static LoginMenuMessages forgetPassword(String username, String answer, String newPassword) {
        if (!User.getUserByUsername(username).getQuestionAnswer().equals(answer))
            return LoginMenuMessages.SECURITY_ANSWER_WRONG;

        if (CheckFormatAndEncrypt.isPasswordWeak(newPassword))
            return LoginMenuMessages.WEAK_PASSWORD;

        Stronghold.getCurrentUser().setPassword(newPassword);
        User.saveUsersToFile();
        return LoginMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }
}
