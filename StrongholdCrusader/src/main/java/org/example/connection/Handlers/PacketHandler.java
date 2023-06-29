package org.example.connection.Handlers;

import org.example.connection.ClientToServerCommands;
import org.example.connection.Connection;
import org.example.connection.Packet;

import java.io.IOException;

public class PacketHandler {
    private final Connection connection;
    private final SignupMenuHandler signupMenuHandler;

    public PacketHandler(Connection connection) {
        this.connection = connection;
        signupMenuHandler = new SignupMenuHandler(connection);
    }

    public void handle(Packet receivedPacket) throws IOException {
        updateHandlerPackets(receivedPacket);
        ClientToServerCommands receivedPacketCommand = ClientToServerCommands.getCommandByString(receivedPacket.getCommand());
        switch (receivedPacketCommand) {
            case GET_DEFAULT_SLOGANS:
                signupMenuHandler.getDefaultSlogans();
                break;
            case LIVE_CHECK_USERNAME:
                signupMenuHandler.checkUsername();
                break;
            case LIVE_CHECK_PASSWORD:
                signupMenuHandler.checkPassword();
                break;
            case LIVE_CHECK_PASSWORD_CONFIRMATION:
                signupMenuHandler.checkPasswordConfirmation();
                break;
            case RANDOM_PASSWORD:
                signupMenuHandler.generateRandomPassword();
                break;
            case CHECK_SIGNUP_INFO:
                signupMenuHandler.checkSignUpInfo();
                break;
            case GET_SECURITY_QUESTIONS:
                signupMenuHandler.getSecurityQuestions();
                break;
            case GET_CAPTCHA:
                signupMenuHandler.generateCaptcha();
                break;
            case COMPLETE_SIGNUP:
                signupMenuHandler.completeSignup();
                break;
        }
    }

    private void updateHandlerPackets(Packet receivedPacket) {
        signupMenuHandler.setReceivedPacket(receivedPacket);
    }
}
