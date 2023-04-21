package org.example.view;

import org.apache.commons.cli.*;
import org.example.controller.SignupMenuController;
import org.example.model.utils.InputProcessor;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.commands.SignupMenuCommands;
import org.example.view.enums.messages.SignupMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu {
    public static void run() throws ParseException {
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

    private static void register(Matcher matcher) throws ParseException {
        String username, email, nickname, slogan;

        //extract options and arguments from input
        String arguments = matcher.replaceAll("");
        String[] args = InputProcessor.separateInput(arguments);
        Scanner scanner = new Scanner(System.in);

        Options options = new Options();
        Option usernameOption = Option.builder().argName("u").longOpt("username").hasArgs().required().desc("username").build();
        Option passwordOption = Option.builder().argName("p").longOpt("password").hasArgs().required().desc("password").build();
        Option emailOption = Option.builder().argName("e").longOpt("email").hasArgs().required().desc("email").build();
        Option nicknameOption = Option.builder().argName("n").longOpt("nickname").hasArg().required().desc("nickname").build();
        Option sloganOption = Option.builder().argName("s").longOpt("slogan").hasArg().required(false).desc("slogan").build();
        options.addOption(usernameOption);
        options.addOption(passwordOption);
        options.addOption(emailOption);
        options.addOption(nicknameOption);
        options.addOption(sloganOption);

        CommandLineParser parser = new DefaultParser(false);
        try {
            CommandLine cmd = parser.parse(options, args);

            //check username input
            if (cmd.getOptionValues(usernameOption).length != 1)
                throw new ParseException("error: username must have only one argument");
            username = cmd.getOptionValue(usernameOption);

            //check password input
            String originalPassword, passwordConfirmation;
            if (cmd.getOptionValues(usernameOption).length > 2 || cmd.getOptionValues(usernameOption).length == 0)
                throw new ParseException("error: password must have one or two arguments");
            originalPassword = cmd.getOptionValues(passwordOption)[0];
            if (originalPassword.equals("random")) {
                String suggestedPassword = RandomGenerator.generateSecurePassword();
                passwordConfirmation = null;
                System.out.println("Your random password is: " + suggestedPassword);
                System.out.println("Please re-enter your password here:");
                String passwordByUser = scanner.nextLine();
                if (passwordByUser.equals(suggestedPassword)) originalPassword = suggestedPassword;
                else {
                    System.out.println("You've entered the suggested password incorrectly, try to signup again");
                    return;
                }
            } else {
                if (cmd.getOptionValues(passwordOption).length != 2)
                    throw new ParseException("error: password confirmation is missing");
                passwordConfirmation = cmd.getOptionValues(passwordOption)[1];
            }
            if (cmd.getOptionValues(nicknameOption).length != 1)
                throw new ParseException("error: nickname must have exactly one argument!");
            nickname = cmd.getOptionValue(nicknameOption);
            if (cmd.getOptionValues(emailOption).length != 1)
                throw new ParseException("error: you must just enter one valid email address!");
            email = cmd.getOptionValue(emailOption);
            //TODO: handle all the ways slogan could be, the below may cause NullPointerException!
            slogan = cmd.getOptionValue(sloganOption);

            while (true) {
                SignupMenuMessages registerMessage = SignupMenuController
                        .createUser(username, originalPassword, passwordConfirmation, email, nickname, slogan);
                if (registerMessage.equals(SignupMenuMessages.RANDOM_SLOGAN)) {
                    slogan = RandomGenerator.getRandomSlogan();
                    System.out.println(slogan);
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
                    inner:
                    while (!passwordByUser.equals(originalPassword)) {
                        System.out.println("Please re-enter your password:");
                        passwordByUser = scanner.nextLine();
                        if (passwordByUser.equals(originalPassword)) {
                            passwordConfirmation = passwordByUser;
                            break inner;
                        }
                    }
                } else if (registerMessage.equals(SignupMenuMessages.WEAK_PASSWORD)) {
                    System.out.println("Your password is weak!");
                    return;
                } else if (registerMessage.equals(SignupMenuMessages.USER_EXISTS)) {
                    System.out.println("There is already a user registered with username: " + username);
                    String suggestedUsername = SignupMenuController.suggestNewUsername(username);
                    System.out.println("Do you want \"" + suggestedUsername + "\" as your new username? " + "[ Y : yes / N : no ]");
                    String userAnswer = scanner.nextLine();
                    if (userAnswer.equals("N")) return;
                    else username = suggestedUsername;
                } else if (registerMessage.equals(SignupMenuMessages.SHOW_QUESTIONS)) {
                    System.out.println("Pick your security question: 1. What is my father’s name" +
                            " 2. What was my first pet’s name? " +
                            "3. What is my mother’s last name?");
                    String input = "";

                    while ((matcher = SignupMenuCommands.getMatcher(input, SignupMenuCommands.USER_LOGIN)) == null) {
                        input = scanner.nextLine();
                        if ((matcher = SignupMenuCommands.getMatcher(input, SignupMenuCommands.USER_LOGIN)) != null) {
                            securityQuestion(scanner, matcher, username, originalPassword, nickname, email, slogan);
                        }
                    }
                    return;
                }

            }


        } catch (ParseException exception) {
            if (exception instanceof AlreadySelectedException)
                printMessage("error: " + ((AlreadySelectedException) exception).getOption().getDescription() + " was entered multiple times");
            else if (exception instanceof MissingArgumentException)
                printMessage("error: " + "you must enter an argument for " + ((MissingArgumentException) exception).getOption().getDescription());
            else if (exception instanceof MissingOptionException)
                printMessage("error: " + "missing " + getMissingOptionsString((MissingOptionException) exception));
            else if (exception instanceof UnrecognizedOptionException)
                printMessage("error: " + "option " + ((UnrecognizedOptionException) exception).getOption() + " isn't recognized");
            else printMessage(exception.getMessage());
        }
    }

    private static void securityQuestion(Scanner scanner, Matcher matcher, String username, String password, String nickname, String email,
                                         String slogan) {
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
            SignupMenuMessages securityQuestionMessage = SignupMenuController.pickSecurityQuestion(questionNumber, answer, answerConfirmation,
                    username, password, nickname, slogan, email);
            while (!securityQuestionMessage.equals(SignupMenuMessages.SUCCESS)) {
                securityQuestionMessage = SignupMenuController.pickSecurityQuestion(questionNumber, answer, answerConfirmation
                        , username, password, nickname, slogan, email);
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
            return;


        } catch (ParseException exception) {

            if (exception instanceof AlreadySelectedException)
                printMessage("error: " + ((AlreadySelectedException) exception).getOption().getDescription() + " was entered multiple times");
            else if (exception instanceof MissingArgumentException)
                printMessage("error: " + "you must enter an argument for " + ((MissingArgumentException) exception).getOption().getDescription());
            else if (exception instanceof MissingOptionException)
                printMessage("error: " + "missing " + getMissingOptionsString((MissingOptionException) exception));
            else if (exception instanceof UnrecognizedOptionException)
                printMessage("error: " + "option " + ((UnrecognizedOptionException) exception).getOption() + " isn't recognized");
            else printMessage(exception.getMessage());
        }


    }
    //TODO

    private static void goToLoginMenu(Matcher matcher) {
    }


    private static String getMissingOptionsString(MissingOptionException exception) {
        String result = "";
        for (Object missingOption : exception.getMissingOptions())
            result = ((Option) missingOption).getDescription() + ", ";
        result = result.replaceAll(", $", "");
        return result;
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }
}
