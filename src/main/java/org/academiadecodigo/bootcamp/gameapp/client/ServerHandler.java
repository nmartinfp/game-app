package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import org.academiadecodigo.bootcamp.gameapp.client.controller.*;
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
        client.send(message + "\n");
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
                ((CltLobbyController) navigation.getController(ProtocolConfig.VIEW_LOBBY)).receiveChatMsg(message);
            }
        });
    }

    public void userLogin(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                CltLoginController loginController = navigation.getController(ProtocolConfig.VIEW_LOGIN);

                if (message.equals(ProtocolConfig.VIEW_LOBBY)) {
                    System.out.println("vou mudar para lobby" + loginController);
                    loginController.successfullyAuth(message);
                    return;
                }
                loginController.authFailure();
            }
        });
    }

    public void registryMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                CltRegisterController registerController = navigation.getController(ProtocolConfig.VIEW_REGISTER);

                if (message.equals(ProtocolConfig.VIEW_LOGIN)) {
                    registerController.backScreen();
                    return;
                }

                registerController.registerFailure();
            }
        });
    }

    // TODO: 08/07/17 send player to room
    public void createRoom(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltLobbyController lobbyController = navigation.getController(ProtocolConfig.VIEW_LOBBY);
                lobbyController.roomView(message);
            }
        });
    }

    public void changeRoomName(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltLobbyController lobbyController = navigation.getController(ProtocolConfig.VIEW_LOBBY);
                lobbyController.roomCreated(message);

            }
        });
    }

    public void receivedMessageRoom(String message) {
        ((CltRpsController) navigation.getController(ProtocolConfig.VIEW_RPS)).receiveChatMsg(message);
    }
    // TODO: 08/07/17 send player to room

    public void addToRoom(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltLobbyController lobbyController = navigation.getController(ProtocolConfig.VIEW_LOBBY);
                lobbyController.roomView(message);
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

