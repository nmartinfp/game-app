package org.academiadecodigo.bootcamp.gameapp.client.controller;

import com.sun.jmx.snmp.tasks.Task;
import javafx.application.Platform;
import org.academiadecodigo.bootcamp.gameapp.client.Client;

/**
 * Created by Cyrille on 27/06/17.
 */
public class ClientHandler implements Task {

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

    @Override
    public void cancel() {

    }
}

