package org.academiadecodigo.bootcamp.gameapp.utilities;

import java.net.InetAddress;

/**
 * Created by codecadet on 01/07/2017.
 */
public final class AppConfig {

    /**
     * Server and Client configuration
     */
    public static final int PORT = 1234;
    public static final InetAddress IP = InetAddress.getLoopbackAddress();

    /**
     * DataBase Configuration
     * URL is the IP for Nelson's Machine
     */
    public static final String DATABASE_URL = "jdbc:mysql://192.168.1.18/gameApp";
    public static final String DATABASE_USER = "gameapp";
    public static final String DATABASE_PWD = "gamepwd";
}
