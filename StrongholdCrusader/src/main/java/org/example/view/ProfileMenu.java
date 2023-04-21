package org.example.view;

import org.example.controller.ProfileMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.view.enums.commands.ProfileMenuCommands;
import org.example.view.enums.messages.ProfileMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

//TODO ask about change password and return to main menu functions

public class ProfileMenu {
    public static void run(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            String command = scanner.nextLine();
            Matcher matcher;

            if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)) != null)
                changeUsername(matcher);

            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME)) != null)
                changeNickname(matcher);

            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)) != null)
                changePassword(matcher);

            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL)) != null)
                changeEmail(matcher);

            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN)) != null)
                changeSlogan(matcher);

            else if(ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGHSCORE) != null)
                showHighscore();

            else if(ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK) != null)
                showRank();

            else if(ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN) != null)
                showSlogan();

            else if(ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN) != null)
                removeSlogan();

            else if(ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE) != null)
                showProfile();
        }
    }

    private static void changeUsername(Matcher matcher){
        String username = matcher.group("username");

        ProfileMenuMessages message = ProfileMenuController.changeUsername(username);

        switch (message){
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

    private static void changeNickname(Matcher matcher){
        String nickname = matcher.group("nickname");

        ProfileMenuMessages message = ProfileMenuController.changeNickname(nickname);

        switch (message){
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

    private static void changePassword(Matcher matcher){
        String oldPassword = matcher.group("oldpass");
        String newPassword = matcher.group("newpass");

        ProfileMenuMessages message = ProfileMenuController.changePassword(oldPassword, newPassword);

        switch (message){
            case INCORRECT_CAPTCHA:
                System.out.println("Incorrect captcha!");
                break;

            case NO_PASSWORD_PROVIDED:
                System.out.println("No new Password provided!");
                break;

            case WEAK_PASSWORD:
                System.out.println("Weak new password!");
                break;

            case INCORRECT_PASSWORD:
                System.out.println("Current password is incorrect!");
                break;

            default:
                System.out.println("Password changed successfully!");
                break;
        }
    }

    private static void changeEmail(Matcher matcher){
        String email = matcher.group("email");

        ProfileMenuMessages message = ProfileMenuController.changeEmail(email);

        switch (message){
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

    private static void changeSlogan(Matcher matcher){
        String slogan = matcher.group("slogan");

        ProfileMenuMessages message = ProfileMenuController.changeSlogan(slogan);

        switch (message){
            case NO_SLOGAN_PROVIDED:
                System.out.println("No slogan provided!");
                break;

            default:
                System.out.println("Slogan changed successfully!");
                break;
        }
    }

    private static void showHighscore(){
        System.out.println("Highscore: " + Stronghold.getCurrentUser().getHighScore());
    }

    private static void showRank(){
        System.out.println("Rank: " + Stronghold.getCurrentUser().getRank());
    }

    private static void showSlogan(){
        if(Stronghold.getCurrentUser().getSlogan() == null)
            System.out.println("Slogan is empty!");

        else
            System.out.println("Slogan: " + Stronghold.getCurrentUser().getSlogan());
    }

    private static void removeSlogan(){
        Stronghold.getCurrentUser().setSlogan(null);
        System.out.println("Slogan removed successfully");
    }

    private static void showProfile(){
        User user = Stronghold.getCurrentUser();

        System.out.println("Username: " + user.getUsername());
        System.out.println("Nickname: " + user.getNickname());
        System.out.println("Email: " + user.getEmail());

        if(user.getSlogan() != null)
            System.out.println("Slogan: " + user.getSlogan());

        System.out.println("Highscore:" + user.getHighScore());
        System.out.println("Rank: " + user.getRank());
    }

    private static void goToMainMenu(){

    }
}
