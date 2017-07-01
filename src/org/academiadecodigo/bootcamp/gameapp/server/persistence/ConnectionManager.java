package org.academiadecodigo.bootcamp.gameapp.server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by codecadet on 01/07/2017.
 */
public class ConnectionManager {

    Connection connection;

    public Connection getConnection() {

        if (connection == null) {

            try {
                connection = DriverManager.getConnection("jdbc:mysql://192.168.1.18:3306/gameApp", "gameapp", "gamepwd");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failure to connect to database : " + e.getMessage());
                System.exit(1);
            }
        }
        return connection;
    }

    public void close() {

        if (connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failure to close database connections: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
