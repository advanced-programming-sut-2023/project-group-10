package org.example.connection;

import org.example.view.enums.messages.SignupMenuMessages;

import java.io.IOException;
import java.util.HashMap;

public class PacketHandler {
    private final Connection connection;

    public PacketHandler(Connection connection) {
        this.connection = connection;
    }

    public void handle(Packet receivedPacket) throws IOException {
        ClientToServerCommands receivedPacketCommand = ClientToServerCommands.getCommandByString(receivedPacket.getCommand());
        switch (receivedPacketCommand) {
            case SIGN_UP:
                handleSignUp(receivedPacket);
        }
    }

    private void handleSignUp(Packet receivedPacket) throws IOException {
        String username = receivedPacket.getAttribute().get("username");
        SignupMenuMessages result = null;
        /*= SignupMenuController.createUser()*/;
        Packet toBeSent;
        if (result == SignupMenuMessages.SUCCESS)
            toBeSent = new Packet(ServerToClientCommands.SUCCESSFUL_SIGNUP.getCommand(), null);
        else {
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put("message", result.name());
            toBeSent = new Packet(ServerToClientCommands.FAILED_SIGNUP.getCommand(), attributes);
        }
        connection.sendPacket(toBeSent);
    }
}
