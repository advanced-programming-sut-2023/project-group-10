package org.example.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.controller.SignupMenuController;
import org.example.model.Stronghold;
import org.example.model.User;
import org.example.model.utils.CheckFormatAndEncrypt;
import org.example.model.utils.RandomGenerator;
import org.example.view.enums.messages.SignupMenuMessages;

import java.io.IOException;

public class ProfileMenu extends Application {
    public Circle avatar;
    public Text username;
    public Text nickname;
    public Text email;
    public Text slogan;
    public VBox mainButtons;
    public VBox info;
    public HBox mainPane;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        Pane pane = new FXMLLoader(ProfileMenu.class.getResource("/view/profileMenu.fxml")).load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Profile Menu");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void initialize(){
    Stronghold.setCurrentUser(new User("Rozhin", "Rozhin23@", "rozhintzn", "rozhin@gmail.com", "yo yo", "1", "hamid"));
    //TODO delete this line
    avatar.setFill(new ImagePattern(new Image(Stronghold.getCurrentUser().getAvatar())));
    username.setText("username: " + Stronghold.getCurrentUser().getUsername());
    //password.setText(Stronghold.getCurrentUser().getPassword());
    nickname.setText("nickname: " + Stronghold.getCurrentUser().getNickname());
    email.setText("email: " + Stronghold.getCurrentUser().getEmail());
    if(Stronghold.getCurrentUser().getSlogan() == null || Stronghold.getCurrentUser().getSlogan().equals("")) slogan.setText("slogan in empty!");
    else slogan.setText("slogan: " + Stronghold.getCurrentUser().getSlogan());
    }

    public void changeUsername() {
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        Text currentUsername = new Text(Stronghold.getCurrentUser().getUsername());
        TextField newUsername = new TextField();
        newUsername.setPromptText("new username");
        Text usernameDetail = new Text();
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button submit = new Button("submit");
        Button back = new Button("back");
        buttons.getChildren().addAll(back, submit);
        submit.setOnMouseClicked(mouseEvent -> {
            if(usernameDetail.getText().equals("valid username!")) {
                try {
                    Stronghold.getCurrentUser().setUsername(newUsername.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Username change successful");
                    alert.setContentText("Your username was changed successfully");
                    username.setText(Stronghold.getCurrentUser().getUsername());
                    mainPane.getChildren().remove(change);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(currentUsername, newUsername, usernameDetail, buttons);
        newUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            checkUsername(newValue, usernameDetail);
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changePassword(){
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        TextField currentPassword = new TextField();
        currentPassword.setPromptText("current password");
        Text currentPassDetail = new Text();
        TextField newPassword = new TextField();
        newPassword.setPromptText("new password");
        Text newPassDetail = new Text();

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button submit = new Button("submit");
        Button back = new Button("back");
        buttons.getChildren().addAll(back, submit);
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
            mainPane.getChildren().removeAll(change);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(currentPassword, currentPassDetail, newPassword, newPassDetail, buttons);
        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            checkPassword(newValue, newPassDetail);
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changeNickname(){
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        Text currentNickname = new Text(Stronghold.getCurrentUser().getNickname());
        TextField newNickname = new TextField();
        newNickname.setPromptText("new nickname");
        Text nicknameDetail = new Text();
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button submit = new Button("submit");
        Button back = new Button("back");
        buttons.getChildren().addAll(back, submit);
        submit.setOnMouseClicked(mouseEvent -> {
            if(nicknameDetail.getText().equals("valid nickname!")) {
                try {
                    Stronghold.getCurrentUser().setNickname(newNickname.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Nickname change successful");
                    alert.setContentText("Your nickname was changed successfully");
                    nickname.setText(Stronghold.getCurrentUser().getNickname());
                    mainPane.getChildren().remove(change);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        change.getChildren().addAll(currentNickname, newNickname, nicknameDetail, buttons);
        newNickname.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNickname(newValue, nicknameDetail);
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change);
            mainPane.getChildren().add(mainButtons);
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changeEmail(){
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        Text currentEmail = new Text(Stronghold.getCurrentUser().getEmail());
        TextField newEmail = new TextField();
        newEmail.setPromptText("new email");
        Text emailDetail = new Text();
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button submit = new Button("submit");
        Button back = new Button("back");
        buttons.getChildren().addAll(back, submit);
        submit.setOnMouseClicked(mouseEvent -> {
            if(emailDetail.getText().equals("valid email!")) {
                try {
                    Stronghold.getCurrentUser().setEmail(newEmail.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Email change successful");
                    alert.setContentText("Your email was changed successfully");
                    email.setText(Stronghold.getCurrentUser().getEmail());
                    mainPane.getChildren().remove(change);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(currentEmail, newEmail, emailDetail, buttons);
        newEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            checkEmail(newValue, emailDetail);
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changeSlogan() {
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        HBox sloganContainer = new HBox(15);
        sloganContainer.setAlignment(Pos.CENTER);

        TextField newSlogan = new TextField();
        newSlogan.setMinWidth(175);
        newSlogan.setPromptText("slogan");

        Button random = new Button("random");
        sloganContainer.getChildren().addAll(newSlogan, random);

        ComboBox<String> defaultSlogans = new ComboBox<>();
        defaultSlogans.setMaxWidth(250);
        defaultSlogans.setPromptText("slogan");
        defaultSlogans.getItems().add("None");
        for(String defaultSlogan : RandomGenerator.getSlogans()){
            defaultSlogans.getItems().add(defaultSlogan);
        }

        defaultSlogans.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue.equals("None"))
                newSlogan.setDisable(false);
            else {
                newSlogan.setDisable(true);
                newSlogan.clear();
            }
        });

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button submit = new Button("submit");
        Button back = new Button("back");
        Button removeSlogan = new Button("remove slogan");
        buttons.getChildren().addAll(back, submit, removeSlogan);

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change);
            mainPane.getChildren().add(mainButtons);
        });

        submit.setOnMouseClicked(mouseEvent -> {
            if(newSlogan.isDisable()) Stronghold.getCurrentUser().setSlogan(defaultSlogans.getValue());
            else Stronghold.getCurrentUser().setSlogan(newSlogan.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slogan change successful");
            alert.setContentText("Your slogan was changed successfully");
            slogan.setText("slogan: " + Stronghold.getCurrentUser().getSlogan());
            mainPane.getChildren().remove(change);
            mainPane.getChildren().add(mainButtons);
        });

        random.setOnMouseClicked(mouseEvent -> {
            newSlogan.setText(RandomGenerator.getRandomSlogan());
        });

        removeSlogan.setOnMouseClicked(mouseEvent -> {
            Stronghold.getCurrentUser().setSlogan("");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slogan removed successful");
            alert.setContentText("Your slogan was removed successfully");
            slogan.setText("slogan is empty");
            mainPane.getChildren().remove(change);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(sloganContainer, defaultSlogans, buttons);

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changeAvatar() throws Exception{
        new ChangeAvatarMenu().start(stage);
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
        if(CheckFormatAndEncrypt.isNicknameFormatInvalid(nickname))
            nicknameDetail.setText("invalid nickname format!");
        else nicknameDetail.setText("valid nickname!");
    }

    private void checkEmail(String email, Text emailDetail){
        if(CheckFormatAndEncrypt.isEmailFormatInvalid(email))
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

    public void goToMainMenu() throws Exception{
        new MainMenuGFX().start(stage);
    }

    public void scoreboard() throws Exception {
        new Scoreboard().start(stage);
    }
}
