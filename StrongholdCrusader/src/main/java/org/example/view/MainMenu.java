package org.example.view;

import org.example.controller.MainMenuController;
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
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.LOGOUT) != null) {
                logout();
                return;
            }

            if (MainMenuCommands.getMatcher(input, MainMenuCommands.EXIT) != null)
                System.exit(0);
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
        MainMenuMessages message = MainMenuController.checkMapAndGovernmentsCount(mapSize, governmentCount);
        switch (message) {
            case SUCCESS:
                System.out.println("Enter usernames of players you wish to play with: ");
                break;
            case INVALID_GOVERNMENT_COUNT:
                System.out.println("You should enter a number between 2 and 8 for count of governments!");
                return;
            case INVALID_MAP_SIZE:
                System.out.println("You should enter 200 or 400 for map size!");
                return;
        }

        int enteredCount = 0;
        // input format : -p <player's username> -c <selected color>
        Scanner scanner = new Scanner(System.in);
        HashMap<String, String> players = new HashMap<>();
        while (enteredCount < governmentCount) {
            if (getUsersForGame(players))
                enteredCount++;

        }
        org.example.model.game.envirnmont.Map map = CustomizeMapMenu.run(mapSize);
        GameMenu.run(players, map);
    }

    private static boolean getUsersForGame(HashMap<String, String> players) {
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
        MainMenuMessages message = MainMenuController.getPlayers(username, color);
        switch (message) {
            case SUCCESS:
                System.out.println("player with username " + username + " added to the game successfully with color " + color);
                break;
            case INVALID_USERNAME:
                System.out.println("Player with " + username + " doesn't seem to exist!");
                return false;
            case INVALID_COLOR:
                System.out.println("You've entered an invalid color name!");
                return false;
        }
        players.put(username, color);
        return true;


    }

    private static void logout() {

        MainMenuMessages message = MainMenuController.logout();
        System.out.println("user logged out successfully!");
    }

    private static void goToProfileMenu() {
        ProfileMenu.run();
    }
}

