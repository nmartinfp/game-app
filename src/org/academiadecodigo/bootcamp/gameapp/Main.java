package org.academiadecodigo.bootcamp.gameapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.client.ClientRegistry;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.gameapp.server.service.ServiceRegistry;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.JdbcUserService;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */


public class Main extends Application {

    private Client client;

    /**
     * Tests if the uses passes the key 'server' to start in server mode.
     * Otherwise it starts as a client.
     *
     * @param args
     */
    public static void main(String[] args) {

        // TODO: 02/07/17 CHECK to see if start is fixed to start on main
        if (args[0].equals("server")) {
            Server server = new Server();
            prepairServer();
            server.init();
            server.start();
            return;
        }

        launch(args);
    }

    // TODO: 02/07/17 Check to see if existe BUG's ** see if connection isn't null &&
    // userService on ServerParser ins't null too check line 19
    private static void prepairServer(){
        ConnectionManager connectionManager = new ConnectionManager();
        UserService userService = new JdbcUserService(connectionManager.getConnection());

        ServiceRegistry.getInstance().addService(userService);
    }

    /**
     * Shows in the server console the stream messages received.
     */
    @Override
    public void init() {
        client = new Client();
        //CltProtocolParser clientHandler = new CltProtocolParser();

        ClientRegistry.getInstance().setClient(client);
        //ClientRegistry.getInstance().setHandler(clientHandler);
    }

    /**
     * Loading the first view.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Navigation.getInstance().setStage(primaryStage);
        Navigation.getInstance().loadScreen("login");

    }

    @Override
    public void stop (){
        client.closeClient();
    }
}