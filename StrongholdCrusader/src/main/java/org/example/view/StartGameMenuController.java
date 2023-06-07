package org.example.view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.model.User;

import java.util.ArrayList;

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
        // TODO: uncomment this later
//        playerCountSlider.setMax(Math.min(8, User.getUsers().size()));
        playerCountSlider.setMax(8);
        playerCountSlider.setValue(4);
    }

    private void prepareSize() {
        sizeToggleGroup = new ToggleGroup();
        int[] sizes = {200, 400};
        RadioButton option = null;
        for (int size : sizes) {
            option = new RadioButton(String.valueOf(size));
            option.setToggleGroup(sizeToggleGroup);
            option.getStyleClass().add("moderate-spacing");
            sizeBox.getChildren().add(option);
        }
        sizeToggleGroup.selectToggle(option);
    }

    private void preparePlayersInfo() {
        playersInfoBox.add(new PlayerInfoStartGame("you", this, true).container, 0, 0);
        for (int i = 1; i < (int) playerCountSlider.getValue(); i++)
            playersInfoBox.add(new PlayerInfoStartGame("player" + (i + 1), this, false).container, i % 4, i / 4);
    }

    public int getSize() {
        return Integer.parseInt(((RadioButton) sizeToggleGroup.getSelectedToggle()).getText());
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
                // TODO: link to logged in user
//                username.setText(Stronghold.getCurrentUser().getUsername());
                username.setText("placeholder");
            } else username.setPromptText("username");
            colorPicker = new ColorPicker();
            row = new TextField();
            row.setPromptText("keep's row");
            column = new TextField();
            column.setPromptText("keep's column");
            errorMessage = new Label();
            errorMessage.setWrapText(true);
            username.textProperty().addListener(observable -> {
                isUsernameValid = User.getUserByUsername(username.getText()) != null;
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
                    isKeepPositionValid = !duplicateCoordinate(controller);
                    updateErrorMessage();
                }
            });
        }

        private boolean duplicateCoordinate(StartGameMenuController controller) {
            if (row.getText().isEmpty() || column.getText().isEmpty()) return false;
            for (PlayerInfoStartGame playerInfo : allPlayerInfos)
                if (playerInfo != this && playerInfo.row.getText().equals(this.row.getText()) && playerInfo.column.getText().equals(this.column.getText()))
                    return true;
            return false;
        }

        private void updateErrorMessage() {
            if (!isKeepPositionValid) errorMessage.setText("duplicate keep position");
            else if (!isUsernameValid) errorMessage.setText("user with this username doesn't seam to exist");
            else errorMessage.setText("");
        }
    }
}
