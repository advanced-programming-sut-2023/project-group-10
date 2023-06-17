package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.SignupMenuController;
import org.example.model.Stronghold;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.SignupMenuMessages;

import java.io.IOException;

public class ProfileMenu extends Application {
    public Circle avatar;
    public Text username;
    public Text password;
    public Text nickname;
    public Text email;
    public VBox mainButtons;
    public Pane mainPane;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        Pane pane = new FXMLLoader(ProfileMenu.class.getResource("/view/profileMenu.fxml")).load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    @FXML
    public void initialize(){
        //TODO set avatar
        /*username.setText(Stronghold.getCurrentUser().getUsername());
        password.setText(Stronghold.getCurrentUser().getPassword());
        nickname.setText(Stronghold.getCurrentUser().getNickname());
        email.setText(Stronghold.getCurrentUser().getEmail());*/
    }

    public void changeUsername() {
        Button back = new Button("back");
        back.setTranslateX(120);
        back.setTranslateY(160);
        mainPane.getChildren().add(back);

        VBox change = new VBox(20);
        change.setTranslateX(200);
        change.setTranslateY(100);
        change.setAlignment(Pos.CENTER);

        Text currentUsername = new Text(Stronghold.getCurrentUser().getUsername());
        TextField newUsername = new TextField();
        newUsername.setPromptText("new username");
        Text usernameDetail = new Text();
        Button submit = new Button("submit");
        submit.setOnMouseClicked(mouseEvent -> {
            if(usernameDetail.getText().equals("valid username!")) {
                try {
                    Stronghold.getCurrentUser().setUsername(newUsername.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Username change successful");
                    alert.setContentText("Your username was changed successfully");
                    mainPane.getChildren().remove(change);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change, back);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(currentUsername, newUsername, usernameDetail, submit);
        newUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            checkUsername(newValue, usernameDetail);
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changePassword(){
        Button back = new Button("back");
        back.setTranslateX(120);
        back.setTranslateY(160);
        mainPane.getChildren().add(back);

        VBox change = new VBox(20);
        change.setTranslateX(200);
        change.setTranslateY(100);
        change.setAlignment(Pos.CENTER);

        TextField currentPassword = new TextField();
        currentPassword.setPromptText("current password");
        Text currentPassDetail = new Text();
        TextField newPassword = new TextField();
        newPassword.setPromptText("new password");
        Text newPassDetail = new Text();

        Button submit = new Button("submit");
        submit.setOnMouseClicked(mouseEvent -> {
            if(newPassDetail.getText().equals("valid password!")) {
                if(!Stronghold.getCurrentUser().getPassword().equals(currentPassword.getText()))
                    currentPassDetail.setText("wrong password!");
                else {
                    try {
                        Stronghold.getCurrentUser().setPassword(newPassword.getText());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Password change successful");
                        alert.setContentText("Your password was changed successfully");
                        mainPane.getChildren().remove(change);
                        mainPane.getChildren().add(mainButtons);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change, back);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(currentPassword, currentPassDetail, newPassword, newPassDetail, submit);
        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPassword(newValue, newPassDetail);
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changeNickname(){
        Button back = new Button("back");
        back.setTranslateX(120);
        back.setTranslateY(160);
        mainPane.getChildren().add(back);

        VBox change = new VBox(20);
        change.setTranslateX(200);
        change.setTranslateY(100);
        change.setAlignment(Pos.CENTER);

        Text currentNickname = new Text();
        TextField newNickname = new TextField();
        newNickname.setPromptText("new nickname");
        Text nicknameDetail = new Text();
        Button submit = new Button("submit");
        submit.setOnMouseClicked(mouseEvent -> {
            if(nicknameDetail.getText().equals("valid nickname!")) {
                try {
                    Stronghold.getCurrentUser().setNickname(newNickname.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Nickname change successful");
                    alert.setContentText("Your nickname was changed successfully");
                    mainPane.getChildren().remove(change);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        change.getChildren().addAll(currentNickname, newNickname, nicknameDetail, submit);
        newNickname.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNickname(newValue, nicknameDetail);
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change, back);
            mainPane.getChildren().add(mainButtons);
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changeEmail(){
        Button back = new Button("back");
        back.setTranslateX(120);
        back.setTranslateY(160);
        mainPane.getChildren().add(back);

        VBox change = new VBox(20);
        change.setTranslateX(200);
        change.setTranslateY(100);
        change.setAlignment(Pos.CENTER);

        Text currentEmail = new Text();
        TextField newEmail = new TextField();
        newEmail.setPromptText("new nickname");
        Text emailDetail = new Text();
        Button submit = new Button("submit");
        submit.setOnMouseClicked(mouseEvent -> {
            if(emailDetail.getText().equals("valid email!")) {
                try {
                    Stronghold.getCurrentUser().setEmail(newEmail.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Email change successful");
                    alert.setContentText("Your email was changed successfully");
                    mainPane.getChildren().remove(1);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change, back);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(currentEmail, newEmail, emailDetail, submit);
        newEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            checkEmail(newValue, emailDetail);
        });

        mainPane.getChildren().remove(1);
        mainPane.getChildren().add(change);
    }

    public void changeAvatar(){

    }

    private void checkUsername(String username, Text usernameDetail){
        SignupMenuMessages message = SignupMenuController.checkUsername(username);

        if(message.equals(SignupMenuMessages.INVALID_USERNAME_FORMAT))
            usernameDetail.setText("invalid username format!");
        else if(message.equals(SignupMenuMessages.USER_EXISTS))
            usernameDetail.setText("username exists!");
        else usernameDetail.setText("valid username!");
    }

    private void checkNickname(String nickname, Text nicknameDetail){
        if(!CheckFormatAndEncrypt.isNicknameFormatInvalid(nickname))
            nicknameDetail.setText("invalid nickname format!");
        else nicknameDetail.setText("valid nickname!");
    }

    private void checkEmail(String email, Text emailDetail){
        if(!CheckFormatAndEncrypt.isEmailFormatInvalid(email))
            emailDetail.setText("invalid email format!");
        else emailDetail.setText("valid email!");
    }

    private void checkPassword(String newPassword, Text newPassDetail){
        String result = CheckFormatAndEncrypt.isPasswordWeak(newPassword);

        switch (result) {
            case "short password":
                newPassDetail.setText("short password!");
                break;
            case "no lowercase letter":
                newPassDetail.setText("password must have a lowercase letter");
                break;
            case "no uppercase letter":
                newPassDetail.setText("password must have an uppercase letter");
                break;
            case "no number":
                newPassDetail.setText("password must have a digit");
                break;
            case "no special character":
                newPassDetail.setText("password must have a special character");
                break;
            default:
                newPassDetail.setText("valid password!");
                break;
        }
    }

    public void goToMainMenu() {
        //TODO go to main menu
    }

    public void scoreboard(MouseEvent mouseEvent) throws Exception {
        new Scoreboard().start(stage);
    }
}
