package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.controller.GameMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.envirnmont.Map;

import java.util.ArrayList;
import java.util.HashMap;

public class StartGameMenuController {
    private Stage stage;
    public VBox settingsContainer;
    public Label playerCountLabel;
    public Slider playerCountSlider;
    public HBox sizeBox;
    public GridPane playersInfoBox;
    private ToggleGroup sizeToggleGroup;

    public void prepareMenu(Stage stage) {
        this.stage = stage;
        preparePlayerCount();
        prepareSize();
    }

    private void preparePlayerCount() {
        settingsContainer.setMaxWidth(stage.getWidth() / 2);
        playerCountSlider.valueProperty().addListener(observable -> {
            playerCountLabel.setText("player count = " + (int) playerCountSlider.getValue());
            playersInfoBox.getChildren().clear();
            PlayerInfoStartGame.allPlayerInfos.clear();
            preparePlayersInfo();
        });
        playerCountSlider.setMin(2);
        playerCountSlider.setMax(Math.min(8, User.getUsers().size()) + 0.1);
        playerCountSlider.setValue(playerCountSlider.getMin());
    }

    private void prepareSize() {
        sizeToggleGroup = new ToggleGroup();
        int[] sizes = {200, 400};
        RadioButton option;
        for (int size : sizes) {
            option = new RadioButton(String.valueOf(size));
            option.setToggleGroup(sizeToggleGroup);
            option.getStyleClass().add("moderate-spacing");
            sizeBox.getChildren().add(option);
        }
        sizeToggleGroup.selectToggle(sizeToggleGroup.getToggles().get(0));
    }

    private void preparePlayersInfo() {
        playersInfoBox.add(new PlayerInfoStartGame("you", this, true).container, 0, 0);
        for (int i = 1; i < (int) playerCountSlider.getValue(); i++)
            playersInfoBox.add(new PlayerInfoStartGame("player" + (i + 1), this, false).container, i % 4, i / 4);
    }

    public int getSize() {
        return Integer.parseInt(((RadioButton) sizeToggleGroup.getSelectedToggle()).getText());
    }

    public void startGame() throws Exception {
        if (!PlayerInfoStartGame.isInfoValid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid information");
            alert.setHeaderText("");
            alert.setContentText("invalid information, check fields and try again");
            alert.showAndWait();
            return;
        }
        HashMap<String, Color> colors = new HashMap<>();
        HashMap<String, Coordinate> keeps = new HashMap<>();
        for (PlayerInfoStartGame playerInfo : PlayerInfoStartGame.allPlayerInfos) {
            colors.put(playerInfo.username.getText(), playerInfo.colorPicker.getValue());
            keeps.put(playerInfo.username.getText(), new Coordinate(Integer.parseInt(playerInfo.row.getText()), Integer.parseInt(playerInfo.column.getText())));
        }
        GameMenuController.initializeGame(colors, keeps, new Map(getSize()));
        new CustomizeMapMenuGFX().start(SignupMenu.stage);
    }

    public void goBackToMainMenu() throws Exception {
        new MainMenuGFX().start(SignupMenu.stage);
    }

    static class PlayerInfoStartGame {
        static final ArrayList<PlayerInfoStartGame> allPlayerInfos = new ArrayList<>();
        final VBox container;
        final Label header;
        final TextField username;
        final ColorPicker colorPicker;
        final TextField row;
        final TextField column;
        final Label errorMessage;
        private boolean isUsernameValid = true;
        private boolean isKeepPositionValid = true;

        public PlayerInfoStartGame(String headerText, StartGameMenuController controller, boolean isLoggedIn) {
            container = new VBox();
            container.setAlignment(Pos.BOTTOM_CENTER);
            container.setSpacing(10);
            container.getStyleClass().add("moderate-spacing");
            header = new Label(headerText);
            username = new TextField();
            if (isLoggedIn) {
                username.setEditable(false);
                username.setText(Stronghold.getCurrentUser().getUsername());
            } else username.setPromptText("username");
            colorPicker = new ColorPicker();
            row = new TextField();
            row.setPromptText("keep's row");
            column = new TextField();
            column.setPromptText("keep's column");
            errorMessage = new Label();
            errorMessage.setWrapText(true);
            username.textProperty().addListener(observable -> {
                isUsernameValid = User.getUserByUsername(username.getText()) != null && !duplicateUsername();
                updateErrorMessage();
            });
            addPositionValidityListener(controller, row);
            addPositionValidityListener(controller, column);
            container.getChildren().addAll(header, username, colorPicker, row, column, errorMessage);
            allPlayerInfos.add(this);
        }

        public void addPositionValidityListener(StartGameMenuController controller, TextField number) {
            number.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!number.getText().matches("\\d*") || !number.getText().isEmpty() && Integer.parseInt(number.getText()) > controller.getSize())
                    number.setText(oldValue);
                else {
                    isKeepPositionValid = !duplicateCoordinate();
                    updateErrorMessage();
                }
            });
        }

        public static boolean isInfoValid() {
            for (PlayerInfoStartGame playerInfo : allPlayerInfos) {
                if (!playerInfo.isUsernameValid || !playerInfo.isKeepPositionValid) return false;
                if (playerInfo.username.getText().isEmpty() || playerInfo.row.getText().isEmpty() || playerInfo.column.getText().isEmpty())
                    return false;
            }
            return true;
        }

        private boolean duplicateCoordinate() {
            if (row.getText().isEmpty() || column.getText().isEmpty()) return false;
            for (PlayerInfoStartGame playerInfo : allPlayerInfos)
                if (playerInfo != this && playerInfo.row.getText().equals(this.row.getText()) && playerInfo.column.getText().equals(this.column.getText()))
                    return true;
            return false;
        }

        private boolean duplicateUsername() {
            if (username.getText().isEmpty()) return false;
            for (PlayerInfoStartGame playerInfo : allPlayerInfos)
                if (playerInfo != this && playerInfo.username.getText().equals(username.getText())) return true;
            return false;
        }

        private void updateErrorMessage() {
            if (!isKeepPositionValid) errorMessage.setText("duplicate keep position");
            else if (!isUsernameValid) {
                if (duplicateUsername()) errorMessage.setText("user has already been entered");
                else errorMessage.setText("user doesn't seem to exist");
            } else errorMessage.setText("");
        }
    }
}
