package org.example.view;

import org.example.controller.ProfileMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CaptchaGenerator;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.ProfileMenuCommands;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfileMenu {
    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME) != null)
                changeUsername(command);

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME) != null)
                changeNickname(command);

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD) != null)
                changePassword(command);

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL) != null)
                changeEmail(command);

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN) != null)
                changeSlogan(command);

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGHSCORE) != null)
                showHighscore();

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK) != null)
                showRank();

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN) != null)
                showSlogan();

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN) != null)
                removeSlogan();

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE) != null)
                showProfile();

            else if (command.matches("^\\s*show\\s+menu\\s+name\\s*$"))
                System.out.println("profile menu");

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.BACK) != null)
                return;
            else
                System.out.println("invalid command!");
        }
    }

    public static String changeUsername(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String username = "";
        String result;
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-u")) {
                username = option.getValue();
            } else {
                result = "invalid option";
                System.out.println(result);
                return result;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changeUsername(username);

        switch (message) {
            case NO_USERNAME_PROVIDED:
                result = "No username provided!";
                System.out.println(result);
                return result;

            case INVALID_USERNAME:
                result = "Username format is invalid!";
                System.out.println(result);
                return result;
            case OLD_USERNAME_ENTERED:
                result = "You have to enter a new username!";
                System.out.println(result);
                return result;

            case USERNAME_EXISTS:
                result = "Username already exists!";
                System.out.println(result);
                return result;

            default:
                result = "Username changed successfully";
                System.out.println(result);
                break;
        }
        return result;
    }

    public static String changeNickname(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String nickname = "";
        String result;
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-n")) {
                nickname = option.getValue();
            } else {
                result = "invalid option";
                System.out.println(result);
                return result;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changeNickname(nickname);

        switch (message) {
            case NO_NICKNAME_PROVIDED:
                result = "No nickname provided!";
                break;

            case INVALID_NICKNAME:
                result = "Nickname format is invalid!";
                break;

            default:
                result = "Nickname changed successfully!";
                break;
        }
        System.out.println(result);
        return result;
    }

    public static void changePassword(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String oldPassword = "";
        String newPassword = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-n"))
                newPassword = option.getValue();

            else if (option.getKey().equals("-o"))
                oldPassword = option.getValue();

            else {
                System.out.println("invalid option");
                return;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changePassword(oldPassword, newPassword);
        switch (message) {
            case INCORRECT_CAPTCHA:
                System.out.println("Incorrect captcha!");
                break;

            case NO_PASSWORD_PROVIDED:
                System.out.println("No new Password provided!");
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

            case INCORRECT_PASSWORD:
                System.out.println("Current password is incorrect!");
                break;

            default:
                CaptchaGenerator.run();
                System.out.println("Password changed successfully!");
                break;
        }
    }

    public static String changeEmail(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String email = "";
        String result;
        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-e")) {
                email = option.getValue();
            } else {
                result = "invalid option";
                System.out.println(result);
                return result;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changeEmail(email);

        switch (message) {
            case NO_EMAIL_PROVIDED:
                result = "No email provided!";
                break;
            case OLD_EMAIL_ENTERED:
                result = "You have to enter a new email!";
                break;

            case EMAIL_EXISTS:
                result = "Email already exists!";
                break;

            case INVALID_EMAIL:
                result = "Invalid email format!";
                break;

            default:
                result = "Email changed successfully!";
                break;
        }
        System.out.println(result);
        return result;
    }

    public static void changeSlogan(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String slogan = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-s")) {
                slogan = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changeSlogan(slogan);

        switch (message) {
            case NO_SLOGAN_PROVIDED:
                System.out.println("No slogan provided!");
                break;

            default:
                System.out.println("Slogan changed successfully!");
                break;
        }
    }

    public static void showHighscore() {
        System.out.println("Highscore: " + Stronghold.getCurrentUser().getHighScore());
    }

    public static void showRank() {
        System.out.println("Rank: " + Stronghold.getCurrentUser().getRank());
    }

    public static void showSlogan() {
        if (Stronghold.getCurrentUser().getSlogan() == null)
            System.out.println("Slogan is empty!");

        else
            System.out.println("Slogan: " + Stronghold.getCurrentUser().getSlogan());
    }

    public static void removeSlogan() {
        Stronghold.getCurrentUser().setSlogan(null);
        System.out.println("Slogan removed successfully");
    }

    public static String showProfile() {
        User user = Stronghold.getCurrentUser();
        String result = "";
        result = result.concat("Username: " + user.getUsername() + "\n");
        result = result.concat("Nickname: " + user.getNickname() + "\n");
        result = result.concat("Email: " + user.getEmail() + "\n");

        if (user.getSlogan() != null)
            result = result.concat("Slogan: " + user.getSlogan() + "\n");
        result = result.concat("Highscore:" + user.getHighScore() + "\n");
        result = result.concat("Rank: " + user.getRank() + "\n");
        System.out.println(result);
        return result;
    }
}
