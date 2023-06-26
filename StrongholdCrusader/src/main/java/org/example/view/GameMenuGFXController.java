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
import org.example.model.game.buildings.BuildingLists;
import org.example.model.game.buildings.buildingconstants.BuildingTypeName;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.ExtendedBlock;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.unitconstants.MilitaryUnitRole;
import org.example.model.game.units.unitconstants.RoleName;

import java.util.*;

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
    public static Stage stage;
    private Coordinate selectionStartCoordinate;
    private LinkedList<ExtendedBlock> selectedBlocks;
    private HashMap<RoleName, Integer> selectedRoleCountMap;
    private BuildingTypeName buildingTypeName;

    public void prepareGame(Stage stage) {
        GameMenuGFXController.stage = stage;
        scrollPaneContent = Stronghold.getMapGroupGFX();
        System.out.println(stage.getHeight());
        controlBox.setPrefHeight(stage.getHeight() / 5);
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
        buildingBox.getItems().addAll(BuildingLists.allBuildings.getItems());
        selectedBlocks = new LinkedList<>();
        selectedRoleCountMap = new HashMap<>();
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
        buildingBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Label label = (Label) newValue.getChildren().get(1);
                String string = label.getText().replaceAll(" ", "_");
                buildingTypeName = BuildingTypeName.getBuildingTypeNameByNameString(string);
            } else buildingTypeName = null;
        });
        initializeMiniMap();
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
                    if (buildingTypeName != null) {
                        ExtendedBlock extendedBlock = Stronghold.getCurrentBattle().getBattleMap().getExtendedBlockByRowAndColumn(coordinate);
                        extendedBlock.setBuilding(coordinate, buildingTypeName);
                        Popup popup = buildingDetails(coordinate, buildingTypeName);
                        extendedBlock.getObject().setOnMouseEntered(mouseEvent1 -> {
                            popup.setAnchorX(mouseEvent1.getSceneX() + 5);
                            popup.setAnchorY(mouseEvent.getSceneY() + 5);
                            popup.show(stage);
                        });
                        extendedBlock.getObject().setOnMouseExited(mouseEvent1 -> popup.hide());
                        if (!scrollPaneContent.getChildren().contains(extendedBlock.getObject()))
                            scrollPaneContent.getChildren().add(extendedBlock.getObject());
                    }
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
        selectedTroopsInfoPane.getChildren().clear();
        for (ExtendedBlock selectedBlock : selectedBlocks) {
            ArrayList<MilitaryUnit> selectedUnits = selectedBlock.getBlock().getSelectableMilitaryUnitsByGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
            for (MilitaryUnit selectedUnit : selectedUnits)
                selectedRoleCountMap.put(selectedUnit.getRole().getName(), selectedRoleCountMap.getOrDefault(selectedUnit.getRole().getName(), 0) + 1);
        }
        for (Map.Entry<RoleName, Integer> roleCountEntry : selectedRoleCountMap.entrySet())
            selectedTroopsInfoPane.getChildren().add(generateTroopBox(roleCountEntry.getKey(), roleCountEntry.getValue()));
    }

    private VBox generateTroopBox(RoleName type, int troopCount) {
        ImageView imageView = new ImageView(((MilitaryUnitRole) MilitaryUnitRole.getRoleByName(type)).getRoleListImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        Slider slider = new Slider(0, troopCount, troopCount);
        Label nameLabel = new Label(type + " | count = " + (int) slider.getValue());
        HBox troopInfo = new HBox(imageView, nameLabel);
        troopInfo.setAlignment(Pos.CENTER);
        slider.valueProperty().addListener(observable -> nameLabel.setText(type + " | count = " + (int) slider.getValue()));
        VBox result = new VBox(troopInfo, slider);
        result.setAlignment(Pos.CENTER);
        result.setPadding(new Insets(10, 40, 5, 40));
        return result;
    }

    private void initializeControls() {
        // TODO: add other initialization processes (state of troops, resources, ...)
        updateCurrentPlayerInfo();
    }

    private static Popup buildingDetails(Coordinate coordinate, BuildingTypeName buildingTypeName) {
        Popup popup = new Popup();
        VBox vBox = new VBox();
        String string = "coordinate: (" + coordinate.row + "," + coordinate.column + ")";
        string += "\nbuilding type: " + buildingTypeName.name();
        string += "\nhitpoint: " + Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(coordinate.row, coordinate.column).getBuilding().getHitPoint();
        Text text = new Text(string);
        vBox.getChildren().add(text);
        popup.getContent().add(vBox);
        vBox.setBackground(Background.fill(Color.BROWN));
        text.setFill(Color.BEIGE);
        vBox.setPadding(new Insets(7));
        return popup;
    }

    public void castle() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.castleBuildings.getItems());
    }

    public void farm() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.farmBuildings.getItems());
    }

    public void foodProcessing() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.foodProcessingBuildings.getItems());
    }

    public void industry() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.industryBuildings.getItems());
    }

    public void town() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.townBuildings.getItems());
    }

    public void weapon() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.weaponBuildings.getItems());
    }

    public void unknown() {
        buildingBox.getItems().clear();
        buildingBox.getItems().addAll(BuildingLists.unknown.getItems());
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
        });
        ImagePattern backImage = new ImagePattern(new Image(GameMenuGFXController.class.getResource("/images/icons/back.png").toString()));
        Rectangle back = new Rectangle(30, 30, backImage);
        first.getChildren().addAll(popularity, button, back);
        back.setOnMouseClicked(mouseEvent -> {
            controlBox.getChildren().remove(0);
            controlBox.getChildren().add(0, buildingContainer);
            buildingBox.getItems().clear();
            buildingBox.getItems().addAll(BuildingLists.allBuildings.getItems());
        });

        int foodRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFoodRate();
        int taxRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate();
        int fearRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate();
        int religionRate = Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getReligionCount();
        int total = foodRate + taxRate - fearRate + religionRate;
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
        foodRateContainer.setAlignment(Pos.CENTER);

        Slider taxRateSlider = new Slider(-3, 8, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getTaxRate());
        taxRateSlider.setBlockIncrement(1);
        Text taxRateText = new Text("tax rate: " + (int) taxRateSlider.getValue());
        taxRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            taxRateText.setText("tax rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setTaxRate(newValue.intValue());
        });
        VBox taxRateContainer = new VBox(5, taxRateSlider, taxRateText);
        taxRateContainer.setAlignment(Pos.CENTER);

        Slider fearRateSlider = new Slider(-5, 5, Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getFearRate());
        Text fearRateText = new Text("fear rate: " + (int) fearRateSlider.getValue());
        fearRateSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            fearRateText.setText("fear rate: " + newValue.intValue());
            Stronghold.getCurrentBattle().getGovernmentAboutToPlay().setFearRate(newValue.intValue());
        });
        VBox fearRateContainer = new VBox(5, fearRateSlider, fearRateText);
        fearRateContainer.setAlignment(Pos.CENTER);

        Text religionCount = new Text("religion count: " + Stronghold.getCurrentBattle().getGovernmentAboutToPlay().getReligionCount());
        ComboBox<String> religion = new ComboBox<>();
        religion.setPromptText("religion");
        religion.setMinHeight(25);
        religion.setMaxHeight(25);
        religion.getItems().add("have religion");
        religion.getItems().add("don't have religion");
        religion.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("have religion")) {
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay().addReligion();
                religionCount.setText("religion count: 1");
            } else {
                Stronghold.getCurrentBattle().getGovernmentAboutToPlay().removeReligion();
                religionCount.setText("religion count: 0");
            }
        });
        VBox religionContainer = new VBox(5, religion, religionCount);
        religionContainer.setAlignment(Pos.CENTER);

        Rectangle back = new Rectangle(50, 30, new ImagePattern(new Image(Objects.requireNonNull(GameMenuGFXController.class.getResource("/images/icons/backHand.png")).toString())));
        back.setOnMouseClicked(mouseEvent -> {
            popup.hide();
            popularityFactors();
        });

        sliders.getChildren().addAll(foodRateContainer, taxRateContainer, fearRateContainer, religionContainer, back);
        sliders.setPadding(new Insets(25));
        Image image = new Image(GameMenu.class.getResource("/images/backgrounds/greenSheet2.jpeg").toExternalForm(), 197, 316, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        sliders.setBackground(new Background(backgroundImage));
        popup.getContent().add(sliders);

        return popup;
    }

    public void initializeMiniMap() {
        GridPane miniMap=new GridPane();
        int size = Stronghold.getCurrentBattle().getBattleMap().getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle miniBlock = new Rectangle(miniMapBox.getWidth()/size,miniMapBox.getHeight()/size);
                System.out.println(Double.toString(miniMapBox.getHeight())+"..."+Double.toString(miniBlock.getHeight()));
                miniBlock.setFill(Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(j, i).getTexture().getColor());
                miniMap.getChildren().add(miniBlock);
                GridPane.setConstraints(miniBlock, j, i);
            }
        }
        miniMapBox.getChildren().add(miniMap);
    }
}