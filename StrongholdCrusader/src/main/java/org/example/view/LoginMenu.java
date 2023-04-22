package org.example.view;

import org.example.controller.LoginMenuController;
import org.example.view.enums.commands.LoginMenuCommands;
import org.example.view.enums.messages.LoginMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    Scanner scanner = new Scanner(System.in);
    public static void run(){
        Scanner scanner = new Scanner(System.in);
        Matcher matcher;

        while(true){
            String command = scanner.nextLine();

            if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN)) != null)
                login(matcher);
        }
    }

    private static void login(Matcher matcher){
        String username = matcher.group("username");
        String password = matcher.group("password");
        boolean stayLoggedIn=false;

        LoginMenuMessages message = LoginMenuController.login(username, password,stayLoggedIn);

        switch (message){
            case USERNAME_NOT_EXIST:
                System.out.println("Username does not exist!");
                break;

            case WRONG_PASSWORD:
                System.out.println("Username and password didn't march!");
                break;

            case LOGIN_SUCCESSFUL:
                System.out.println("User logged in successfully!");
                break;
        }
    }

    private static void forgetPassword(Matcher matcher){
        Scanner scanner = new Scanner(System.in);
        String username = matcher.group("username");
        String answer = scanner.nextLine();

        LoginMenuMessages message = LoginMenuController.forgetPassword(username, answer);

        switch (message){
            case SECURITY_ANSWER_WRONG:
                System.out.println("Wrong answer!");
                break;

            case SECURITY_ANSWER_CORRECT:
                System.out.println("You can now set a new password!");
                break;
        }
    }

    private static void goToRegisterMenu(){

    }
}
