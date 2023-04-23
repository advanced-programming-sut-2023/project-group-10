package org.example.view;

import org.apache.commons.cli.*;
import org.example.controller.SignupMenuController;
import org.example.model.SecurityQuestion;
import org.example.model.utils.InputProcessor;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.commands.SignupMenuCommands;
import org.example.view.enums.messages.SignupMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu extends Menu {
    public static void run() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if (SignupMenuCommands.getMatcher(input, SignupMenuCommands.CREATE_USER) != null) register(scanner, input);
            else if (SignupMenuCommands.getMatcher(input, SignupMenuCommands.USER_LOGIN) != null) {
                //TODO:Run login menu?
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private static void register(Scanner scanner, String input) {
        // process input
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
                    System.out.println("invalid option");
                    return;
            }
        }
        if (username == null || password == null || passwordConfirmation == null || nickname == null || email == null || slogan == null) {
            System.out.println("empty field");
            return;
        }
        if (username.isEmpty() || password.isEmpty() || (!password.equals("random") && passwordConfirmation.isEmpty()) || nickname.isEmpty() || email.isEmpty()) {
            System.out.println("missing option");
            return;
        }

        // process controller's response
        SignupMenuMessages registerMessage = SignupMenuController.createUser(username, password, passwordConfirmation, email, nickname);
        switch (registerMessage) {
            case INVALID_USERNAME_FORMAT:
                System.out.println("Invalid format for username");
                return;
            case INVALID_EMAIL_FORMAT:
                System.out.println("Invalid format for email");
                return;
            case EMAIL_EXISTS:
                System.out.println("There is a user who is registered with this email address!");
                return;
            case WRONG_PASSWORD_CONFIRMATION:
                System.out.println("passwords doesn't match, try to signup again");
                return;
            case WEAK_PASSWORD:
                System.out.println("Your password is weak!");
                return;
            case USER_EXISTS:
                System.out.println("There is already a user registered with username: " + username);
                if ((username = suggestNewUsername(scanner, username)) == null) {
                    System.out.println("try to sign up again with a different username");
                    return;
                }
                break;
        }
        if (slogan.equals("random")) {
            slogan = RandomGenerator.getRandomSlogan();
            System.out.println("your slogan is: \"" + slogan + "\"");
        }
        if (password.equals("random") && (password = generateRandomPassword(scanner)) == null) {
            System.out.println("You've entered the suggested password incorrectly, try to signup again");
            return;
        }
        securityQuestion(scanner, username, password, nickname, email, slogan);
    }

    private static String suggestNewUsername(Scanner scanner, String oldUsername) {
        String suggestedUsername = SignupMenuController.suggestNewUsername(oldUsername);
        System.out.println("Do you want \"" + suggestedUsername + "\" as your new username? " + "[ Y : yes / N : no ]");
        String userAnswer;
        while (true) {
            userAnswer = scanner.nextLine();
            if (userAnswer.matches("\\s*N\\s*")) return null;
            if (userAnswer.matches("\\s*Y\\s*")) return suggestedUsername;
            System.out.println("invalid response, please enter Y (yes) or N (no)");
        }
    }

    private static String generateRandomPassword(Scanner scanner) {
        String suggestedPassword = RandomGenerator.generateSecurePassword();
        System.out.println("Your random password is: " + suggestedPassword);
        System.out.println("Please re-enter your password here:");
        String passwordByUser = scanner.nextLine();
        if (passwordByUser.equals(suggestedPassword)) return suggestedPassword;
        else return null;
    }

    private static void securityQuestion(Scanner scanner, String username, String password, String nickname, String email, String slogan) {
        System.out.println("Pick your security question: " + SecurityQuestion.getAllQuestionsString());
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine();
            if ((matcher = SignupMenuCommands.getMatcher(input, SignupMenuCommands.PICK_QUESTION)) == null)
                System.out.println("invalid command, use \"question pick\" to select the security question");
            else break;
        }

        String questionNumber, answer, answerConfirmation;
        String arguments = matcher.replaceAll("");
        String[] args = InputProcessor.separateInput(arguments);
        Options options = new Options();
        Option numberOption = Option.builder().argName("q").longOpt("questionNumber").hasArgs().required().desc("question number").build();
        Option answerOption = Option.builder().argName("a").longOpt("answer").hasArgs().required().desc("answer").build();
        Option confirmOption = Option.builder().argName("c").longOpt("answerConfirm").hasArgs().required().desc("answer Confirmation").build();
        options.addOption(numberOption);
        options.addOption(answerOption);
        options.addOption(confirmOption);
        CommandLineParser parser = new DefaultParser(false);
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getOptionValues(numberOption).length != 1)
                throw new ParseException("error: number must have only one argument");
            questionNumber = cmd.getOptionValue(numberOption);

            if (cmd.getOptionValues(answerOption).length != 1)
                throw new ParseException("error: answer must have only one argument");
            answer = cmd.getOptionValue(answerOption);

            if (cmd.getOptionValues(confirmOption).length != 1)
                throw new ParseException("error: answer Confirmation must have only one argument");
            answerConfirmation = cmd.getOptionValue(confirmOption);
            SignupMenuMessages securityQuestionMessage = SignupMenuController.pickSecurityQuestion(questionNumber, answer, answerConfirmation, username, password, nickname, slogan, email);
            while (!securityQuestionMessage.equals(SignupMenuMessages.SUCCESS)) {
                securityQuestionMessage = SignupMenuController.pickSecurityQuestion(questionNumber, answer, answerConfirmation, username, password, nickname, slogan, email);
                if (securityQuestionMessage.equals(SignupMenuMessages.REENTER_ANSWER)) {
                    System.out.println("Please re-enter your answer!");
                    String confirmation = scanner.nextLine();
                    while (!confirmation.equals(answer)) {
                        confirmation = scanner.nextLine();
                    }
                    answerConfirmation = confirmation;


                } // didn't handle number out of bounds
                else if (securityQuestionMessage.equals(SignupMenuMessages.SUCCESS)) {
                    System.out.println("User successfully created");
                    return;
                } else {
                    System.out.println("Invalid command!");
                }
            }
        } catch (ParseException exception) {
            printMessage(ExceptionMessages.getMessageFromException(exception));
        }
    }
    //TODO

    private static void goToLoginMenu(Matcher matcher) {
    }


}