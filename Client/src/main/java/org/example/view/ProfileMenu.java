package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.model.BackgroundBuilder;
import org.example.view.enums.messages.ProfileMenuMessages;

public class ProfileMenu extends Application {
    private static Stage stage;
    public Circle avatar;
    public Text username;
    public Text nickname;
    public Text email;
    public Text slogan;
    public VBox mainButtons;
    public VBox info;
    public HBox mainPane;

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        Pane pane = new FXMLLoader(ProfileMenu.class.getResource("/view/profileMenu.fxml")).load();
        pane.setBackground(new Background(BackgroundBuilder.setBackground("/images/backgrounds/background6.jpeg")));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Profile Menu");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        //TODO get current user
        avatar.setFill(new ImagePattern(new Image(Stronghold.getCurrentUser().getAvatar())));
        username.setText("username: " + Stronghold.getCurrentUser().getUsername());
        nickname.setText("nickname: " + Stronghold.getCurrentUser().getNickname());
        email.setText("email: " + Stronghold.getCurrentUser().getEmail());
        if (Stronghold.getCurrentUser().getSlogan() == null || Stronghold.getCurrentUser().getSlogan().equals(""))
            slogan.setText("slogan in empty!");
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
            if (usernameDetail.getText().equals("valid username!")) {
                try {
                    if (usernameDetail.getText().equals("valid username!")) {
                        //TODO change username
                        ProfileMenuController.changeUsername(username.getText());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Username change successful");
                        alert.setContentText("Your username was changed successfully");
                        username.setText("username: " + Stronghold.getCurrentUser().getUsername());
                        mainPane.getChildren().remove(change);
                        mainPane.getChildren().add(mainButtons);
                        //TODO username does not change in database
                    }
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

    public void changePassword() {
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        PasswordField currentPassword = new PasswordField();
        currentPassword.setPromptText("current password");
        currentPassword.setMinWidth(230);
        Text currentPassDetail = new Text();
        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("new password");
        newPassword.setMinWidth(230);
        Text newPassDetail = new Text();

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);
        Button submit = new Button("submit");
        Button back = new Button("back");
        buttons.getChildren().addAll(back, submit);
        submit.setOnMouseClicked(mouseEvent -> {
            if (newPassDetail.getText().equals("valid password!")) {
                //TODO check password
                if (!Stronghold.getCurrentUser().getPassword().equals(CheckFormatAndEncrypt.encryptString(currentPassword.getText())))
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

    public void changeNickname() {
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
            if (nicknameDetail.getText().equals("valid nickname!")) {
                try {
                    //TODO change nickname
                    ProfileMenuController.changeNickname(newNickname.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Nickname change successful");
                    alert.setContentText("Your nickname was changed successfully");
                    nickname.setText("nickname: " + Stronghold.getCurrentUser().getNickname());
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

    public void changeEmail() {
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
            if (emailDetail.getText().equals("valid email!")) {
                try {
                    if (emailDetail.getText().equals("valid email!")) {
                        //TODO change email
                        ProfileMenuController.changeEmail(newEmail.getText());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Email change successful");
                        alert.setContentText("Your email was changed successfully");
                        email.setText(Stronghold.getCurrentUser().getEmail());
                        mainPane.getChildren().remove(change);
                        mainPane.getChildren().add(mainButtons);
                    }
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
        for (String defaultSlogan : RandomGenerator.getSlogans()) {
            defaultSlogans.getItems().add(defaultSlogan);
        }

        defaultSlogans.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue.equals("None"))
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
            if (newSlogan.isDisable()) ProfileMenuController.changeSlogan(defaultSlogans.getValue());
            else ProfileMenuController.changeSlogan(newSlogan.getText());
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

    public void changeAvatar() throws Exception {
        new ChangeAvatarMenu().start(stage);
    }

    private void checkUsername(String username, Text usernameDetail) {
        ProfileMenuMessages message = ProfileMenuController.changeUsername(username);

        if (message.equals(ProfileMenuMessages.INVALID_USERNAME))
            usernameDetail.setText("invalid username format!");
        else if (message.equals(ProfileMenuMessages.OLD_USERNAME_ENTERED))
            usernameDetail.setText("enter a new username!");
        else if (message.equals(ProfileMenuMessages.USERNAME_EXISTS))
            usernameDetail.setText("username exists!");
        else usernameDetail.setText("valid username!");
    }

    private void checkNickname(String nickname, Text nicknameDetail) {
        if (CheckFormatAndEncrypt.isNicknameFormatInvalid(nickname))
            nicknameDetail.setText("invalid nickname format!");
        else nicknameDetail.setText("valid nickname!");
    }

    private void checkEmail(String email, Text emailDetail) {
        if (CheckFormatAndEncrypt.isEmailFormatInvalid(email))
            emailDetail.setText("invalid email format!");
        else if (Stronghold.getCurrentUser().getEmail().equals(email))
            emailDetail.setText("enter a new email!");
        else if (User.getUserByEmail(email) != null)
            emailDetail.setText("email exists!");
        else emailDetail.setText("valid email!");
    }

    private void checkPassword(String newPassword, Text newPassDetail) {
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

    public void goToMainMenu() throws Exception {
        new MainMenuGFX().start(stage);
    }

    public void scoreboard() throws Exception {
        new Scoreboard().start(stage);
    }
}
