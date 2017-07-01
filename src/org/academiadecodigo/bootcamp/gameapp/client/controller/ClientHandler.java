package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.application.Platform;
import org.academiadecodigo.bootcamp.gameapp.client.Client;

/**
 * Created by Cyrille on 27/06/17.
 */
// TODO: 01/07/17 this class isn't a controller.
public class ClientHandler implements Runnable {

    private LoginController loginController;
    private Client client;

    public ClientHandler(Client client, LoginController loginController) {
        this.client = client;
        this.loginController = loginController;
    }

    @Override
    public void run() {

        while (true) {
            MessageEvent messageEvent = new MessageEvent(client.receive());

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loginController.getInformation().fireEvent(messageEvent);
                }
            });

        }
    }
}

