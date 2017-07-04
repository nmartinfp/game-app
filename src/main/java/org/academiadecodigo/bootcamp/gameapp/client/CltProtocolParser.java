package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLobbyController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLoginController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltRegisterController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.Controller;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

import java.util.HashMap;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
// TODO: 02/07/2017 this class will be protocol handler client
// TODO: 02/07/2017 try safe downcast
public class CltProtocolParser implements Runnable {

    private HashMap<String, Controller> controllerMap;
    private Client client;

    public CltProtocolParser() {
        client = ClientRegistry.getInstance().getClient();
        controllerMap = new HashMap<>();
    }

    @Override
    public void run() {

        while (!client.getClientSocket().isClosed()) {

            String message = client.receive();
            System.out.println("message recieved from server: " + message);
            protocolHandler(message);
        }
    }

    private void protocolHandler(String message) {

        String[] protocol = message.split(" ");

        System.out.println(protocol[ProtocolConfig.PROTOCOL] + " agora so  amensagem " + protocol[ProtocolConfig.MESSAGE]);

        switch (protocol[ProtocolConfig.PROTOCOL]){

            case ProtocolConfig.SERVER_LOGIN:
                System.out.println("metodo para ir para a parte do login");
                userLogin(protocol[ProtocolConfig.MESSAGE]);
                break;
            case ProtocolConfig.SERVER_REGISTER:
                registryMessage(protocol[ProtocolConfig.MESSAGE]);
                break;
            case ProtocolConfig.SERVER_LOBBY:

                break;
            case ProtocolConfig.SERVER_GAME:

                break;
            case ProtocolConfig.CLIENT_CHAT:
                receivedMessage(message);
                break;
        }
    }

    private void receivedMessage(final String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((CltLobbyController)controllerMap.get("Lobby")).receiveChatMsg(message);
            }
        });
    }

    private void userLogin(final String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("entrei no metodo para mudar para lobby");
                if (message.equals(ProtocolConfig.LOBBY_VIEW)) {
                    System.out.println("vou mudar para lobby");
                    ((CltLoginController)controllerMap.get("Login")).successfullyAuth(message);
                }
            }
        });
    }

    private void registryMessage(final String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (message.equals(ProtocolConfig.LOGIN_VIEW)) {
                    ((CltRegisterController)controllerMap.get("Register")).backScreen();
                }
            }
        });
    }

    public void setInitializable(Controller controller) {
        controllerMap.put(controller.getName(), controller);
    }
}

