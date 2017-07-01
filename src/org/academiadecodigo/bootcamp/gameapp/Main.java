package org.academiadecodigo.bootcamp.gameapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.client.controller.LoginController;
import org.academiadecodigo.bootcamp.gameapp.server.Server;

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

        if (args[0].equals("server")) {
            Server server = new Server();
            server.Init();
            return;
        }

        launch(args);
    }

    /**
     * Shows in the server console the stream messages received.
     */
    @Override
    public void init() {
        client = new Client();
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

        //Wire Dependencies
        LoginController loginController = (LoginController) Navigation.getInstance().getController("login");
        loginController.setClient(client);
    }

    @Override
    public void stop (){
        client.closeClient();
    }
}