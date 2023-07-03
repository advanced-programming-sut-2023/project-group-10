package org.example.view.lobby;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.User;
import org.example.model.lobby.Group;
import org.example.view.DataBank;
import org.example.view.MapMenu;
import org.example.view.SignupMenu;

import java.io.IOException;
import java.util.HashMap;

public class GroupWaitingController {
    public Label groupNameIdLabel;
    public Label groupMemberCountLabel;
    public Label groupMembersLabel;
    public HBox buttonsBox;
    public VBox checkboxContainer;
    private Group currentGroup;
    private Timeline updateGroup;

    @FXML
    public void initialize() {
        currentGroup = GroupWaitingMenu.targetGroup;
        Client.getInstance().getNotificationReceiver().setGroupCache(currentGroup);
        updateGroupView();

        updateGroup = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Group groupCache = Client.getInstance().getNotificationReceiver().getGroupCache();
            if (Client.getInstance().getNotificationReceiver().isGroupComplete()) {
                Client.getInstance().getNotificationReceiver().setGroupComplete(false);
                currentGroup = groupCache;
                startSequence();
            } else if (currentGroup != groupCache) {
                currentGroup = groupCache;
                if (currentGroup != null) updateGroupView();
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("group removed due to inactivity!");
                    updateGroup.stop();
                    alert.show();
                    GroupWaitingMenu.targetGroup = null;
                    try {
                        new LobbyHomeGFX().start(SignupMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }));
        updateGroup.setCycleCount(Timeline.INDEFINITE);
        updateGroup.play();
    }

    private void stylePane() {

    }

    private void updateGroupView() {
        checkboxContainer.getChildren().clear();
        if (buttonsBox.getChildren().size() > 1) buttonsBox.getChildren().remove(1);
        buttonsBox.setSpacing(15);
        groupNameIdLabel.setText(currentGroup.getGroupName() + " (id: " + currentGroup.getGroupId() + ")");
        groupMemberCountLabel.setText(currentGroup.getMembers().size() + "/" + currentGroup.getMembersCap());
        StringBuilder membersText = new StringBuilder("MEMBERS:\n");
        for (User member : currentGroup.getMembers()) {
            membersText.append(member.getNickname());
            if (member.equals(currentGroup.getAdmin())) membersText.append(" (admin)");
            membersText.append("\n");
        }
        groupMembersLabel.setText(membersText.toString());
        if (DataBank.getLoggedInUser().equals(currentGroup.getAdmin())) {
            Button startButton = new Button("start");
            startButton.setFont(new Font("Chalkboard", 13));
            startButton.setBackground(Background.fill(Color.WHITE));
            startButton.setOnMouseClicked(mouseEvent -> {
                if (currentGroup.getMembers().size() < 2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("not enough players are in the group to start the game");
                    alert.showAndWait();
                } else {
                    HashMap<String, String> attributes = new HashMap<>();
                    attributes.put("group id", currentGroup.getGroupId());
                    Packet request = new Packet(ClientToServerCommands.START_EARLY.getCommand(), attributes);
                    try {
                        Client.getInstance().sendPacket(request);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            buttonsBox.getChildren().add(startButton);

            CheckBox isPrivateCheckbox = new CheckBox("private");
            isPrivateCheckbox.setSelected(currentGroup.isPrivate());
            isPrivateCheckbox.selectedProperty().addListener(observable -> {
                HashMap<String, String> attributes = new HashMap<>();
                attributes.put("group id", currentGroup.getGroupId());
                attributes.put("is private", String.valueOf(isPrivateCheckbox.isSelected()));
                Packet request = new Packet(ClientToServerCommands.CHANGE_GROUP_PRIVACY.getCommand(), attributes);
                try {
                    Client.getInstance().sendPacket(request);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            checkboxContainer.getChildren().add(isPrivateCheckbox);
            checkboxContainer.setSpacing(15);
        }
    }

    private void startSequence() {
        MapMenu.setGroup(currentGroup);
        try {
            new MapMenu().start(SignupMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateGroup.stop();
    }

    public void exitGroup() throws Exception {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("group id", currentGroup.getGroupId());
        Packet request = new Packet(ClientToServerCommands.LEAVE_GROUP.getCommand(), attributes);
        Client.getInstance().sendPacket(request);
        GroupWaitingMenu.targetGroup = null;
        new LobbyHomeGFX().start(SignupMenu.stage);
        updateGroup.stop();
    }
}
