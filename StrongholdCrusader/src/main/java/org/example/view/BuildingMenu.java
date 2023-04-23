package org.example.view;

import org.example.controller.BuildingMenuController;
import org.example.model.utils.InputProcessor;
import org.example.view.enums.messages.BuildingMenuMessages;

import java.util.HashMap;
import java.util.Map;

public class BuildingMenu {
    public static void run(){

    }
    private static void createUnit(String input){
        HashMap<String, String> options = InputProcessor.separateInput(input);
        String type = "";
        String c = "";
        for (Map.Entry<String, String> option : options.entrySet()) {
            switch (option.getKey()) {

                case "-c":
                    c = option.getValue();
                    break;
                case "-t":
                    type = option.getValue();
                default:
                    System.out.println("invalid option");
                    return;

            }
        }

        if (!c.matches("\\d+"))
            System.out.println("You should enter a number for row!");
        int count=Integer.parseInt(c);
        BuildingMenuMessages message= BuildingMenuController.createUnit(type,count);
        switch (message){
            case INVALID_ROW:
                System.out.println("You've entered invalid row");
                break;
            case INVALID_COLUMN:

        }
    }
    private static void repair(){

    }
    private static void selectUnit(){

    }

}
