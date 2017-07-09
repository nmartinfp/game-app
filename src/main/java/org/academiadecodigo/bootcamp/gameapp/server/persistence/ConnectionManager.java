package org.academiadecodigo.bootcamp.gameapp.server.persistence;

import org.academiadecodigo.bootcamp.gameapp.server.State;
import org.academiadecodigo.bootcamp.gameapp.utilities.AppConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.Logger;
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.PriorityLevel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class ConnectionManager {

    Connection connection;


    public Connection getConnection() {

        if (connection == null) {

            try {
                connection = DriverManager.getConnection(AppConfig.DATABASE_URL, AppConfig.DATABASE_USER,
                        AppConfig.DATABASE_PWD);

            } catch (SQLException e) {

                e.printStackTrace();
                System.out.println("Failure to connect to database : " + e.getMessage());
                Logger.getInstance().log(PriorityLevel.HIGH, "Database connection failure: " + e.getMessage());
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
                Logger.getInstance().log(PriorityLevel.HIGH, "Close Database connections failed! " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
