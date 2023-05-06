package org.example.view;

import org.example.controller.LoginMenuController;
import org.example.model.utils.CaptchaGenerator;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.LoginMenuCommands;
import org.example.view.enums.messages.LoginMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginMenu {
    Scanner scanner = new Scanner(System.in);

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN) != null)
                login(command);

            else if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGET_PASSWORD) != null)
                forgetPassword(command);

            else if (LoginMenuCommands.getMatcher(command, LoginMenuCommands.BACK) != null)
                return;

            else
                System.out.println("invalid command");
        }
    }

    private static void login(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String username = "";
        String password = "";
        boolean stayLoggedIn = false;

        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "--stay-logged-in":
                    stayLoggedIn=true;
                    break;
                case "-u":
                    username = option.getValue();
                    break;

                case "-p":
                    password = option.getValue();
                    break;

                default:
                    System.out.println("invalid option");
                    return;
            }
        }

        LoginMenuMessages message = LoginMenuController.login(username, password, stayLoggedIn);

        switch (message) {
            case USERNAME_DOESNT_EXIST:
                System.out.println("Username does not exist!");
                break;

            case WRONG_PASSWORD:
                System.out.println("Username and password didn't march!");
                break;

            case LOGIN_SUCCESSFUL:
                CaptchaGenerator.run();
                System.out.println("User logged in successfully!");
                MainMenu.run();
                break;
        }
    }

    private static void forgetPassword(String input) {
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String username = "";
        String newPassword = scanner.nextLine();

        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-u")) {
                username = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }

        LoginMenuMessages message = LoginMenuController.forgetPassword(username, answer, newPassword);

        switch (message) {
            case SECURITY_ANSWER_WRONG:
                System.out.println("Wrong answer!");
                break;

            case SHORT_PASSWORD:
                System.out.println("Short new password!\nYou must provide at least 6 characters!");
                break;

            case NO_LOWERCASE_LETTER:
                System.out.println("Your password must contain a lowercase letter!");
                break;

            case NO_UPPERCASE_LETTER:
                System.out.println("Your password must contain an uppercase letter!");
                break;

            case NO_NUMBER:
                System.out.println("Your password must contain at least one digit!");
                break;

            case NO_SPECIAL_CHARACTER:
                System.out.println("Your password must contain at least one special character!");
                break;

            default:
                System.out.println("Password changed successfully!");
                break;
        }
    }
}
