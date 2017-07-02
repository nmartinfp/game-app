package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import org.academiadecodigo.bootcamp.gameapp.client.controller.LoginController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.RegisterController;

/**
 * Created by Cyrille on 27/06/17.
 */
// TODO: 02/07/2017 this class will be protocol handler client
// TODO: 02/07/2017 try safe downcast
public class ClientHandler implements Runnable {

    private Initializable initializable;
    private Client client;

    public ClientHandler() {
        client = ClientRegistry.getInstance().getClient();
    }

    @Override
    public void run() {

        while (!client.getClientSocket().isClosed()) {

            String message = client.receive();
            registryMessage(message);

        }
    }

    private void handlingMessage(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ( (LoginController)initializable).onText(message);
            }
        });
    }

    private void registryMessage(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (message.equals("login")) {
                    ((RegisterController) initializable).backScreen();
                }
            }
        });
    }

    public void setInitializable(Initializable initializable) {
        this.initializable = initializable;
    }
}

