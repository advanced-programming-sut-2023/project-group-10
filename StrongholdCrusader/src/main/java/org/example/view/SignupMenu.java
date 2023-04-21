package org.example.view;

import org.example.controller.SignupMenuController;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.commands.SignupMenuCommands;
import org.example.view.enums.messages.SignupMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        Matcher matcher;
        while (true) {
            if ((matcher = SignupMenuCommands.getMatcher(scanner.nextLine(), SignupMenuCommands.CREATE_USER)) != null)
                register(matcher);
            else if ((matcher = SignupMenuCommands.getMatcher(scanner.nextLine(), SignupMenuCommands.USER_LOGIN)) != null) {
                //TODO:Run login menu?
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void register(Matcher matcher) {
        Scanner scanner = new Scanner(System.in);
        String username = "", password = "", passwordConfirmation = "", nickname = "", slogan = "", email = "", questionNumber = "", answer = "", answerConfirmation = "";
        while (true) {
            SignupMenuMessages registerMessage = SignupMenuController.createUser(username, password, passwordConfirmation, email, nickname, slogan);
            if (registerMessage.equals(SignupMenuMessages.RANDOM_SLOGAN)) {
                slogan = RandomGenerator.getRandomSlogan();
                System.out.println(slogan);
            } else if (registerMessage.equals(SignupMenuMessages.RANDOM_PASSWORD)) {
                String suggestedPassword = RandomGenerator.generateSecurePassword();
                System.out.println("Your random password is: " + suggestedPassword);
                System.out.println("Please re-enter your password here:");
                String passwordByUser = scanner.nextLine();
                if (passwordByUser.equals(suggestedPassword))
                    password = suggestedPassword;
                else {
                    System.out.println("You've entered the suggested password incorrectly, try to signup again");
                    return;
                }
            } else if (registerMessage.equals(SignupMenuMessages.EMPTY_FIELD)) {
                System.out.println("Empty field!");
                return;
            } else if (registerMessage.equals(SignupMenuMessages.INVALID_USERNAME_FORMAT)) {
                System.out.println("Invalid format for username");
                return;
            } else if (registerMessage.equals(SignupMenuMessages.INVALID_EMAIL_FORMAT)) {
                System.out.println("Invalid format for email");
                return;
            } else if (registerMessage.equals(SignupMenuMessages.EMAIL_EXISTS)) {
                System.out.println("There is a user who is registered with this email address!");
                return;
            } else if (registerMessage.equals(SignupMenuMessages.REENTER_PASSWORD_CONFIRMATION)) {
                String passwordByUser = passwordConfirmation;
                while (!passwordByUser.equals(password)) {
                    System.out.println("Please re-enter your password:");
                    passwordByUser = scanner.nextLine();
                    if (passwordByUser.equals(password)) {
                        passwordConfirmation = passwordByUser;
                        break;
                    }
                }
            } else if (registerMessage.equals(SignupMenuMessages.WEAK_PASSWORD)) {
                System.out.println("Your password is weak!");
                return;
            } else if (registerMessage.equals(SignupMenuMessages.USER_EXISTS)) {
                System.out.println("There is already a user registered with username: " + username);
                String suggestedUsername = SignupMenuController.suggestNewUsername(username);
                System.out.println("Do you want \"" + suggestedUsername + "\" as your new username? " +
                        "[ Y : yes / N : no ]");
                String userAnswer = scanner.nextLine();
                if (userAnswer.equals("N"))
                    return;
                else
                    username = suggestedUsername;
            } else if (registerMessage.equals(SignupMenuMessages.SHOW_QUESTIONS)) {
                
            }

        }

    }

    private static void securityQuestion(Matcher matcher) {
    }

    private static void goToLoginMenu(Matcher matcher) {
        //TODO: deliver to login menu @Rozhin

    }
}
