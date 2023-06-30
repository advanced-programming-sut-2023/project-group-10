package org.example.connection.Handlers;

import org.example.connection.ClientToServerCommands;
import org.example.connection.Connection;
import org.example.connection.Packet;

import java.io.IOException;

public class PacketHandler {
    private final Connection connection;
    private final SignupMenuHandler signupMenuHandler;
    private final LoginMenuHandler loginMenuHandler;
    private final ProfileMenuHandler profileMenuHandler;

    public PacketHandler(Connection connection) {
        this.connection = connection;
        signupMenuHandler = new SignupMenuHandler(connection);
        loginMenuHandler = new LoginMenuHandler(connection);
        profileMenuHandler = new ProfileMenuHandler(connection);
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
            case CHECK_EMAIL:
                signupMenuHandler.checkEmail();
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
            case GET_SECURITY_QUESTION:
                loginMenuHandler.getSecurityQuestion();
                break;
            case TRY_TO_CHANGE_PASSWORD:
                loginMenuHandler.tryToChangePassword();
                break;
            case LOG_IN:
                loginMenuHandler.login();
                break;
            case CHANGE_USERNAME:
                profileMenuHandler.handleChangeUsername();
                break;
            case CHANGE_PASSWORD:
                profileMenuHandler.handleChangePassword();
                break;
            case CHANGE_NICKNAME:
                profileMenuHandler.handleChangeNickname();
                break;
            case CHANGE_EMAIL:
                profileMenuHandler.handleChangeEmail();
                break;


        }
    }

    private void updateHandlerPackets(Packet receivedPacket) {
        signupMenuHandler.setReceivedPacket(receivedPacket);
        loginMenuHandler.setReceivedPacket(receivedPacket);
        profileMenuHandler.setReceivedPacket(receivedPacket);
    }
}
