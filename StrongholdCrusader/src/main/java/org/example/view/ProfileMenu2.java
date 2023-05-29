package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.SignupMenuController;
import org.example.model.Stronghold;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.view.enums.messages.SignupMenuMessages;

public class ProfileMenu2 extends Application {
    public Circle avatar;
    public Text username;
    public Text password;
    public Text nickname;
    public Text email;
    public VBox mainButtons;
    public Pane mainPane;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new FXMLLoader(ProfileMenu2.class.getResource("/view/profileMenu.fxml")).load();
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
                    mainPane.getChildren().remove(1);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        change.getChildren().addAll(currentUsername, newUsername, usernameDetail, submit);
        newUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            checkUsername(newValue, usernameDetail);
        });

        mainPane.getChildren().remove(1);
        mainPane.getChildren().add(change);
    }

    public void changePassword(){

    }

    public void changeNickname(){
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
                    mainPane.getChildren().remove(1);
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

        mainPane.getChildren().remove(1);
        mainPane.getChildren().add(change);
    }

    public void changeEmail(){
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
}
