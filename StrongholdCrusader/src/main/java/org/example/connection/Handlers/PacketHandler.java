package org.example.connection.Handlers;

import org.example.connection.ClientToServerCommands;
import org.example.connection.Connection;
import org.example.connection.Packet;
import org.example.connection.ServerToClientCommands;

import java.io.IOException;

public class PacketHandler {
    private final Connection connection;
    private final SignupMenuHandler signupMenuHandler;
    private final LoginMenuHandler loginMenuHandler;
    private final ProfileMenuHandler profileMenuHandler;
    private final MainMenuHandler mainMenuHandler;
    private final ChatHandler chatHandler;
    private final LobbyHandler lobbyHandler;

    public PacketHandler(Connection connection) {
        this.connection = connection;
        signupMenuHandler = new SignupMenuHandler(connection);
        loginMenuHandler = new LoginMenuHandler(connection);
        profileMenuHandler = new ProfileMenuHandler(connection);
        mainMenuHandler = new MainMenuHandler(connection);
        chatHandler = new ChatHandler(connection);
        lobbyHandler = new LobbyHandler(connection);
    }

    public void handle(Packet receivedPacket) throws IOException {
        updateHandlerPackets(receivedPacket);
        if (!receivedPacket.getAttribute().get("sessionID").equals(connection.getSessionId()))
            connection.sendNotification(new Packet(ServerToClientCommands.UNAUTHORIZED_REQUEST.getCommand(), null));
        else if (connection.getSessionExpirationTime() < System.currentTimeMillis())
            connection.sendNotification(new Packet(ServerToClientCommands.CONNECTION_TIMED_OUT.getCommand(), null));

        ClientToServerCommands receivedPacketCommand = ClientToServerCommands.getCommandByString(receivedPacket.getCommand());
        switch (receivedPacketCommand) {
            case INITIALIZE_APP:
                signupMenuHandler.initializeApp();
                break;
            case GET_LOGGED_IN_USER:
                signupMenuHandler.getLoggedInUser();
                break;
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
            case GET_SORTED_USERS:
                profileMenuHandler.sortedUsers();
                break;
            case GET_CONNECTION:
                profileMenuHandler.connectionDatabase();
                break;
            case GET_ALL_CONNECTIONS:
                profileMenuHandler.allConnections();
                break;
            case GET_LAST_VISIT:
                profileMenuHandler.lastVisit();
                break;
            case SEARCH_USER:
                profileMenuHandler.getSearchedUsers();
                break;
            case GET_FRIENDS:
                profileMenuHandler.getFriends();
                break;
            case GET_PENDING_FRIENDS:
                profileMenuHandler.getPendingFriends();
                break;
            case REQUEST_FRIEND:
                profileMenuHandler.requestFriend();
                break;
            case ACCEPT_FRIEND:
                profileMenuHandler.acceptFriend();
                break;
            case REJECT_FRIEND:
                profileMenuHandler.rejectFriend();
                break;
            case LOGOUT:
                mainMenuHandler.handleLogout();
                break;
            case CAN_CREATE_PRIVATE_CHAT:
                chatHandler.canCreatePrivateChat();
                break;
            case CREATE_PRIVATE_CHAT:
                chatHandler.createPrivateChat();
                break;
            case IS_ROOM_ID_VALID:
                chatHandler.isRoomIDValid();
                break;
            case CREATE_ROOM:
                chatHandler.creatRoom();
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
            case IS_ADMIN:
                chatHandler.isAdmin();
                break;
            case ADD_MEMBER_TO_ROOM:
                chatHandler.addMemberToRoom();
                break;
            case SEND_MESSAGE:
                chatHandler.sendMessage();
                break;
            case CAN_UPDATE_MESSAGE:
                chatHandler.canUpdateMessage();
                break;
            case DELETE_MESSAGE:
                chatHandler.deleteMessage();
                break;
            case EDIT_MESSAGE:
                chatHandler.editMessage();
                break;
            case CREATE_GROUP:
                lobbyHandler.createGroup();
                break;
            case REFRESH_GROUP_LIST:
                lobbyHandler.refreshGroupList();
                break;
            case JOIN_GROUP:
                lobbyHandler.joinGroup();
                break;
            case LEAVE_GROUP:
                lobbyHandler.leaveGroup();
                break;
            case START_EARLY:
                lobbyHandler.startEarly();
                break;
            case CHANGE_GROUP_PRIVACY:
                lobbyHandler.changeGroupPrivacy();
                break;
        }
    }

    private void updateHandlerPackets(Packet receivedPacket) {
        signupMenuHandler.setReceivedPacket(receivedPacket);
        loginMenuHandler.setReceivedPacket(receivedPacket);
        profileMenuHandler.setReceivedPacket(receivedPacket);
        chatHandler.setReceivedPacket(receivedPacket);
        lobbyHandler.setReceivedPacket(receivedPacket);
    }
}
