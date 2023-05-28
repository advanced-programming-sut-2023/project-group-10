package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.SignupMenuController;
import org.example.model.Stronghold;
import org.example.view.enums.messages.SignupMenuMessages;

public class ChangeUsername extends Application {

    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        ChangeUsername.stage = stage;

        Pane pane = new FXMLLoader(ChangeUsername.class.getResource("/view/changeUsername.fxml")).load();
        Button button = new Button("back");
        pane.getChildren().add(button);
        button.setTranslateX(30);
        button.setTranslateY(30);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                new ProfileMenu2().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox main = new VBox(20);
        main.setTranslateX(200);
        main.setTranslateY(100);
        main.setAlignment(Pos.CENTER);
        pane.getChildren().add(main);

        Text currentUsername = new Text();
        TextField newUsername = new TextField();
        newUsername.setPromptText("new username");
        Text usernameDetail = new Text();
        Button submit = new Button("submit");
        submit.setOnMouseClicked(mouseEvent -> {
            try {
                submit(newUsername.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        main.getChildren().addAll(currentUsername, newUsername, usernameDetail, submit);
        newUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            checkUsername(newValue, usernameDetail);
        });

        Scene scene = new Scene(pane);
        stage.setTitle("Change Username");
        stage.setScene(scene);
        stage.show();
    }

    private void submit(String username) throws Exception{
        Stronghold.getCurrentUser().setUsername(username);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Username change successful");
        alert.setContentText("Your username was changed successfully");
        new ProfileMenu2().start(stage);
    }

    private void checkUsername(String username, Text usernameDetail){
        SignupMenuMessages message = SignupMenuController.checkUsername(username);

        if(message.equals(SignupMenuMessages.INVALID_USERNAME_FORMAT))
            usernameDetail.setText("invalid username format!");
        else if(message.equals(SignupMenuMessages.USER_EXISTS))
            usernameDetail.setText("username exists!");
        else usernameDetail.setText("valid username!");
    }
}
