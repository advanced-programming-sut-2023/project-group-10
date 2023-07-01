package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
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
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.User;

import java.util.ArrayList;
import java.util.List;


public class Scoreboard extends Application {
    private String path;

    public static void bind(ListView<Integer> rank, ListView<Circle> avatar, ListView<String> username, ListView<Integer> highScore) {
        Node n1 = rank.lookup(".scroll-bar");
        if (n1 instanceof ScrollBar) {
            final ScrollBar bar1 = (ScrollBar) n1;
            Node n2 = highScore.lookup(".scroll-bar");
            if (n2 instanceof ScrollBar) {
                final ScrollBar bar2 = (ScrollBar) n2;
                bar1.valueProperty().bindBidirectional(bar2.valueProperty());
            }
            Node n3 = username.lookup(".scroll-bar");
            if (n3 instanceof ScrollBar) {
                final ScrollBar bar3 = (ScrollBar) n3;
                bar1.valueProperty().bindBidirectional(bar3.valueProperty());
            }
            Node n4 = avatar.lookup(".scroll-bar");
            if (n4 instanceof ScrollBar) {
                final ScrollBar bar4 = (ScrollBar) n4;
                bar1.valueProperty().bindBidirectional(bar4.valueProperty());
            }
        }
    }

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

        ListView<Integer> rank = new ListView<>();
        rank.setMaxWidth(70);
        rank.setFixedCellSize(70);
        ListView<Circle> avatar = new ListView<>();
        avatar.setMaxWidth(150);
        avatar.setFixedCellSize(70);
        ListView<String> username = new ListView<>();
        username.setMaxWidth(150);
        username.setFixedCellSize(70);
        ListView<Integer> highScore = new ListView<>();
        highScore.setMaxWidth(100);
        highScore.setFixedCellSize(70);

        Packet packet = new Packet(ClientToServerCommands.GET_SORTED_USERS.getCommand(), null);
        Client.getInstance().sendPacket(packet);
        ArrayList<User> sortedUsers = User.getSortedUsersFromJson(Client.getInstance().recievePacket().getAttribute().get("array list"));

        for (int i = 0; i < sortedUsers.size(); i++) {
            rank.getItems().add(i + 1);
            username.getItems().add(sortedUsers.get(i).getUsername());
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
            avatar.getItems().add(circle);
            highScore.getItems().add(sortedUsers.get(i).getHighScore());
        }

        HBox hBox = new HBox(rank, avatar, username, highScore);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxHeight(300);

        HBox main = new FXMLLoader(Scoreboard.class.getResource("/view/scoreboard.fxml")).load();
        main.getChildren().addAll(vBox, hBox);
        Scene scene = new Scene(main);
        stage.setScene(scene);
        stage.setTitle("Scoreboard");
        stage.setMaximized(true);
        stage.show();

        bind(rank, avatar, username, highScore);
    }

    public void profileMenu() throws Exception {
        new ProfileMenu().start(SignupMenu.stage);
    }
}