package org.academiadecodigo.bootcamp.gameapp.utilities;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
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

    /*
     * Protocol Configuration
     *
     * @ First element:
     *      Server - Messages to the core application
     *      Client - Messages with respect to users
     * _ Second element:
     *      This part referee's to what parto of Server core or Client core will be send the message coming
     */
    public static final String SERVER_LOGIN = "@SERVER_LOGIN";
    public static final String SERVER_REGISTER = "@SERVER_REGISTER";
    public static final String SERVER_LOBBY = "@SERVER_LOBBY";
    public static final String SERVER_GAME = "@SERVER_GAME";
    public static final String SERVER_CHAT = "@SERVER_CHAT";
    public static final String SERVER_ERR = "@SERVER_ERROR";

    public static final String CLIENT_LOGIN = "@CLIENT_LOGIN";
    public static final String CLIENT_REGISTER = "@CLIENT_REGISTER";
    public static final String CLIENT_LOBBY = "@CLIENT_LOBBY";
    public static final String CLIENT_GAME = "@CLIENT_GAME";
    public static final String CLIENT_CHAT = "@CLIENT_CHAT";

    /*
     * View Load Configuration
     */
    public static final String LOGIN_VIEW = "login";
    public static final String REGISTER_VIEW = "register";
    public static final String LOBBY_VIEW = "lobby";
}