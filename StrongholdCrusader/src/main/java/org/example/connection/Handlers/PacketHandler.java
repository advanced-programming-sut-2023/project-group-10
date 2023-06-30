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
    private final ChatHandler chatHandler;

    public PacketHandler(Connection connection) {
        this.connection = connection;
        signupMenuHandler = new SignupMenuHandler(connection);
        loginMenuHandler = new LoginMenuHandler(connection);
        profileMenuHandler = new ProfileMenuHandler(connection);
        chatHandler = new ChatHandler(connection);
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
            case CHECK_EMAIL:
                signupMenuHandler.checkEmail();
                break;
            case CHECK_NICKNAME:
                signupMenuHandler.checkNickname();
                break;
            case RANDOM_PASSWORD:
                signupMenuHandler.generateRandomPassword();
                break;
            case RANDOM_SLOGAN:
                signupMenuHandler.generateRandomSlogan();
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
            case CHANGE_AVATAR:
                profileMenuHandler.handleChangeAvatar();
                break;
            case CHANGE_SLOGAN:
                profileMenuHandler.handleChangeSlogan();
                break;
            case GET_MY_PRIVATE_CHATS:
                chatHandler.getMyPrivateChats();
                break;
            case GET_MY_ROOMS:
                chatHandler.getMyRooms();
                break;
            case GET_PUBLIC_CHAT_MESSAGES:
                chatHandler.getPublicChatMessages();
                break;
            case GET_ROOM_MESSAGES:
                chatHandler.getRoomMessages();
                break;
            case GET_PRIVATE_CHAT_MESSAGES:
                chatHandler.getPrivateChatMessages();
                break;
            case EXIT_CHAT:
                chatHandler.exitChat();
                break;
            case SEND_MESSAGE:
                chatHandler.sendMessage();
                break;
        }
    }

    private void updateHandlerPackets(Packet receivedPacket) {
        signupMenuHandler.setReceivedPacket(receivedPacket);
        loginMenuHandler.setReceivedPacket(receivedPacket);
        profileMenuHandler.setReceivedPacket(receivedPacket);
        chatHandler.setReceivedPacket(receivedPacket);
    }
}
