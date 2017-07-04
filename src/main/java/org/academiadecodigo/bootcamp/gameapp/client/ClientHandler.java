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
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

// TODO: 04/07/17 close thread of this runnable *see readLine of client.
public class ClientHandler implements Runnable {

    private HashMap<String, Controller> controllerMap;
    private Client client;

    public ClientHandler() {
        client = ClientRegistry.getInstance().getClient();
        controllerMap = new HashMap<>();
    }

    @Override
    public void run() {

        while (!client.getClientSocket().isClosed()) {

            String message = client.receive();
            System.out.println("message recieved from server: " + message);
            ProtocolParser.clientProtocolHandler(this, message);
        }
    }

    public void receivedMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((CltLobbyController) controllerMap.get("Lobby")).receiveChatMsg(message);
            }
        });
    }

    public void userLogin(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (message.equals(ProtocolConfig.LOBBY_VIEW)) {
                    System.out.println("vou mudar para lobby");
                    ((CltLoginController) controllerMap.get("Login")).successfullyAuth(message);
                    return;
                }
                ((CltLoginController) controllerMap.get("Login")).authFailure();
            }
        });
    }

    public void registryMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (message.equals(ProtocolConfig.LOGIN_VIEW)) {
                    ((CltRegisterController) controllerMap.get("Register")).backScreen();
                }
            }
        });
    }

    public void genericHandler(Runnable actionToBeTaken, final String message) {
        Platform.runLater(actionToBeTaken);
    }

    public void setInitializable(Controller controller) {
        controllerMap.put(controller.getName(), controller);
    }


}

