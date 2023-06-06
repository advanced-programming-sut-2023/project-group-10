package org.example.view;

import javafx.geometry.Insets;
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
        preparePlayersInfo();
    }

    private void preparePlayerCount() {
        settingsContainer.setMaxWidth(stage.getWidth() / 4);
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
        for (int i = 0; i < (int) playerCountSlider.getValue(); i++)
            playersInfoBox.add(new PlayerInfoStartGame(i + 1, this).container, i % 4, i / 4);
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
        private boolean isUsernameValid=true;
        private boolean isKeepPositionValid=true;

        public PlayerInfoStartGame(int number, StartGameMenuController controller) {
            container = new VBox();
            container.setAlignment(Pos.BOTTOM_CENTER);
            container.setSpacing(10);
            container.getStyleClass().add("moderate-spacing");
            container.getStyleClass().add("parent--moderate-spacing");
            header = new Label("player" + number);
            username = new TextField();
            username.setPromptText("username");
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
            row.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!row.getText().matches("\\d*") || !row.getText().isEmpty() && Integer.parseInt(row.getText()) > controller.getSize()) {
                    row.setText(oldValue);
                    errorMessage.setText("invalid row number");
                } else {
                    isKeepPositionValid = !duplicateCoordinate();
                    updateErrorMessage();
                }
            });
            column.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!column.getText().matches("\\d*") || !column.getText().isEmpty() && Integer.parseInt(column.getText()) > controller.getSize()) {
                    column.setText(oldValue);
                    errorMessage.setText("invalid column number");
                } else {
                    isKeepPositionValid = !duplicateCoordinate();
                    updateErrorMessage();
                }
            });
            container.getChildren().addAll(header, username, colorPicker, row, column, errorMessage);
            allPlayerInfos.add(this);
        }

        private boolean duplicateCoordinate() {
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
