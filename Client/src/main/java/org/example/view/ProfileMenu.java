package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.BackgroundBuilder;

import java.util.HashMap;

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
        //avatar.setFill(new ImagePattern(new Image(Stronghold.getCurrentUser().getAvatar())));
        username.setText("username: " + DataBank.getUsername());
        nickname.setText("nickname: " + DataBank.getNickname());
        email.setText("email: " + DataBank.getEmail());
        if (DataBank.getSlogan() == null || DataBank.getSlogan().equals(""))
            slogan.setText("slogan in empty!");
        else slogan.setText("slogan: " + DataBank.getSlogan());
    }

    public void changeUsername() {
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        Text currentUsername = new Text(DataBank.getUsername());
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
                        HashMap<String, String> attributes = new HashMap<>();
                        attributes.put("new username", username.getText());
                        Packet packet = new Packet("change username", attributes);
                        Client.getInstance().sendPacket(packet);
                        DataBank.setUsername(username.getText());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Username change successful");
                        alert.setContentText("Your username was changed successfully");
                        username.setText("username: " + DataBank.getUsername());
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
            HashMap<String, String> attribute = new HashMap<>();
            attribute.put("username", newValue);
            Packet packet = new Packet(ClientToServerCommands.LIVE_CHECK_USERNAME.getCommand(), attribute);
            try {
                Client.getInstance().sendPacket(packet);
                usernameDetail.setText(Client.getInstance().recievePacket().getAttribute().get("message"));
            } catch (Exception e){
                e.printStackTrace();
            }
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
                HashMap<String, String> attribute = new HashMap<>();
                attribute.put("current password", currentPassword.getText());
                attribute.put("new password", newPassword.getText());
                Packet packet = new Packet(ClientToServerCommands.CHANGE_PASSWORD.getCommand(), attribute);
                try {
                    Client.getInstance().sendPacket(packet);
                    if (Client.getInstance().recievePacket().getAttribute() == null) {
                        DataBank.setPassword(newPassword.getText());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Password change successful");
                        alert.setContentText("Your password was changed successfully");
                        mainPane.getChildren().remove(change);
                        mainPane.getChildren().add(mainButtons);
                    } else currentPassDetail.setText("wrong password!");
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        back.setOnMouseClicked(mouseEvent -> {
            mainPane.getChildren().removeAll(change);
            mainPane.getChildren().add(mainButtons);
        });

        change.getChildren().addAll(currentPassword, currentPassDetail, newPassword, newPassDetail, buttons);
        newPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            HashMap<String, String> attribute = new HashMap<>();
            attribute.put("password", newValue);
            Packet packet = new Packet(ClientToServerCommands.LIVE_CHECK_PASSWORD.getCommand(), attribute);
            try {
                Client.getInstance().sendPacket(packet);
                newPassDetail.setText(Client.getInstance().recievePacket().getAttribute().get("message"));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mainPane.getChildren().remove(mainButtons);
        mainPane.getChildren().add(change);
    }

    public void changeNickname() {
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        Text currentNickname = new Text(DataBank.getNickname());
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
                    HashMap<String, String> attributes = new HashMap<>();
                    attributes.put("new nickname", newNickname.getText());
                    Packet packet = new Packet(ClientToServerCommands.CHANGE_NICKNAME.getCommand(), attributes);
                    Client.getInstance().sendPacket(packet);
                    DataBank.setNickname(newNickname.getText());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Nickname change successful");
                    alert.setContentText("Your nickname was changed successfully");
                    nickname.setText("nickname: " + DataBank.getNickname());
                    mainPane.getChildren().remove(change);
                    mainPane.getChildren().add(mainButtons);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        change.getChildren().addAll(currentNickname, newNickname, nicknameDetail, buttons);
        newNickname.textProperty().addListener((observable, oldValue, newValue) -> {
            HashMap<String, String> attribute = new HashMap<>();
            attribute.put("nickname", newValue);
            Packet packet = new Packet(ClientToServerCommands.CHECK_NICKNAME.getCommand(), attribute);
            try {
                Client.getInstance().sendPacket(packet);
                nicknameDetail.setText(Client.getInstance().recievePacket().getAttribute().get("message"));
            } catch (Exception e){
                e.printStackTrace();
            }
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

        Text currentEmail = new Text(DataBank.getEmail());
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

                        HashMap<String, String> attributes = new HashMap<>();
                        attributes.put("new email", newEmail.getText());
                        Packet packet = new Packet("change email", attributes);
                        Client.getInstance().sendPacket(packet);
                        DataBank.setEmail(newEmail.getText());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Email change successful");
                        alert.setContentText("Your email was changed successfully");
                        email.setText(DataBank.getEmail());
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
            try {
                HashMap<String, String> attributes = new HashMap<>();
                attributes.put("email", newValue);
                Packet packet = new Packet(ClientToServerCommands.CHECK_EMAIL.getCommand(), attributes);
                Client.getInstance().sendPacket(packet);
                emailDetail.setText(Client.getInstance().recievePacket().getAttribute().get("message"));
            } catch (Exception e){
                e.printStackTrace();
            }
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

        Packet packet = new Packet("get default slogans", null);
        try {
            Client.getInstance().sendPacket(packet);
            String string = Client.getInstance().recievePacket().getAttribute().get("slogans");
            for (String defaultSlogan : string.split("\n")) {
                defaultSlogans.getItems().add(defaultSlogan);
            }
        } catch (Exception e){
            e.printStackTrace();
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
            HashMap<String, String> attributes = new HashMap<>();
            if (newSlogan.isDisable()) {
                attributes.put("slogan", defaultSlogans.getValue());
                DataBank.setSlogan(defaultSlogans.getValue());
            }
            else {
                attributes.put("slogan", newSlogan.getText());
                DataBank.setSlogan(newSlogan.getText());
            }

            Packet changeSlogan = new Packet("change slogan", attributes);
            try {
                Client.getInstance().sendPacket(changeSlogan);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slogan change successful");
            alert.setContentText("Your slogan was changed successfully");
            slogan.setText("slogan: " + DataBank.getSlogan());
            mainPane.getChildren().remove(change);
            mainPane.getChildren().add(mainButtons);
        });

        random.setOnMouseClicked(mouseEvent -> {
            try {
                Packet randomSlogan = new Packet(ClientToServerCommands.RANDOM_SLOGAN.getCommand(), null);
                Client.getInstance().sendPacket(randomSlogan);
                newSlogan.setText(Client.getInstance().recievePacket().getAttribute().get("slogan"));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        removeSlogan.setOnMouseClicked(mouseEvent -> {
            HashMap<String, String> attribute = new HashMap<>();
            attribute.put("slogan", "");
            Packet changeSlogan = new Packet("change slogan", attribute);
            try {
                Client.getInstance().sendPacket(changeSlogan);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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

    public void goToMainMenu() throws Exception {
        //new MainMenuGFX().start(stage);
        //TODO
    }

    public void scoreboard() throws Exception {
        //new Scoreboard().start(stage);
        //TODO
    }
}
