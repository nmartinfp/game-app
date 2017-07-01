package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import org.academiadecodigo.bootcamp.gameapp.client.controller.LoginController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.RegisterController;

/**
 * Created by Cyrille on 27/06/17.
 */
// TODO: 01/07/17 this class isn't a controller.
public class ClientHandler implements Runnable {

    private LoginController loginController;
    private Client client;

    public ClientHandler(LoginController loginController) {
        this.loginController = loginController;
        client = ClientRegistry.getInstance().getClient();
    }

    @Override
    public void run() {

        while (!client.getClientSocket().isClosed()) {
            System.out.println(!client.getClientSocket().isClosed());
            MessageEvent messageEvent = new MessageEvent(client.receive());

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    loginController.onLogin();
                }
            });

        }
    }
}

