package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLobbyController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLoginController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltRegisterController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltRpsController;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class ServerHandler implements Runnable {

    private Navigation navigation;
    private Client client;

    public ServerHandler() {
        navigation = Navigation.getInstance();
    }


    public void sendMessage(String message) {
        System.out.println("Server Handler - sendMessage (debug)" + message);
        client.send(message + "\n");
    }


    @Override
    public void run() {

        while (!client.clientConnected()) {

            String message = client.receive();
            System.out.println("Message received from server: " + message);
            ProtocolParser.clientProtocolHandler(this, message);
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               EVENT HANDLING
//----------------------------------------------------------------------------------------------------------------------

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
                System.out.println(ProtocolConfig.VIEW_LOBBY);

                if (message.equals(ProtocolConfig.VIEW_LOBBY)) {
                    loginController.successfullyAuth(message);

                    return;
                }
                if(message.equals((ProtocolConfig.ERR_LOGED))){
                    loginController.userLoged();

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


    public void unregisterRoom(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltLobbyController lobbyController = navigation.getController(ProtocolConfig.VIEW_LOBBY);
                lobbyController.removingRoom(message);
            }
        });
    }

    public void receivedMessageRoom(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((CltRpsController) navigation.getController(ProtocolConfig.VIEW_RPS)).receiveChatMsg(message);
            }
        });

    }


    public void addToRoom(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltLobbyController lobbyController = navigation.getController(ProtocolConfig.VIEW_LOBBY);
                lobbyController.roomView(message);
            }
        });
    }


    public void resultGame(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltRpsController rpsController = navigation.getController(ProtocolConfig.VIEW_RPS);
                rpsController.setWinner(message);
            }
        });
    }


    public void showOtherHand(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltRpsController rpsController = navigation.getController(ProtocolConfig.VIEW_RPS);
                rpsController.showOtherHand(message);
            }
        });
    }


    public void resetRoom(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltRpsController rpsController = navigation.getController(ProtocolConfig.VIEW_RPS);
                rpsController.resetView(message);
            }
        });
    }


    public void roomExit(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CltRpsController rpsController = navigation.getController(ProtocolConfig.VIEW_RPS);

                if (message.contains("GAME OVER")) {
                    rpsController.gameOverText(message);
                    return;
                }

                rpsController.back();
            }
        });
    }

//----------------------------------------------------------------------------------------------------------------------
//
//----------------------------------------------------------------------------------------------------------------------

    public void setClient(Client client) {
        this.client = client;
    }
}

