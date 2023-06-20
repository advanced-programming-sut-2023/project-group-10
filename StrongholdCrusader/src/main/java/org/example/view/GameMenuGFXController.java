package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.model.game.buildings.buildingconstants.BuildingCategory;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.utils.RandomGenerator;

import java.util.ArrayList;

public class GameMenuGFXController {
    public Pane mapBox;
    public HBox controlBox;
    public ListView<VBox> buildingBox;
    public Pane miniMapBox;
    public Pane infoBox;

    public void prepareGame(Stage stage) {
        System.out.println(stage.getHeight());
        controlBox.setPrefHeight(stage.getHeight() / 5);
        controlBox.setStyle("-fx-background-color: #171817");
        mapBox.setPrefHeight(stage.getHeight() - controlBox.getPrefHeight());
        mapBox.setStyle("-fx-background-color: #796e59");
        buildingBox.setPrefWidth(stage.getWidth()*4/6);
        miniMapBox.setPrefWidth(stage.getWidth() / 6);
        miniMapBox.setStyle("-fx-background-color: #6c6cb4");
        infoBox.setPrefWidth(stage.getWidth() / 6);
        infoBox.setStyle("-fx-background-color: #ee9a73");
        allBuildings();
    }

    private void allBuildings(){
        for(BuildingTypeName buildingTypeName : BuildingTypeName.values()){
            VBox vBox = new VBox(2);
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            buildingBox.getItems().add(vBox);
            vBox.getChildren().add(new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingTypeName.toString().toLowerCase() + ".png").toString()))));
            vBox.getChildren().add(new Label(BuildingType.getBuildingTypeByName(buildingTypeName).getName().toString().replaceAll("_", " ")));
        }
    }

    public void castle() {
        setBuildingBox(BuildingCategory.CASTLE);
    }

    public void farm() {
        setBuildingBox(BuildingCategory.FARM);
    }

    public void foodProcessing() {
        setBuildingBox(BuildingCategory.FOOD_PROCESSING);
    }

    public void industry() {
        setBuildingBox(BuildingCategory.INDUSTRY);
    }

    public void town() {
        setBuildingBox(BuildingCategory.TOWN);
    }

    public void weapon() {
        setBuildingBox(BuildingCategory.WEAPON);
    }

    private void setBuildingBox(BuildingCategory category){
        buildingBox.getItems().clear();
        ArrayList<BuildingType> buildings = new ArrayList<>();
        for(BuildingTypeName buildingTypeName : BuildingTypeName.values()){
            if(BuildingType.getBuildingTypeByName(buildingTypeName) == null) continue; //TODO delete this line
            if(BuildingType.getBuildingTypeByName(buildingTypeName).getCategory().equals(category))
                buildings.add(BuildingType.getBuildingTypeByName(buildingTypeName));
        }

        for(BuildingType buildingType : buildings){
            VBox vBox = new VBox(2); //TODO set spacing
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            buildingBox.getItems().add(vBox);
            vBox.getChildren().add(new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingType.getName() + ".png").toString()))));
            vBox.getChildren().add(new Label(buildingType.getName().toString().replaceAll("_", " ")));
        }
    }
}