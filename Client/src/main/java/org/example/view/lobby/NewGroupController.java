package org.example.view.lobby;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.connection.Client;
import org.example.connection.ClientToServerCommands;
import org.example.connection.Packet;
import org.example.model.lobby.Group;
import org.example.view.SignupMenu;

import java.util.HashMap;

public class NewGroupController {
    public TextField groupNameField;
    public Spinner<Integer> playerCountSpinner;
    public CheckBox isPrivateCheckbox;

    @FXML
    public void initialize() {
        playerCountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 8, 2));
    }

    public void createNewGroup() throws Exception {
        HashMap<String, String> attributes = new HashMap<>();
        if (groupNameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("first enter the group's name");
            alert.showAndWait();
            return;
        }
        attributes.put("group name", groupNameField.getText());
        attributes.put("player count", String.valueOf(playerCountSpinner.getValue()));
        attributes.put("is private", String.valueOf(isPrivateCheckbox.isSelected()));
        Packet request = new Packet(ClientToServerCommands.CREATE_GROUP.getCommand(), attributes);
        Client.getInstance().sendPacket(request);
        Packet response = Client.getInstance().recievePacket();
        Group createdGroup = new Gson().fromJson(response.getAttribute().get("group object"), Group.class);
        new GroupWaitingMenu(createdGroup).start(SignupMenu.stage);
    }
}
