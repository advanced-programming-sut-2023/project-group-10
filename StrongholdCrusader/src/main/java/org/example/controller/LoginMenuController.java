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

        LoginMenuMessages message = checkPassword(newPassword);
        if(!message.equals(LoginMenuMessages.STRONG_PASSWORD))
            return message;

        Stronghold.getCurrentUser().setPassword(newPassword);
        User.saveUsersToFile();
        return LoginMenuMessages.CHANGE_PASSWORD_SUCCESSFUL;
    }

    private static LoginMenuMessages checkPassword (String newPassword){
        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("short password"))
            return LoginMenuMessages.SHORT_PASSWORD;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no lowercase letter"))
            return LoginMenuMessages.NO_LOWERCASE_LETTER;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no uppercase letter"))
            return LoginMenuMessages.NO_UPPERCASE_LETTER;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no number"))
            return LoginMenuMessages.NO_NUMBER;

        if(CheckFormatAndEncrypt.isPasswordWeak(newPassword).equals("no special character"))
            return LoginMenuMessages.NO_SPECIAL_CHARACTER;

        else return LoginMenuMessages.STRONG_PASSWORD;
    }
}
