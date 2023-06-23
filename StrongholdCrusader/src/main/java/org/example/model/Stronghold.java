package org.example.model;

import com.google.gson.reflect.TypeToken;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import org.example.model.game.Battle;
import org.example.model.game.buildings.buildingconstants.*;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.units.unitconstants.MilitaryEquipmentRole;
import org.example.model.game.units.unitconstants.MilitaryPersonRole;
import org.example.model.game.units.unitconstants.Role;
import org.example.model.game.units.unitconstants.WorkerRole;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.example.model.User.gson;

public class Stronghold {
    public static DataBase dataBase = new DataBase();
    private static User currentUser;
    private static Battle currentBattle;
    private static ExtendedBlock[][] currentMapGraphics;
    private static Group mapGroupGFX;
    private static HashMap<Polygon, Coordinate> polygonCoordinateMap = new HashMap<>();
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static Battle getCurrentBattle() {
        return currentBattle;
    }

    public static void setCurrentBattle(Battle newBattle) {
        currentBattle = newBattle;
    }

    public static ExtendedBlock[][] getCurrentMapGraphics() {
        return currentMapGraphics;
    }

    public static void setCurrentMapGraphics(ExtendedBlock[][] currentMapGraphics) {
        Stronghold.currentMapGraphics = currentMapGraphics;
    }

    public static Group getMapGroupGFX() {
        return mapGroupGFX;
    }

    public static void setMapGroupGFX(Group mapGroupGFX) {
        Stronghold.mapGroupGFX = mapGroupGFX;
    }

    public static HashMap<Polygon, Coordinate> getPolygonCoordinateMap() {
        return polygonCoordinateMap;
    }

    public static User getLoggedInUserFromFile() {

        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/LoggedInUser.json")));
            User loggedInUser = gson.fromJson(json, new TypeToken<User>() {
            }.getType());
            if (loggedInUser != null)
                return loggedInUser;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void addUserToFile(User user) {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/LoggedInUser.json");
            fileWriter.write(gson.toJson(user));
            fileWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static void initializeApp() {
        Stronghold.dataBase.loadUsersFromFile();
        //initialize unit role classes
        Role.initializeRoles();
        MilitaryEquipmentRole.initializeRoles();
        MilitaryPersonRole.initializeRoles();
        WorkerRole.initializeRoles();
        //initialize building type classes
        BuildingType.initializeTypes();
        AttackingBuildingType.initializeTypes();
        ItemProducingBuildingType.initializeTypes();
        PersonProducingBuildingType.initializeTypes();
        PopularityIncreasingBuildingType.initializeTypes();
        StorageBuildingType.initializeTypes();
    }
}