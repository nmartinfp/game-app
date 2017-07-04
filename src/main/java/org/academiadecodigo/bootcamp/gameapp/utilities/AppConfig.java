package org.academiadecodigo.bootcamp.gameapp.utilities;

import java.net.InetAddress;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public final class AppConfig {

    /**
     * Server and Client configuration
     */
    public static final int PORT = 1234;
    public static final InetAddress IP = InetAddress.getLoopbackAddress();
    //public static final InetAddress IP = InetAddress.getLoopbackAddress();

    /**
     * DataBase Configuration
     * URL is the IP for Nelson's Machine
     */
    //public static final String DATABASE_URL = "jdbc:mysql://localhost/gameApp";
    public static final String DATABASE_URL = "jdbc:mysql://192.168.1.18/gameApp";
    public static final String DATABASE_USER = "gameapp";
    public static final String DATABASE_PWD = "gamepwd";
}
