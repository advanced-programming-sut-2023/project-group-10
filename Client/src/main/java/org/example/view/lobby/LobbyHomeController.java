package org.example.view.lobby;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.User;
import org.example.model.lobby.Group;
import org.example.view.SignupMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbyHomeController {
    public ListView<VBox> groupsList;
    public TextField groupIdField;
    private Timeline updateGroup;
    private ArrayList<Group> currentList;

    @FXML
    public void initialize() {
        groupsList.setPrefWidth(SignupMenu.stage.getWidth() / 2);
        refreshGroupsList();
        Client.getInstance().getNotificationReceiver().setGroupListCache(currentList);

        updateGroup = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            ArrayList<Group> groupsListCache = Client.getInstance().getNotificationReceiver().getGroupListCache();
            if (groupsListCache != currentList) {
                currentList = groupsListCache;
                updateListView();
            }
        }));
        updateGroup.setCycleCount(Timeline.INDEFINITE);
        updateGroup.play();
    }

    public void createNewGroup() throws Exception {
        new NewGroupMenu().start(SignupMenu.stage);
    }

    public void refreshGroupsList() {
        Packet request = new Packet(ClientToServerCommands.REFRESH_GROUP_LIST.getCommand(), null);
        Packet response;
        try {
            Client.getInstance().sendPacket(request);
            response = Client.getInstance().recievePacket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentList = new Gson().fromJson(response.getAttribute().get("groups"), new TypeToken<List<Group>>() {
        }.getType());
        updateListView();
    }

    public void joinGroupByFieldId() {
        joinGroup(groupIdField.getId());
    }

    private void joinGroup(String groupId) {
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("group id", groupId);
        Packet request = new Packet(ClientToServerCommands.JOIN_GROUP.getCommand(), attributes);
        try {
            Client.getInstance().sendPacket(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Packet response;
        try {
            response = Client.getInstance().recievePacket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Group group = new Gson().fromJson(response.getAttribute().get("group object"), Group.class);
        if (group == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("couldn't find a group with this id!");
            alert.showAndWait();
        } else {
            try {
                new GroupWaitingMenu(group).start(SignupMenu.stage);
                updateGroup.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void updateListView() {
        groupsList.getItems().clear();
        for (Group group : currentList)
            groupsList.getItems().add(generateGroupInfoView(group));
    }

    private VBox generateGroupInfoView(Group group) {
        Label groupNameId = new Label(group.getGroupName() + " (ID: " + group.getGroupId() + ")");
        Label groupMemberCount = new Label(group.getMembers().size() + "/" + group.getMembersCap());
        StringBuilder membersText = new StringBuilder("~MEMBERS~\n");
        for (User member : group.getMembers()) {
            membersText.append(member.getNickname());
            if (member.equals(group.getAdmin())) membersText.append(" (admin)");
            membersText.append("\n");
        }
        Label members = new Label(membersText.toString());
        Button joinButton = new Button("join");
        joinButton.setOnMouseClicked(event -> joinGroup(group.getGroupId()));
        VBox result = new VBox(groupNameId, groupMemberCount, members, joinButton);
        result.setAlignment(Pos.CENTER);
        return result;
    }
}
