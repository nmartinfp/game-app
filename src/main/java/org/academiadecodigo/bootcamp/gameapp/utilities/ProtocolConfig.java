package org.academiadecodigo.bootcamp.gameapp.utilities;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public final class ProtocolConfig {

    /*
     * Protocol Configuration
     * First element is the protocol
     * Second element is the message
     */
    public static final int PROTOCOL = 0;
    public static final int MESSAGE = 1;
    public static final int USERNAME = 1;
    public static final int PASSWORD = 2;
    public static final int FIRST_NAME = 3;


    /*
     * Protocol Configuration
     *
     * @ First token:
     *      Server - Messages from server to client
     *      Client - Messages from client to server
     * _ Second token:
     *      This part referee's to what part of Server core or Client core will be send the message coming
     */
    public static final String SERVER_LOGIN = "@SERVER_LOGIN";

    public static final String SERVER_REGISTER = "@SERVER_REGISTER";

    public static final String SERVER_CHAT = "@SERVER_CHAT";
    public static final String SERVER_CREATE_ROOM = "@SERVER_CREATE_ROOM";
    public static final String SERVER_REGISTER_ROOM = "@SERVER_REGISTER_ROOM";
    public static final String SERVER_JOIN_ROOM = "@SERVER_JOIN_ROOM";

    public static final String SERVER_CHAT_ROOM = "@SERVER_CHAT_ROOM";
    public static final String SERVER_GAME = "@SERVER_GAME";
    public static final String SERVER_OTHER_HAND = "@SERVER_OTHER_HAND";
    public static final String SERVER_RESET_ROOM = "@SERVER_RESET_ROOM";
    public static final String SERVER_ROOM_EXIT = "@SERVER_ROOM_EXIT";

    public static final String ERR = "ERROR";

    public static final String CLIENT_LOGIN = "@CLIENT_LOGIN";
    public static final String CLIENT_REGISTER = "@CLIENT_REGISTER";
    public static final String CLIENT_CHAT = "@CLIENT_CHAT";
    public static final String CLIENT_CREATE_ROOM = "@CLIENT_CREATE_ROOM";
    public static final String CLIENT_JOIN_ROOM = "@CLIENT_JOIN_ROOM";
    public static final String CLIENT_GAME = "@CLIENT_GAME";


    /*
     * View Load Configuration
     */
    public static final String VIEW_LOGIN = "login";
    public static final String VIEW_REGISTER = "register";
    public static final String VIEW_LOBBY = "lobby";
    public static final String VIEW_RPS = "rpsgame";
}