package org.example.view;

import org.example.controller.MainMenuController;
import org.example.model.Stronghold;
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
            else if (MainMenuCommands.getMatcher(input, MainMenuCommands.LOGOUT) != null) {
                logout();
                return;
            } else if (MainMenuCommands.getMatcher(input, MainMenuCommands.EXIT) != null)
                System.exit(0);
            else if (MainMenuCommands.getMatcher(input, MainMenuCommands.PROFILE_MENU) != null)
                goToProfileMenu();
            else if (input.matches("^\\s*show\\s+menu\\s+name\\s*$"))
                System.out.println("main menu");

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
            mapSize = Integer.parseInt(size);
        if (!count.matches("\\d+")) {
            System.out.println("You should enter a number(between 2 and 8) for count");
            return;
        } else
            governmentCount = Integer.parseInt(count);
        MainMenuMessages message = MainMenuController.checkMapAndGovernmentsCount(mapSize, governmentCount);
        switch (message) {
            case INVALID_GOVERNMENT_COUNT:
                System.out.println("You should enter a number between 2 and 8 for count of governments!");
                return;
            case INSUFFICIENT_GlOBAL_USERS:
                System.out.println("There isn't enough registered users!");
                return;
            case INVALID_MAP_SIZE:
                System.out.println("You should enter 200 or 400 for map size!");
                return;
        }

        Scanner scanner = new Scanner(System.in);
        HashMap<String, String> players = new HashMap<>();
        System.out.println("pick a color for your own government [color name]");
        String colors = "";
        for (Color value : Color.values()) {
            colors = colors.concat(value.getName() + " - ");
        }
        System.out.println(colors.substring(0, colors.length() - 3));
        String myOwnColor = null;
        for (int i = 0; i < 10; i++) {
            myOwnColor = scanner.nextLine();
            if (Color.getColorByName(myOwnColor) != null)
                break;
            else
                myOwnColor = "";

        }
        if (myOwnColor.length() == 0) {
            System.out.println("You've taken all your chances,If you wish to start a new game, try again!");
            return;
        }
        players.put(Stronghold.getCurrentUser().getUsername(), myOwnColor);

        // input format : -u <player's username> -c <selected color>
        System.out.println("Enter usernames of players you wish to play with: ");
        int enteredCount = 0;
        while (enteredCount < governmentCount - 1) {
            if (getUsersForGame(players))
                enteredCount++;

        }
        GameMenu.run(players, new org.example.model.game.envirnmont.Map(mapSize));
    }

    private static boolean getUsersForGame(HashMap<String, String> players) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String color = "";
        String username = "";

        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {
                case "-u":
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
        MainMenuMessages message = MainMenuController.getPlayers(username, color, players);
        switch (message) {
            case SUCCESS:
                System.out.println("player with username " + username + " added to the game successfully with color " + color);
                break;
            case USER_IN_THE_BATTLE:
                System.out.println("Player with username " + username + " is already added!");
                return false;
            case TAKEN_COLOR:
                System.out.println("You've already assigned this color to a player, choose a new color!");
                return false;
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