package org.example.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.controller.MapMenuController;
import org.example.model.Stronghold;
import org.example.model.game.Item;
import org.example.model.game.RockType;
import org.example.model.game.TreeType;
import org.example.model.game.buildings.buildingconstants.BuildingCategory;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Block;
import org.example.model.game.envirnmont.BlockTexture;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.messages.CustomizeMapMessages;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameMenuGFXController {
    public ScrollPane mapBox;
    public Rectangle faceImage;
    public Rectangle bookImage;
    public Rectangle edge;
    private ExtendedBlock[][] mapView;
    public HBox controlBox;
    public ListView<VBox> buildingBox;
    public Pane miniMapBox;
    public Pane infoBox;
    private static Stage stage;
    private static Popup showingBlockInfoPopup;

    public void prepareGame(Stage stage) {
        GameMenuGFXController.stage = stage;
        System.out.println(stage.getHeight());
        controlBox.setPrefHeight(stage.getHeight() / 5);
        controlBox.setStyle("-fx-background-color: #171817");
        mapBox.setPrefHeight(stage.getHeight() - controlBox.getPrefHeight());
        initializeMapView();
        buildingBox.setPrefWidth(stage.getWidth()*4/6);
        miniMapBox.setPrefWidth(stage.getWidth() / 6);
        miniMapBox.setStyle("-fx-background-color: #6c6cb4");
        infoBox.setPrefWidth(stage.getWidth() / 6);
        infoBox.setStyle("-fx-background-color: #ee9a73");
        allBuildings();
    }

    @FXML
    public void initialize(){
        bookImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/book.jpeg")).toString())));
        edge.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/edge.png")).toString())));
        int popularity = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().get();
        faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/faces/face" + popularity / 10 + ".png")).toString())));
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().addListener((observable, oldValue, newValue) -> {
            faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/faces/face" + newValue.intValue() / 10 + ".png")).toString())));
        });
    }

    private void initializeMapView() {
        CommonGFXActions.setMapScrollPaneProperties(mapBox);
        mapView=Stronghold.getCurrentMapGraphics();
        for (int i = 0; i < mapView.length; i++) {
            for (int j = 0; j < mapView.length; j++) {
                Polygon blockView = mapView[i][j].getBlockView();
                Coordinate coordinate = Stronghold.getPolygonCoordinateMap().get(blockView);
                blockView.setOnMousePressed(event -> {
                    if (event.isSecondaryButtonDown()) {
                        // TODO: add selection actions
                    }
                });
                blockView.setOnMouseEntered(mouseEvent -> {
                    if(mouseEvent.isPrimaryButtonDown() || mouseEvent.isSecondaryButtonDown()) return;
                    showingBlockInfoPopup=createBlockInfoPopup(coordinate);
                    showingBlockInfoPopup.setAnchorX(mouseEvent.getSceneX());
                    showingBlockInfoPopup.setAnchorY(mouseEvent.getSceneY());
                    showingBlockInfoPopup.show(stage);
                });
                blockView.setOnMouseExited(mouseEvent -> showingBlockInfoPopup.hide());
            }
        }
        mapBox.setContent(Stronghold.getMapGroupGFX());
        mapBox.setHvalue(0.5);
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

            Popup popup = createPopup(buildingType);
            vBox.hoverProperty().addListener((Observable, oldValue, newValue) -> {
                if(newValue) popup.show(stage);
                else popup.hide();
            });
        }
    }

    private static Popup createPopup(BuildingType buildingType){
        Popup popup = new Popup();
        Label label = new Label();
        String string = "hitpoint: " + buildingType.getMaxHitPoint();
        string += "\ncost: " + buildingType.getBuildingCost();
        if(buildingType.getResourcesNeeded() != null) {
            for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeeded().entrySet()) {
                string += ("\n" + entry.getKey().getName() + " needed: " + entry.getValue());
            }
        }

        string += ("\nemployee count: " + buildingType.getEmployeeCount());
        popup.getContent().add(label);
        label.setText(string);
        popup.setAnchorX(500);
        popup.setAnchorY(300);
        return popup;
    }

    private Popup createBlockInfoPopup(Coordinate blockPosition) {
        Popup popup=new Popup();
        Label label=new Label(MapMenuController.showDetailsExtended(blockPosition));
        label.setStyle("-fx-text-fill: rgba(211,234,216,0.78)");
        VBox container=new VBox(label);
        container.setBackground(Background.fill(new Color(0, 0, 0, 1)));
        container.setMouseTransparent(true);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.CENTER);
        popup.getContent().add(container);
        return popup;
    }
}