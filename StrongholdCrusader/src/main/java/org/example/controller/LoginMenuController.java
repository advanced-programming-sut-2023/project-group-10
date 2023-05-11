package org.example.controller;

import org.apache.commons.lang3.time.StopWatch;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.LoginMenuMessages;

import java.util.Scanner;

public class LoginMenuController {
    public static LoginMenuMessages login(String username, String password, boolean stayLoggedIn) {
        if (User.getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOESNT_EXIST;
        //TODO: a function for checking password exists in user,Mehrazin would probably modify it, but try to use that function

        loginPassword(username, password);

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
        Stronghold.dataBase.loadUsersFromFile();
        if (!User.checkSecurityAnswer(username, answer))
            return LoginMenuMessages.SECURITY_ANSWER_WRONG;

        LoginMenuMessages message = checkPassword(newPassword);
        if (!message.equals(LoginMenuMessages.STRONG_PASSWORD))
            return message;

        Stronghold.getCurrentUser().setPassword(newPassword);
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

    private static void loginPassword(String username, String password) {
        int wrongPassword = 1;

        while (!User.getUserByUsername(username).checkPassword(password)) {
            System.out.println("Wrong password!");
            System.out.println("You have to wait " + wrongPassword * 5 + " seconds to enter another password!");
            StopWatch watch = new StopWatch();
            watch.start();

            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine();
            long time;

            while ((time = watch.getTime()) < wrongPassword * 5000L) {
                System.out.println("You have to wait " + (wrongPassword * 5 - time / 1000) + " seconds to enter another password!");
                password = scanner.nextLine();
            }
            watch.stop();
            wrongPassword++;
        }
    }
}
