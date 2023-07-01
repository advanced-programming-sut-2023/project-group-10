package org.example.view;

import com.google.gson.Gson;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
        vBox.getChildren().addAll(currentPlayerAvatar, currentPlayerUsername, currentPlayerScore, back);

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

            HBox hBox = new HBox(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(rank, username, circle, highScore, connectionState, lastSeen);
            hBoxListView.getItems().add(hBox);
        }
    }
}