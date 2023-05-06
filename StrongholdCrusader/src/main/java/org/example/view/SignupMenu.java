package org.example.view;

import org.example.controller.SignupMenuController;
import org.example.model.SecurityQuestion;
import org.example.model.utils.CaptchaGenerator;
import org.example.model.utils.InputProcessor;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.commands.SignupMenuCommands;
import org.example.view.enums.messages.SignupMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignupMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (SignupMenuCommands.getMatcher(input, SignupMenuCommands.CREATE_USER) != null)
                register(scanner, input);
            else if (SignupMenuCommands.getMatcher(input, SignupMenuCommands.ENTER_LOGIN_MENU) != null)
                LoginMenu.run();
            else if (SignupMenuCommands.getMatcher(input, SignupMenuCommands.EXIT) != null)
                return;
            else if(input.matches("^\\s*show\\s+menu\\s+name\\s*$"))
                System.out.println("signup menu");
            else
                System.out.println("Invalid command!");
        }
    }

    public static String register(Scanner scanner, String input) {
        // process input
        String result;
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String username = "";
        String password = "";
        String passwordConfirmation = "";
        String nickname = "";
        String email = "";
        String slogan = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-u":
                    username = option.getValue();
                    break;
                case "-p":
                    password = option.getValue();
                    break;
                case "-c":
                    passwordConfirmation = option.getValue();
                    break;
                case "-n":
                    nickname = option.getValue();
                    break;
                case "-e":
                    email = option.getValue();
                    break;
                case "-s":
                    slogan = option.getValue();
                    break;
                default:
                    result = "invalid option";
                    System.out.println(result);
                    return result;
            }
        }
        if (username == null || password == null || passwordConfirmation == null || nickname == null || email == null || slogan == null) {
            result = "empty field";
            System.out.println(result);
            return result;
        }
        if (username.isEmpty() || password.isEmpty() || (!password.equals("random") && passwordConfirmation.isEmpty())
                || nickname.isEmpty() || email.isEmpty()) {

            result = "missing option";
            System.out.println(result);
            return result;
        }

        // process controller's response
        SignupMenuMessages registerMessage = SignupMenuController.createUser(username, password, passwordConfirmation, email, nickname);
        switch (registerMessage) {
            case INVALID_USERNAME_FORMAT:
                result = "Invalid format for username";
                System.out.println(result);
                return result;
            case INVALID_EMAIL_FORMAT:
                result = "Invalid format for email";
                System.out.println(result);
                return result;
            case EMAIL_EXISTS:
                result = "There is a user who is registered with this email address!";
                System.out.println(result);
                return result;
            case WRONG_PASSWORD_CONFIRMATION:
                result = "passwords doesn't match, try to signup again";
                System.out.println(result);
                return result;
            case SHORT_PASSWORD:
                result = "Short password!,You must provide at least 6 characters!";
                System.out.println(result);
                return result;

            case NO_LOWERCASE_LETTER:
                result = "Your password must contain a lowercase letter!";
                System.out.println(result);
                return result;

            case NO_UPPERCASE_LETTER:
                result = "Your password must contain an uppercase letter!";
                System.out.println(result);
                return result;

            case NO_NUMBER:
                result = "Your password must contain at least one digit!";
                System.out.println(result);
                return result;

            case NO_SPECIAL_CHARACTER:
                result = "Your password must contain at least one special character!";
                System.out.println(result);
                return result;

            case USER_EXISTS:
                System.out.println("There is already a user registered with username: " + username);
                if ((username = suggestNewUsername(scanner, username)) == null) {
                    result = "try to sign up again with a different username";
                    System.out.println(result);
                    return result;
                }
                break;
        }
        if (slogan.equals("random")) {
            slogan = RandomGenerator.getRandomSlogan();
            System.out.println("your slogan is: \"" + slogan + "\"");
        }
        if (password.equals("random") && (password = generateRandomPassword(scanner)) == null) {
            result = "You've entered the suggested password incorrectly, try to signup again";
            System.out.println(result);
            return result;
        }
        securityQuestion(scanner, username, password, nickname, email, slogan);
        CaptchaGenerator.run();
        result = "User successfully created";
        System.out.println(result);
        return result;
    }

    private static String suggestNewUsername(Scanner scanner, String oldUsername) {
        String suggestedUsername = SignupMenuController.suggestNewUsername(oldUsername);
        System.out.println("Do you want \"" + suggestedUsername + "\" as your new username? " + "[ Y : yes / N : no ]");
        String userAnswer;
        for (int i = 0; i < 2; i++) {
            userAnswer = scanner.nextLine();
            if (userAnswer.matches("\\s*N\\s*")) return null;
            if (userAnswer.matches("\\s*Y\\s*")) return suggestedUsername;
            System.out.println("invalid response, please enter Y (yes) or N (no)");
        }
        return null;
    }

    private static String generateRandomPassword(Scanner scanner) {
        String suggestedPassword = RandomGenerator.generateSecurePassword();
        System.out.println("Your random password is: " + suggestedPassword);
        System.out.println("Please re-enter your password here:");
        String passwordByUser = scanner.nextLine();
        if (passwordByUser.equals(suggestedPassword)) return suggestedPassword;
        else return null;
    }

    public static String pickQuestion(Scanner scanner) {
        String input;
        System.out.println("Pick your security question: " + SecurityQuestion.getAllQuestionsString());
        while (true) {
            input = scanner.nextLine();
            if (SignupMenuCommands.getMatcher(input, SignupMenuCommands.PICK_QUESTION) == null)
                System.out.println("invalid command, use \"question pick\" to select the security question");
            else break;
        }
        return input;
    }

    public static String securityQuestion(Scanner scanner, String username, String password, String nickname, String email, String slogan) {
        String input;
        String result;
        input = pickQuestion(scanner);
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String questionNumber = "";
        String answer = "";
        String answerConfirmation = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-q":
                    questionNumber = option.getValue();
                    break;
                case "-a":
                    answer = option.getValue();
                    break;
                case "-c":
                    answerConfirmation = option.getValue();
                    break;
                default:
                    result = "invalid option, pick security question again";
                    System.out.println(result);
                    securityQuestion(scanner, username, password, nickname, email, slogan);
                    return result;
            }
        }
        if (questionNumber == null || answer == null || answerConfirmation == null) {
            result = "empty field, try again";
            System.out.println(result);
            securityQuestion(scanner, username, password, nickname, email, slogan);
            return result;
        }
        if (questionNumber.isEmpty() || answer.isEmpty() || answerConfirmation.isEmpty()) {
            result = "missing option, try again";
            System.out.println(result);
            securityQuestion(scanner, username, password, nickname, email, slogan);
            return result;
        }
        if (!questionNumber.matches("\\d+")) {
            result = "invalid question number, try again";
            System.out.println(result);
            securityQuestion(scanner, username, password, nickname, email, slogan);
            return result;
        }

        SignupMenuMessages securityQuestionMessage;
        securityQuestionMessage = SignupMenuController.pickSecurityQuestionAndCreateUser
                (questionNumber, answer, answerConfirmation, username, password, nickname, slogan, email);
        if (securityQuestionMessage.equals(SignupMenuMessages.NUMBER_OUT_OF_BOUNDS)) {
            result = "question number should be between 1 and 3, try again";
            securityQuestion(scanner, username, password, nickname, email, slogan);
        } else if (securityQuestionMessage.equals(SignupMenuMessages.REENTER_ANSWER)) {
            result = "answers don't match, try again";

            securityQuestion(scanner, username, password, nickname, email, slogan);
        } else
            result = "Please complete captcha";
        System.out.println(result);
        return result;
    }
}
