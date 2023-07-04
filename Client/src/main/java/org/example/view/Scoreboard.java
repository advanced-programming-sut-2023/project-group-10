package org.example.view;

import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Scoreboard extends Application {
    private String path;

    @Override
    public void start(Stage stage) throws Exception {
        TextField search = new TextField();
        search.setPromptText("search");
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            /*HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("search", newValue);
            Packet packet = new Packet(ClientToServerCommands.SEARCH_USER.getCommand(), hashMap);
            try {
                Client.getInstance().sendPacket(packet);
                String json = Client.getInstance().recievePacket().getAttribute().get("users");
                ArrayList<User> searchedUsers = new Gson().fromJson(json, new com.google.gson.reflect.TypeToken<List<User>>(){}.getType());
                showSearch(searchedUsers);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
        });

        Circle currentPlayerAvatar = new Circle(50, new ImagePattern(new Image(DataBank.getLoggedInUser().getAvatar())));
        currentPlayerAvatar.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() != currentPlayerAvatar && dragEvent.getDragboard().hasString())
                dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            dragEvent.consume();
        });
        currentPlayerAvatar.setOnDragDropped(dragEvent -> {
            Dragboard db = dragEvent.getDragboard();
            if (db.hasString())
                currentPlayerAvatar.setFill(new ImagePattern(new Image(path)));
            else dragEvent.setDropCompleted(false);
            dragEvent.consume();
        });
        Text currentPlayerUsername = new Text(DataBank.getLoggedInUser().getUsername());
        Text currentPlayerScore = new Text(Integer.toString(DataBank.getLoggedInUser().getHighScore()));
        Button back = new Button("back");
        back.setOnMouseClicked(mouseEvent -> {
            try {
                profileMenu();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(search, currentPlayerAvatar, currentPlayerUsername, currentPlayerScore, back);

        ListView<HBox> hBoxListView = new ListView<>();
        hBoxListView.setMaxWidth(600);
        hBoxListView.setMinWidth(500);
        hBoxListView.setMaxHeight(500);
        hBoxListView.setFixedCellSize(70);

        createListView(hBoxListView);
        AtomicInteger firstState = new AtomicInteger(Client.getInstance().getNotificationReceiver().getUserStateChange());
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            if(firstState.get() != Client.getInstance().getNotificationReceiver().getUserStateChange()){
                firstState.set(Client.getInstance().getNotificationReceiver().getUserStateChange());
                try {
                    createListView(hBoxListView);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
        timeline.setCycleCount(-1);
        timeline.play();

        HBox main = new FXMLLoader(Scoreboard.class.getResource("/view/scoreboard.fxml")).load();
        main.getChildren().addAll(vBox, hBoxListView);
        Scene scene = new Scene(main);
        stage.setScene(scene);
        stage.setTitle("Scoreboard");
        stage.setMaximized(true);
        stage.show();
    }

    public void profileMenu() throws Exception {
        new ProfileMenu().start(SignupMenu.stage);
    }

    private void createListView(ListView<HBox> hBoxListView) throws IOException {
        hBoxListView.getItems().clear();
        Packet packet = new Packet(ClientToServerCommands.GET_SORTED_USERS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        String json = Client.getInstance().recievePacket().getAttribute().get("array list");
        ArrayList<User> sortedUsers = new Gson().fromJson(json, new com.google.gson.reflect.TypeToken<List<User>>(){}.getType());

        for (int i = 0; i < sortedUsers.size(); i++) {
            Text rank = new Text(Integer.toString(i+1));
            Text username = new Text(sortedUsers.get(i).getUsername());

            Circle circle = new Circle(30, new ImagePattern(new Image(sortedUsers.get(i).getAvatar())));
            circle.setOnDragDetected(mouseEvent -> {
                Dragboard db = circle.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("circle drag detected");
                db.setContent(content);
                ImagePattern imagePattern = (ImagePattern) circle.getFill();
                Image image = imagePattern.getImage();
                db.setDragView(new Image(image.getUrl(), 40, 40, false, false));
                path = image.getUrl();
            });
            circle.setOnMouseDragged(mouseEvent -> mouseEvent.setDragDetect(true));
            int finalI = i;
            circle.setOnMouseClicked(mouseEvent -> showProfile(sortedUsers.get(finalI)));

            Text highScore = new Text(Integer.toString(sortedUsers.get(i).getHighScore()));

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", username.getText());
            Packet online = new Packet(ClientToServerCommands.GET_CONNECTION.getCommand(), hashMap);
            Client.getInstance().sendPacket(online);
            String state = Client.getInstance().recievePacket().getAttribute().get("state");
            Text connectionState = new Text();

            Text lastSeen = new Text();
            HashMap <String, String> attribute = new HashMap<>();
            attribute.put("username", username.getText());
            Packet lastVisit = new Packet(ClientToServerCommands.GET_LAST_VISIT.getCommand(), attribute);
            Client.getInstance().sendPacket(lastVisit);
            long time = Long.parseLong(Client.getInstance().recievePacket().getAttribute().get("time"));

            if(state.equals("online")) {
                connectionState.setText("        online        ");
                lastSeen.setText("        online        ");
            }
            else {
                long minutes = (System.currentTimeMillis() - time) / 60000;
                lastSeen.setText(minutes + " minutes ago");
                connectionState.setText("offline");
            }

            HBox hBox = new HBox(20);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(rank, username, circle, highScore, connectionState, lastSeen);
            hBoxListView.getItems().add(hBox);
        }
    }
    private void showSearch(ArrayList<User> searched) {
        Popup popup = new Popup();
        VBox all = new VBox(10);
        all.setAlignment(Pos.CENTER);

        for(User user : searched){
            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            Text username = new Text(user.getUsername());
            Circle avatar = new Circle(30, new ImagePattern(new Image(user.getAvatar())));
            Text highScore = new Text(Integer.toString(user.getHighScore()));
            hBox.getChildren().addAll(username, avatar, highScore);
            all.getChildren().add(hBox);
        }

        all.setBackground(Background.fill(Color.BEIGE));
        popup.getContent().add(all);
        popup.show(SignupMenu.stage);
    }

    private void showProfile(User user){
        Popup popup = new Popup();
        HBox hBox = new HBox(15);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(20));
        VBox vBox = new VBox(15);
        vBox.setAlignment(Pos.CENTER);
        ArrayList<User> friends = new ArrayList<>();

        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", user.getUsername());
            Packet packet = new Packet(ClientToServerCommands.GET_FRIENDS.getCommand(), hashMap);
            Client.getInstance().sendPacket(packet);
            String json = Client.getInstance().recievePacket().getAttribute().get("friends");
            friends = new Gson().fromJson(json, new com.google.gson.reflect.TypeToken<List<User>>() {}.getType());
        } catch (Exception e){
            e.printStackTrace();
        }

        Text text = new Text("friends list");
        ListView<HBox> listView = new ListView<>();
        listView.setMaxHeight(300);
        if(friends != null) {
            for (User friend : friends) {
                listView.getItems().add(createFriendHbox(friend));
            }
        }
        VBox vBox2 = new VBox(15);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.getChildren().addAll(text, listView);
        Circle avatar = new Circle(30, new ImagePattern(new Image(user.getAvatar())));
        Text username = new Text(user.getUsername());
        Text highScore = new Text(String.valueOf(user.getHighScore()));
        Button button = new Button("back");
        button.setOnMouseClicked(mouseEvent -> popup.hide());
        vBox.getChildren().addAll(avatar, username, highScore, button);
        hBox.getChildren().addAll(vBox, vBox2);
        hBox.setBackground(Background.fill(Color.BEIGE));
        popup.getContent().add(hBox);
        popup.show(SignupMenu.stage);
    }

    private HBox createFriendHbox (User user){
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        Circle avatar = new Circle(30, new ImagePattern(new Image(user.getAvatar())));
        Text username = new Text(user.getUsername());
        Text nickname = new Text(user.getNickname());
        Text highScore = new Text(String.valueOf(user.getHighScore()));
        hBox.getChildren().addAll(avatar, username, nickname, highScore);
        return hBox;
    }
}