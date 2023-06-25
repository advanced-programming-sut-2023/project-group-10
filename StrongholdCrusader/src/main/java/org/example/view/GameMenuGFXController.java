package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.controller.MapMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.Item;
import org.example.model.game.buildings.buildingconstants.BuildingCategory;
import org.example.model.game.buildings.buildingconstants.BuildingType;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.units.unitconstants.RoleName;
import org.example.model.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class GameMenuGFXController {
    public ScrollPane mapBox;
    private Group scrollPaneContent;
    public BorderPane turnPane;
    public Rectangle currentPlayerAvatar;
    public Label currentPlayerName;
    public Button nextPlayerButton;
    public VBox selectedTroopsInfoPane;
    public Rectangle faceImage;
    public Rectangle bookImage;
    public Rectangle edge;
    public VBox buildingContainer;
    public HBox controlBox;
    public ListView<VBox> buildingBox;
    public Pane miniMapBox;
    public Pane infoBox;
    private Stage stage;
    private Coordinate selectionStartCoordinate;
    private LinkedList<ExtendedBlock> selectedBlocks;

    public void prepareGame(Stage stage) {
        this.stage = stage;
        scrollPaneContent = Stronghold.getMapGroupGFX();
        System.out.println(stage.getHeight());
        controlBox.setPrefHeight(stage.getHeight() / 5);
        controlBox.setStyle("-fx-background-color: #171817");
        mapBox.setPrefWidth(stage.getWidth() * 5 / 6);
        mapBox.setPrefHeight(stage.getHeight() - controlBox.getPrefHeight());
        turnPane.setPrefWidth(stage.getWidth() / 6);
        turnPane.setPrefHeight(mapBox.getPrefHeight());
        currentPlayerAvatar.setWidth(turnPane.getPrefWidth() / 2);
        currentPlayerAvatar.setHeight(turnPane.getPrefWidth() / 2);
        initializeMapView();
        initializeControls();
        buildingBox.setPrefWidth(stage.getWidth() * 4 / 6);
        miniMapBox.setPrefWidth(stage.getWidth() / 6);
        miniMapBox.setStyle("-fx-background-color: #6c6cb4");
        infoBox.setPrefWidth(stage.getWidth() / 6);
        infoBox.setStyle("-fx-background-color: #ee9a73");
        allBuildings();
        selectedBlocks = new LinkedList<>();
    }

    @FXML
    public void initialize() {
        bookImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/book.jpeg")).toString())));
        edge.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/backgrounds/edge.png")).toString())));

        int popularity = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().get();
        faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/faces/face" + popularity / 10 + ".png")).toString())));
        Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getPopularity().addListener((observable, oldValue, newValue) -> {
            faceImage.setFill(new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/faces/face" + newValue.intValue() / 10 + ".png")).toString())));
        });
        faceImage.setOnMouseClicked(mouseEvent -> popularityFactors());
    }

    private void initializeMapView() {
        CommonGFXActions.setMapScrollPaneProperties(mapBox);
        org.example.model.game.envirnmont.Map gameMap = Stronghold.getCurrentBattle().getBattleMap();
        ExtendedBlock[][] mapView = gameMap.getBlocksGraphics();
        for (int i = 0; i < mapView.length; i++) {
            for (int j = 0; j < mapView.length; j++) {
                Polygon blockView = mapView[i][j].getBlockView();
                Coordinate coordinate = gameMap.getPolygonCoordinateMap().get(blockView);
                blockView.setOnMousePressed(mouseEvent -> {
                    if (mouseEvent.isSecondaryButtonDown()) selectionStartCoordinate = coordinate;
                });
                blockView.setOnMouseEntered(mouseEvent -> {
                    if (mouseEvent.isPrimaryButtonDown() || mouseEvent.isSecondaryButtonDown()) return;
                    if (selectionStartCoordinate == null)
                        Tooltip.install(blockView, new Tooltip(MapMenuController.showDetailsExtended(coordinate)));
                    else {
                        Coordinate coordinate1 = new Coordinate(Math.min(selectionStartCoordinate.row, coordinate.row), Math.min(selectionStartCoordinate.column, coordinate.column));
                        Coordinate coordinate2 = new Coordinate(Math.max(selectionStartCoordinate.row, coordinate.row), Math.max(selectionStartCoordinate.column, coordinate.column));
                        for (int k = coordinate1.row; k <= coordinate2.row; k++) {
                            for (int l = coordinate1.column; l <= coordinate2.column; l++)
                                switchSelectionState(new Coordinate(k, l));
                        }
                        selectionStartCoordinate = null;
                    }
                });
                blockView.setOnMouseClicked(mouseEvent -> {
                    if (selectionStartCoordinate != null) {
                        switchSelectionState(selectionStartCoordinate);
                        selectionStartCoordinate = null;
                    }
                });
            }
        }
        mapBox.setContent(scrollPaneContent);
        mapBox.setHvalue(0.5);
    }

    private void switchSelectionState(Coordinate coordinate) {
        ExtendedBlock extendedBlock = Stronghold.getCurrentBattle().getBattleMap().getExtendedBlockByRowAndColumn(coordinate);
        if (selectedBlocks.contains(extendedBlock)) {
            unselectBlockView(extendedBlock);
            selectedBlocks.remove(extendedBlock);
        } else selectBlock(extendedBlock);
    }

    private void unselectBlockView(ExtendedBlock extendedBlock) {
        extendedBlock.getBlockView().setStroke(null);
        updateSelectedBlocksPane();
    }

    private void selectBlock(ExtendedBlock extendedBlock) {
        selectedBlocks.add(extendedBlock);
        extendedBlock.getBlockView().setStroke(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getColor());
        extendedBlock.getBlockView().setStrokeType(StrokeType.INSIDE);
        extendedBlock.getBlockView().setStrokeWidth(2);
        updateSelectedBlocksPane();
    }

    private void updateSelectedBlocksPane() {
        //TODO
    }

    private VBox generateTroopBox(RoleName type, int troopCount) {
        VBox result = new VBox();
        ImageView imageView = new ImageView(type.getRoleListImage(Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getColor()));
        imageView.setPreserveRatio(true);
        Label nameLabel = new Label(type.toString());
        // TODO: add count mechanism
        return result;
    }

    private void initializeControls() {
        // TODO: add other initialization processes (state of troops, resources, ...)
        updateCurrentPlayerInfo();
    }

    private void allBuildings() {
        for (BuildingTypeName buildingTypeName : BuildingTypeName.values()) {
            VBox vBox = new VBox(2);
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            buildingBox.getItems().add(vBox);
            Circle circle = new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingTypeName.toString().toLowerCase() + ".png").toString())));
            vBox.getChildren().add(circle);
            vBox.getChildren().add(new Label(BuildingType.getBuildingTypeByName(buildingTypeName).getName().toString().replaceAll("_", " ")));
            Popup popup = createPopup(BuildingType.getBuildingTypeByName(buildingTypeName));
            circle.setOnMouseEntered(mouseEvent -> {
                popup.setAnchorX(mouseEvent.getSceneX() + 2);
                popup.setAnchorY(mouseEvent.getSceneY() + 2);
                popup.show(stage);
            });
            circle.setOnMouseExited(mouseEvent -> popup.hide());
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

    private void popularityFactors() {
        HBox mainPane = new HBox(30);
        mainPane.setPrefHeight(stage.getHeight() / 5);
        mainPane.setPrefWidth(stage.getWidth() * 4 / 6);
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setBackground(Background.fill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/backgrounds/menu.jpeg").toString()))));

        VBox first = new VBox(10);
        first.setTranslateX(-100);
        first.setAlignment(Pos.CENTER);
        Text popularity = new Text("Popularity");
        popularity.setFont(Font.font("Helvetica", FontPosture.ITALIC, 20));
        Button button = new Button("change rate");
        button.setOnMouseClicked(mouseEvent -> {
            Popup popup = changePopularityMenu();
            popup.show(stage);
            System.out.println(popup.getWidth() + " " + popup.getHeight());
        });
        ImagePattern backImage = new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/icons/back.png").toString()));
        Rectangle back = new Rectangle(30, 30, backImage);
        first.getChildren().addAll(popularity, button, back);
        back.setOnMouseClicked(mouseEvent -> {
            controlBox.getChildren().remove(0);
            controlBox.getChildren().add(0, buildingContainer);
            allBuildings();
        });

        int foodRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate();
        int taxRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate();
        int fearRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate();
        int religionRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getReligionCount();
        int total = foodRate + taxRate + fearRate + religionRate;
        HBox totalContainer = comingMonth(total);
        totalContainer.setTranslateX(-20);
        totalContainer.setTranslateY(50);

        VBox second = faces();
        VBox third = foodAndTax(foodRate, taxRate);
        VBox forth = fearAndReligion(fearRate, religionRate);
        mainPane.getChildren().addAll(first, second, third, forth, totalContainer);

        controlBox.getChildren().remove(0);
        controlBox.getChildren().add(0, mainPane);
    }

    private static VBox faces() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setTranslateX(-30);
        vbox.setTranslateY(20);
        Rectangle greenFace = new Rectangle(40, 60, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/greenFace.png").toString())));
        Rectangle redFace = new Rectangle(40, 60, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/redFace.png").toString())));
        redFace.setTranslateX(greenFace.getTranslateX() + 15);
        redFace.setTranslateY(greenFace.getTranslateY() - 30);
        vbox.getChildren().addAll(greenFace, redFace);
        return vbox;
    }

    private static VBox foodAndTax(int foodRateNum, int taxRateNum) {
        VBox vBox1 = new VBox(10);
        vBox1.setAlignment(Pos.CENTER);

        HBox hBox1 = new HBox(5);
        hBox1.setAlignment(Pos.CENTER_LEFT);
        Text rate = new Text(Integer.toString(foodRateNum));
        Rectangle foodFace = new Rectangle(30, 45);
        Text food = new Text("Food");
        rate.setFont(Font.font("Helvetica", 15));
        food.setFont(Font.font("Helvetica", 15));
        hBox1.getChildren().addAll(rate, foodFace, food);
        String color;
        if (foodRateNum > 0) color = "green";
        else if (foodRateNum == 0) color = "yellow";
        else color = "red";
        foodFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toString())));

        HBox hBox2 = new HBox(5);
        hBox2.setAlignment(Pos.CENTER_LEFT);
        Text taxRate = new Text(Integer.toString(taxRateNum));
        Rectangle taxFace = new Rectangle(30, 45);
        Text tax = new Text("Tax");
        taxRate.setFont(Font.font("Helvetica", 15));
        tax.setFont(Font.font("Helvetica", 15));
        hBox2.getChildren().addAll(taxRate, taxFace, tax);
        if (taxRateNum > 0) color = "green";
        else if (taxRateNum == 0) color = "yellow";
        else color = "red";
        taxFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toString())));

        vBox1.getChildren().addAll(hBox1, hBox2);
        return vBox1;
    }

    private static VBox fearAndReligion(int fearRateNum, int religionRateNum) {
        VBox vBox2 = new VBox(10);
        vBox2.setAlignment(Pos.CENTER);

        HBox fearContainer = new HBox(5);
        fearContainer.setAlignment(Pos.CENTER_LEFT);
        Text fearRate = new Text(Integer.toString(fearRateNum));
        Rectangle fearFace = new Rectangle(30, 45);
        Text fear = new Text("Fear Factor");
        fear.setFont(Font.font("Helvetica", 15));
        fearRate.setFont(Font.font("Helvetica", 15));
        fearContainer.getChildren().addAll(fearRate, fearFace, fear);
        String color;
        if (fearRateNum > 0) color = "red";
        else if (fearRateNum == 0) color = "yellow";
        else color = "green";
        fearFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toExternalForm())));

        HBox religionContainer = new HBox(5);
        religionContainer.setAlignment(Pos.CENTER_LEFT);
        Text religionRate = new Text(Integer.toString(religionRateNum));
        Rectangle religionFace = new Rectangle(30, 45);
        Text religion = new Text("Religion");
        religionRate.setFont(Font.font("Helvetica", 15));
        religion.setFont(Font.font("Helvetica", 15));
        religionContainer.getChildren().addAll(religionRate, religionFace, religion);
        if (religionRateNum > 0) color = "green";
        else color = "yellow";
        religionFace.setFill(new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toExternalForm())));

        vBox2.getChildren().addAll(fearContainer, religionContainer);
        return vBox2;
    }

    private static HBox comingMonth(int total) {
        String totalCount;
        if (total > 0) totalCount = "+" + total;
        else totalCount = Integer.toString(total);
        HBox totalContainer = new HBox(10);
        totalContainer.setAlignment(Pos.CENTER);
        totalContainer.getChildren().add(new Text("In the coming month: " + totalCount));

        String color;
        if (total > 0) color = "green";
        else if (total == 0) color = "yellow";
        else color = "red";

        ImagePattern imagePattern = new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/faces/" + color + "Face.png").toString()));
        Rectangle rectangle = new Rectangle(20, 30, imagePattern);
        totalContainer.getChildren().add(rectangle);
        return totalContainer;
    }

    private void setBuildingBox(BuildingCategory category) {
        buildingBox.getItems().clear();
        ArrayList<BuildingType> buildings = new ArrayList<>();
        for (BuildingTypeName buildingTypeName : BuildingTypeName.values()) {
            if (BuildingType.getBuildingTypeByName(buildingTypeName) == null) continue; //TODO delete this line
            if (BuildingType.getBuildingTypeByName(buildingTypeName).getCategory().equals(category))
                buildings.add(BuildingType.getBuildingTypeByName(buildingTypeName));
        }

        for (BuildingType buildingType : buildings) {
            VBox vBox = new VBox(2); //TODO set spacing
            vBox.setAlignment(Pos.CENTER);
            vBox.setBackground(new Background(RandomGenerator.setBackground("/images/backgrounds/lightBrown1.JPG")));
            vBox.setPadding(new Insets(0, 10, 0, 10));
            buildingBox.getItems().add(vBox);
            Circle circle = new Circle(40, new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/buildings/" + buildingType.getName() + ".png").toString())));
            vBox.getChildren().add(circle);
            vBox.getChildren().add(new Label(buildingType.getName().toString().replaceAll("_", " ")));

            Popup popup = createPopup(buildingType);
            circle.setOnMouseEntered(mouseEvent -> {
                popup.setAnchorX(mouseEvent.getSceneX() + 2);
                popup.setAnchorY(mouseEvent.getSceneY() + 2);
                popup.show(stage);
            });
            circle.setOnMouseExited(mouseEvent -> popup.hide());
        }
    }

    private static Popup createPopup(BuildingType buildingType) {
        Popup popup = new Popup();
        Label label = new Label();
        String string = "hitpoint: " + buildingType.getMaxHitPoint();
        string += "\ncost: " + buildingType.getBuildingCost();
        if (buildingType.getResourcesNeeded() != null) {
            for (Map.Entry<Item, Integer> entry : buildingType.getResourcesNeeded().entrySet()) {
                string += ("\n" + entry.getKey().getName() + " needed: " + entry.getValue());
            }
        }
        string += ("\nemployee count: " + buildingType.getEmployeeCount());
        label.setText(string);
        VBox vBox = new VBox(label);
        vBox.setPadding(new Insets(5));
        label.setStyle("-fx-text-fill: rgba(211,234,216,0.78)");
        popup.getContent().add(vBox);
        vBox.setBackground(Background.fill(Color.BLACK));
        return popup;
    }

    private Popup createBlockInfoPopup(Coordinate blockPosition) {
        Popup popup = new Popup();
        Label label = new Label(MapMenuController.showDetailsExtended(blockPosition));
        label.setStyle("-fx-text-fill: white");
        VBox container = new VBox(label);
        container.setBackground(Background.fill(new Color(0, 0, 0, 1)));
        container.setMouseTransparent(true);
        label.setMouseTransparent(true);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.CENTER);
        popup.getContent().add(container);
        return popup;
    }

    public void goToNextPlayer() {
        // TODO: handle animations and potential bugs
        Stronghold.getCurrentBattle().goToNextPlayer();
        updateCurrentPlayerInfo();
        for (ExtendedBlock selectedBlock : selectedBlocks)
            unselectBlockView(selectedBlock);
        selectedBlocks.clear();
    }

    private void updateCurrentPlayerInfo() {
        User currentPlayer = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getOwner();
        currentPlayerAvatar.setFill(new ImagePattern(new Image(currentPlayer.getAvatar())));
        currentPlayerName.setText(currentPlayer.getUsername() + "\n~" + currentPlayer.getNickname() + "~");
    }

    private Popup changePopularityMenu() {
        Popup popup = new Popup();

        VBox sliders = new VBox(20);
        sliders.setAlignment(Pos.CENTER);

        Slider foodRateSlider = new Slider(-2, 2, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate());
        foodRateSlider.setBlockIncrement(1);
        Text foodRateText = new Text("food rate: " + (int) foodRateSlider.getValue());
        foodRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            foodRateText.setText("food rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFoodRate(newValue.intValue());
        });
        VBox foodRateContainer = new VBox(5, foodRateSlider, foodRateText);

        Slider taxRateSlider = new Slider(-3, 8, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate());
        taxRateSlider.setBlockIncrement(1);
        Text taxRateText = new Text("tax rate: " + (int) taxRateSlider.getValue());
        taxRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            taxRateText.setText("tax rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(newValue.intValue());
        });
        VBox taxRateContainer = new VBox(5, taxRateSlider, taxRateText);

        Slider fearRateSlider = new Slider(-5, 5, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate());
        Text fearRateText = new Text("fear rate: " + (int) fearRateSlider.getValue());
        fearRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            fearRateText.setText("fear rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFearRate(newValue.intValue());
        });
        VBox fearRateContainer = new VBox(5, fearRateSlider, fearRateText);

        Rectangle back = new Rectangle(50, 30, new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/icons/backHand.png")).toString())));
        back.setOnMouseClicked(mouseEvent -> popup.hide());

        sliders.getChildren().addAll(foodRateContainer, taxRateContainer, fearRateContainer, back);
        sliders.setPadding(new Insets(25));
        Image image = new Image(GameMenu.class.getResource("/images/backgrounds/greenSheet.jpeg").toExternalForm(), 180, 248, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        sliders.setBackground(new Background(backgroundImage));
        popup.getContent().add(sliders);

        return popup;
    }
}