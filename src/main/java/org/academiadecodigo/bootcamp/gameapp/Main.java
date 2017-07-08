package org.academiadecodigo.bootcamp.gameapp;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.client.ServerHandler;
import org.academiadecodigo.bootcamp.gameapp.client.ClientRegistry;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.gameapp.server.service.ServiceRegistry;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.JdbcUserService;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class Main extends Application {

    private Client client;
    private ExecutorService newThread;

    /*
     * Tests if the uses passes the key 'server' to start in server mode.
     * Otherwise it starts as a client.
     */
    public static void main(String[] args) {

        if (args.length != 0 && args[0].equals("server")) {
            Server server = new Server();

            wiringServer();

            server.init();
            server.start();
            return;
        }

        launch(args);
    }

    private static void wiringServer(){
        ConnectionManager connectionManager = new ConnectionManager();
        UserService userService = new JdbcUserService(connectionManager.getConnection());

        ServiceRegistry.getInstance().addService(userService);
    }

    /*
     * Shows in the server console the stream messages received.
     */
    @Override
    public void init() {

        client = new Client();
        ServerHandler serverHandler = new ServerHandler();

        serverHandler.setClient(client);

        ClientRegistry.getInstance().setHandler(serverHandler);

        newThread = Executors.newSingleThreadExecutor();
        newThread.submit(serverHandler);
    }

    /*
     * Loading the first view.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.initStyle(StageStyle.UTILITY);
        Navigation.getInstance().setStage(primaryStage);
        Navigation.getInstance().loadScreen(ProtocolConfig.VIEW_LOGIN);
    }

    // TODO: 07/07/17 implement creation of client handler on this class
    // TODO: 07/07/17 close socket on stop()
    @Override
    public void stop (){
        client.closeClient();
    }
}