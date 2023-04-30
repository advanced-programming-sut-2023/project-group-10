package org.example.view;

import org.example.controller.ProfileMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
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

            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.BACK) != null)
                return;
        }
    }

    private static void changeUsername(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String username = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-u")) {
                username = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changeUsername(username);

        switch (message) {
            case NO_USERNAME_PROVIDED:
                System.out.println("No username provided!");
                break;

            case INVALID_USERNAME:
                System.out.println("Username format is invalid!");
                break;

            case USERNAME_EXISTS:
                System.out.println("Username already exists!");
                break;

            default:
                System.out.println("Username changed successfully");
                break;
        }
    }

    private static void changeNickname(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String nickname = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-n")) {
                nickname = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changeNickname(nickname);

        switch (message) {
            case NO_NICKNAME_PROVIDED:
                System.out.println("No nickname provided!");
                break;

            case INVALID_NICKNAME:
                System.out.println("Nickname format is invalid!");

            default:
                System.out.println("Nickname changed successfully!");
                break;
        }
    }

    private static void changePassword(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String oldPassword = "";
        String newPassword = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            if(option.getKey().equals("-n"))
                newPassword = option.getValue();

            else if(option.getKey().equals("-o"))
                oldPassword = option.getValue();

            else{
                System.out.println("invalid command");
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
                System.out.println("Password changed successfully!");
                break;
        }
    }

    private static void changeEmail(String input) {
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String email = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            if (option.getKey().equals("-e")) {
                email = option.getValue();
            } else {
                System.out.println("invalid option");
                return;
            }
        }

        ProfileMenuMessages message = ProfileMenuController.changeEmail(email);

        switch (message) {
            case NO_EMAIL_PROVIDED:
                System.out.println("No email provided!");
                break;

            case EMAIL_EXISTS:
                System.out.println("Email already exists!");
                break;

            case INVALID_EMAIL:
                System.out.println("Invalid email format!");
                break;

            default:
                System.out.println("Email changed successfully!");
                break;
        }
    }

    private static void changeSlogan(String input) {
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

    private static void showHighscore() {
        System.out.println("Highscore: " + Stronghold.getCurrentUser().getHighScore());
    }

    private static void showRank() {
        System.out.println("Rank: " + Stronghold.getCurrentUser().getRank());
    }

    private static void showSlogan() {
        if (Stronghold.getCurrentUser().getSlogan() == null)
            System.out.println("Slogan is empty!");

        else
            System.out.println("Slogan: " + Stronghold.getCurrentUser().getSlogan());
    }

    private static void removeSlogan() {
        Stronghold.getCurrentUser().setSlogan(null);
        System.out.println("Slogan removed successfully");
    }

    private static void showProfile() {
        User user = Stronghold.getCurrentUser();

        System.out.println("Username: " + user.getUsername());
        System.out.println("Nickname: " + user.getNickname());
        System.out.println("Email: " + user.getEmail());

        if (user.getSlogan() != null)
            System.out.println("Slogan: " + user.getSlogan());

        System.out.println("Highscore:" + user.getHighScore());
        System.out.println("Rank: " + user.getRank());
    }
}
