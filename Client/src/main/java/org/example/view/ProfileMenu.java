package org.example.view;

import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.BackgroundBuilder;
import org.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        avatar.setFill(new ImagePattern(new Image(DataBank.getLoggedInUser().getAvatar())));
        username.setText("username: " + DataBank.getLoggedInUser().getUsername());
        nickname.setText("nickname: " + DataBank.getLoggedInUser().getNickname());
        email.setText("email: " + DataBank.getLoggedInUser().getEmail());
        if (DataBank.getLoggedInUser().getSlogan() == null || DataBank.getLoggedInUser().getSlogan().equals(""))
            slogan.setText("slogan in empty!");
        else slogan.setText("slogan: " + DataBank.getLoggedInUser().getSlogan());
    }

    public void changeUsername() {
        VBox change = new VBox(20);
        change.setAlignment(Pos.CENTER);

        Text currentUsername = new Text(DataBank.getLoggedInUser().getUsername());
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
                        HashMap<String, String> usernameAttribute = new HashMap<>();
                        usernameAttribute.put("new username", newUsername.getText());
                        Packet packet = new Packet(ClientToServerCommands.CHANGE_USERNAME.getCommand(), usernameAttribute);
                        Client.getInstance().sendPacket(packet);
                        Client.getInstance().recievePacket();
                        DataBank.getLoggedInUser().setUsername(newUsername.getText());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Username change successful");
                        alert.setContentText("Your username was changed successfully");
                        alert.show();
                        username.setText("username: " + DataBank.getLoggedInUser().getUsername());
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
                    if (Client.getInstance().recievePacket().getAttribute().get("message").equals("")) {
                        DataBank.getLoggedInUser().setPassword(newPassword.getText());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Password change successful");
                        alert.setContentText("Your password was changed successfully");
                        alert.show();
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
                    Client.getInstance().recievePacket();
                    DataBank.getLoggedInUser().setNickname(newNickname.getText());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Nickname change successful");
                    alert.setContentText("Your nickname was changed successfully");
                    alert.show();
                    nickname.setText("nickname: " + DataBank.getLoggedInUser().getNickname());
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
                        Client.getInstance().recievePacket();
                        DataBank.getLoggedInUser().setEmail(newEmail.getText());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Email change successful");
                        alert.setContentText("Your email was changed successfully");
                        alert.show();
                        email.setText(DataBank.getLoggedInUser().getEmail());
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
                DataBank.getLoggedInUser().setSlogan(defaultSlogans.getValue());
            }
            else {
                attributes.put("slogan", newSlogan.getText());
                DataBank.getLoggedInUser().setSlogan(newSlogan.getText());
            }

            Packet changeSlogan = new Packet("change slogan", attributes);
            try {
                Client.getInstance().sendPacket(changeSlogan);
                Client.getInstance().recievePacket();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Slogan change successful");
            alert.setContentText("Your slogan was changed successfully");
            slogan.setText("slogan: " + DataBank.getLoggedInUser().getSlogan());
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
            DataBank.getLoggedInUser().setSlogan("");
            try {
                Client.getInstance().sendPacket(changeSlogan);
                Client.getInstance().recievePacket();
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
        new MainMenuGFX().start(stage);
    }

    public void scoreboard() throws Exception {
        new Scoreboard().start(stage);
    }

    public void showFriends() {
        Popup popup = new Popup();

        VBox friends = new VBox(10);
        Text friendsText = new Text("friends");
        ListView<HBox> friendsListView = getFriendsList();
        friends.getChildren().addAll(friendsText, friendsListView);

        VBox pending = new VBox(10);
        Text pendingText = new Text("pending");
        ListView<HBox> pendingListView = getPendingList(popup);
        pending.getChildren().addAll(pendingText, pendingListView);

        Button back = new Button("back");

        HBox main = new HBox(20);
        main.setAlignment(Pos.CENTER);
        main.getChildren().addAll(back, friends, pending);
        popup.getContent().add(main);
        popup.show(SignupMenu.stage);
        back.setOnMouseClicked(mouseEvent -> popup.hide());
    }

    private ListView<HBox> getFriendsList () {
        ArrayList<User> friendsArrayList = new ArrayList<>();

        HashMap<String, String> attribute = new HashMap<>();
        attribute.put("username", DataBank.getLoggedInUser().getUsername());
        Packet packet = new Packet(ClientToServerCommands.GET_FRIENDS.getCommand(), attribute);
        try {
            Client.getInstance().sendPacket(packet);
            String json = Client.getInstance().recievePacket().getAttribute().get("friends");
            friendsArrayList = new Gson().fromJson(json, new com.google.gson.reflect.TypeToken<List<User>>() {}.getType());
        } catch (Exception e){
            e.printStackTrace();
        }

        ListView<HBox> friendsListView = new ListView<>();
        if(friendsArrayList != null){
            for(User user : friendsArrayList){
                friendsListView.getItems().add(createFriendHbox(user));
            }
        }
        return friendsListView;
    }

    private ListView<HBox> getPendingList(Popup popup) {
        ArrayList<User> pendingArrayList = new ArrayList<>();

        HashMap<String, String> attribute = new HashMap<>();
        attribute.put("username", DataBank.getLoggedInUser().getUsername());
        Packet packet = new Packet(ClientToServerCommands.GET_PENDING_FRIENDS.getCommand(), attribute);
        try{
            Client.getInstance().sendPacket(packet);
            String json = Client.getInstance().recievePacket().getAttribute().get("pending");
            pendingArrayList = new Gson().fromJson(json, new com.google.gson.reflect.TypeToken<List<User>>() {}.getType());
        } catch (Exception e){
            e.printStackTrace();
        }

        ListView<HBox> pendingListView = new ListView<>();
        if(pendingArrayList != null){
            for (User user : pendingArrayList){
                pendingListView.getItems().add(createPendingHbox(user, popup));
            }
        }
        return pendingListView;
    }

    private HBox createFriendHbox (User user){
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        Circle avatar = new Circle(20, new ImagePattern(new Image(user.getAvatar())));
        Text username = new Text(user.getUsername());
        Text nickname = new Text(user.getNickname());
        Text highScore = new Text(String.valueOf(user.getHighScore()));
        hBox.getChildren().addAll(avatar, username, nickname, highScore);
        return hBox;
    }

    private HBox createPendingHbox (User user, Popup popup){
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        Circle avatar = new Circle(20, new ImagePattern(new Image(user.getAvatar())));
        Text username = new Text(user.getUsername());
        Text nickname = new Text(user.getNickname());
        Text highScore = new Text(String.valueOf(user.getHighScore()));
        HBox buttons = new HBox(5);
        buttons.setAlignment(Pos.CENTER);
        Button reject = new Button("reject");
        Button accept = new Button("accept");
        buttons.getChildren().addAll(accept, reject);
        hBox.getChildren().addAll(avatar, username, nickname, highScore, buttons);

        accept.setOnMouseClicked(mouseEvent -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("owner", DataBank.getLoggedInUser().getUsername());
            hashMap.put("requester", user.getUsername());
            Packet packet = new Packet(ClientToServerCommands.ACCEPT_FRIEND.getCommand(), hashMap);
            String result = "";
            try {
                Client.getInstance().sendPacket(packet);
                result = Client.getInstance().recievePacket().getAttribute().get("result");
            } catch (Exception e){
                e.printStackTrace();
            }
            if(result.equals("successful")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("successful request");
                alert.setContentText("You have successfully requested to be " + user.getUsername() + "'s friend!");
                alert.show();
                popup.hide();
                showFriends();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("unable to request");
                alert.setContentText("You have reached your friends limit!");
                alert.show();
            }
        });

        reject.setOnMouseClicked(mouseEvent -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("owner", DataBank.getLoggedInUser().getUsername());
            hashMap.put("requester", user.getUsername());
            Packet packet = new Packet(ClientToServerCommands.REJECT_FRIEND.getCommand(), hashMap);
            try {
                Client.getInstance().sendPacket(packet);
                Client.getInstance().recievePacket();
            } catch (Exception e){
                e.printStackTrace();
            }
            popup.hide();
            showFriends();
        });

        return hBox;
    }
}
