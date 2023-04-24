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
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.LOGOUT) != null)
                logout();
            if (MainMenuCommands.getMatcher(input, MainMenuCommands.PROFILE_MENU) != null)
                goToProfileMenu();

            else System.out.println("Invalid command!");
        }
    }

        private static void startGame (String input){

        }

        private static void logout () {

            MainMenuMessages message = MainMenuController.logout();
            switch (message){
                case SUCCESSFUL_LOGOUT:
                    System.out.println("user logged out successfully!");
                    break;
                case USER_IN_THE_BATTLE:
                    System.out.println("You're in a battle,You can't logout");
                    break;
            }
        }
        private static void goToProfileMenu () {


        }
    }

