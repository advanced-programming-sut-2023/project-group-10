package org.example.view;

import org.example.controller.MainMenuController;
import org.example.view.enums.commands.MainMenuCommands;
import org.example.view.enums.messages.MainMenuMessages;

import java.util.Scanner;

public class MainMenu {
    public static void run() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.START_GAME) != null);
                //startGame(input);
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

    /*
    private static void startGame(String input) throws Exception {
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
        HashMap<String, Coordinate> keeps = new HashMap<>();
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
        System.out.println("Enter your keeps position: [in -x -y format]");
        while (true) {
            try {
                Coordinate position = InputProcessor.getCoordinateFromXYInput(InputProcessor.separateInput(scanner.nextLine()), "-x", "-y", mapSize);
                keeps.put(Stronghold.getCurrentUser().getUsername(), position);
                System.out.println("You've chosen keep successfully, now choose players you wish to play with and their keeps!");
                break;
            } catch (Exception exception) {
                System.out.println(exception.getMessage() + ", Try to enter coordinates again");
            }
        }

        players.put(Stronghold.getCurrentUser().getUsername(), myOwnColor);

        // input format : -u <player's username> -c <selected color> -x keep's x -y keeps y
        int enteredCount = 0;
        while (enteredCount < governmentCount - 1) {
            if (getUsersForGame(players, keeps, mapSize))
                enteredCount++;

        }
        GameMenu.run(players, keeps, new org.example.model.game.envirnmont.Map(mapSize));
    }

    private static boolean getUsersForGame(HashMap<String, String> players, HashMap<String, Coordinate> keeps, int mapSize) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String username = options.getOrDefault("-u", "");
        ;

        if (username == null) {
            System.out.println("empty username filed");
            return false;
        }
        if (username.isEmpty()) {
            System.out.println("missing username");
            return false;
        }
        options.remove("-u");
        String color = options.getOrDefault("-c", "");
        if (color == null) {
            System.out.println("empty color filed");
            return false;
        }
        if (color.isEmpty()) {
            System.out.println("missing color");
            return false;
        }
        options.remove("-c");
        Coordinate position;
        try {
            position = InputProcessor.getCoordinateFromXYInput(options, "-x", "-y", mapSize);
            MainMenuMessages message = MainMenuController.getPlayers(username, color, players, keeps, position);
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
                case TAKEN_POSITION_FOR_KEEP:
                    System.out.println("The coordinate you entered for keep is another player's keep");
                    return false;
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
        keeps.put(username, position);
        players.put(username, color);
        return true;
    }
     */

    private static void logout() {
        MainMenuMessages message = MainMenuController.logout();
        System.out.println("user logged out successfully!");
    }

    private static void goToProfileMenu() {
        //ProfileMenu.run();
    }
}