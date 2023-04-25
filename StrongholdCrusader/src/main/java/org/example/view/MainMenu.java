package org.example.view;

import org.example.controller.MainMenuController;
import org.example.model.User;
import org.example.model.game.Color;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.commands.MainMenuCommands;
import org.example.view.enums.messages.MainMenuMessages;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {
    public static void run() {

        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.START_GAME) != null)
                startGame(input);
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.LOGOUT) != null)
                logout();
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.PROFILE_MENU) != null)
                goToProfileMenu();

            else System.out.println("Invalid command!");
        }
    }

    private static void startGame(String input) {
        // input format : start game -c <government count> -s <map size>
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String count = "";
        String size = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-s":
                    size = option.getValue();
                    break;
                case "-c":
                    count = option.getValue();
                    break;
                default:
                    System.out.println("Invalid option!");
                    return;
            }
        }
        int governmentCount, mapSize;
        if (count.equals("") || size.equals("")) {
            System.out.println("missing option!");
            return;
        }
        if (!size.matches("\\d+")) {
            System.out.println("You should enter a number(200 or 400) for size");
            return;
        } else
            mapSize = Integer.parseInt(count);
        if (!count.matches("\\d+")) {
            System.out.println("You should enter a number(between 2 and 8) for count");
            return;
        } else
            governmentCount = Integer.parseInt(count);
        int enteredCount = 0;
        // input format : -p <player's username> -c <selected color>
        Scanner scanner = new Scanner(System.in);
        HashMap<User, Color> players = new HashMap<>();
        while (enteredCount < governmentCount) {
            if (getUsersForGame(players))
                enteredCount++;
        }
       GameMenu.run();

    }

    private static boolean getUsersForGame(HashMap<User, Color> usernames) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String color = "";
        String username = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-s":
                    username = option.getValue();
                    break;
                case "-c":
                    color = option.getValue();
                    break;
                default:
                    System.out.println("Invalid option!");
                    return false;
            }
        }
        if (color.equals("") || username.equals("")) {
            System.out.println("missing option!");
            return false;
        }
        User myUser = User.getUserByUsername(username);
        if (myUser == null) {
            System.out.println("user not found!");
            return false;
        }
        Color myColor = MainMenuController.isColorValid(color);
        if (myColor == null) {
            System.out.println("Enter a valid color name!");
            return false;
        }
        usernames.put(myUser, myColor);
        System.out.println("player with username " + username + " added to the game successfully with color " + color);

        return true;


    }

    private static void logout() {

        MainMenuMessages message = MainMenuController.logout();
        switch (message) {
            case SUCCESSFUL_LOGOUT:
                System.out.println("user logged out successfully!");
                LoginMenu.run();
                break;
            case USER_IN_THE_BATTLE:
                System.out.println("You're in a battle,You can't logout");
                break;
        }
    }

    private static void goToProfileMenu() {
        ProfileMenu.run();
    }
}

