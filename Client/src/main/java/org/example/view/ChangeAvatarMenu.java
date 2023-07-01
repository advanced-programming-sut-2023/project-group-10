package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.example.connection.Client;
import org.example.connection.Packet;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class ChangeAvatarMenu extends Application {
    private static Stage stage;
    public ListView avatarList;
    public Button browseButton;
    public Rectangle dropHere;

    @Override
    public void start(Stage stage) throws Exception {
        ChangeAvatarMenu.stage = stage;
        HBox hBox = new FXMLLoader(ChangeAvatarMenu.class.getResource("/view/changeAvatar.fxml")).load();
        Scene scene = new Scene(hBox);
        stage.setScene(scene);
        stage.setTitle("change avatar");
        stage.show();
    }

    @FXML
    public void initialize() {
        avatarList.getItems().add("none");
        for (int i = 1; i < 29; i++) {
            String path = Objects.requireNonNull(ChangeAvatarMenu.class.getResource("/images/avatar/avatar" + i + ".png")).toExternalForm();
            avatarList.getItems().add(new Circle(30, new ImagePattern(new Image(path))));
        }
        avatarList.getSelectionModel().select("none");
        avatarList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (avatarList.getSelectionModel().getSelectedItem().equals("none")) {
                browseButton.setDisable(false);
                dropHere.setDisable(false);
                dropHere.setOpacity(1);
            } else {
                browseButton.setDisable(true);
                dropHere.setDisable(true);
                dropHere.setOpacity(0.5);
                dropHere.setFill(new ImagePattern(new Image(Objects.requireNonNull
                        (ChangeAvatarMenu.class.getResource("/images/backgrounds/drop.jpeg")).toExternalForm())));
            }
        });

        dropHere.setFill(new ImagePattern(new Image(
                Objects.requireNonNull(ChangeAvatarMenu.class.getResource("/images/backgrounds/drop.jpeg")).toExternalForm())));
    }

    public void browse() throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Image File (*.jpeg), (*.jpg), (*.png)",
                "*.jpeg", "*.jpg", "*.png");
        fileChooser.setTitle("select profile photo");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("new avatar path", file.toURI().toString());
            Packet packet = new Packet("change avatar", attributes);
            Client.getInstance().sendPacket(packet);
            Client.getInstance().recievePacket();

            Popup popup = new Popup();
            Text text = new Text("Avatar Changed Successfully");
            popup.getContent().add(text);
            popup.show(stage);
            new ProfileMenu().start(stage);
        }
    }

    public void choosePhoto(DragEvent dragEvent) throws Exception {
        File file = dragEvent.getDragboard().getFiles().get(0);
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("new avatar path", file.toURI().toString());
        Packet packet = new Packet("change avatar", attributes);
        Client.getInstance().sendPacket(packet);
        Client.getInstance().recievePacket();
        dropHere.setFill(new ImagePattern(new Image(file.toURI().toString())));
    }

    public void profileMenu() throws Exception {
        new ProfileMenu().start(stage);
    }

    public void submit() throws Exception {
        if (avatarList.getSelectionModel().getSelectedItem().equals("none")) new ProfileMenu().start(stage);
        else {
            Circle circle = (Circle) avatarList.getSelectionModel().getSelectedItem();
            ImagePattern imagePattern = (ImagePattern) circle.getFill();
            String path = imagePattern.getImage().getUrl();
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("new avatar path",path);
            Packet packet = new Packet("change avatar", attributes);
            Client.getInstance().sendPacket(packet);
            Client.getInstance().recievePacket();
            new ProfileMenu().start(stage);
        }
    }
}