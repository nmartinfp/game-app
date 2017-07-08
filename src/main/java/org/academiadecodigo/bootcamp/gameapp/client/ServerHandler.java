package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLobbyController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLoginController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltRegisterController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.Controller;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;

import java.util.HashMap;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

// TODO: 04/07/17 close thread of this runnable *see readLine of client.
public class ServerHandler implements Runnable {

    private Navigation navigation;
    private Client client;

    public ServerHandler() {
       navigation = Navigation.getInstance();
    }

    public void sendMessage(String message) {
        client.send(message);
    }

    @Override
    public void run() {

        while (!client.clientConnected()) {

            String message = client.receive();
            System.out.println("message recieved from server: " + message);
            ProtocolParser.clientProtocolHandler(this, message);
        }
    }

    public void receivedMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((CltLobbyController)navigation.getController(ProtocolConfig.VIEW_LOBBY)).receiveChatMsg(message);
            }
        });
    }

    public void userLogin(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                CltLoginController cltLoginController = navigation.getController(ProtocolConfig.VIEW_LOGIN);

                if (message.equals(ProtocolConfig.VIEW_LOBBY)) {
                    System.out.println("vou mudar para lobby" + cltLoginController);
                    cltLoginController.successfullyAuth(message);
                    return;
                }
                cltLoginController.authFailure();
            }
        });
    }

    public void registryMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                CltRegisterController cltRegisterController = navigation.getController(ProtocolConfig.VIEW_REGISTER);

                if (message.equals(ProtocolConfig.VIEW_LOGIN)) {
                    cltRegisterController.backScreen();
                    return;
                }

                cltRegisterController.registerFailure();
            }
        });
    }

    public void genericHandler(Runnable actionToBeTaken, final String message) {
        Platform.runLater(actionToBeTaken);
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

