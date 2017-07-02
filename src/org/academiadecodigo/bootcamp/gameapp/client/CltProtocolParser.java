package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLoginController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltRegisterController;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

/**
 * Created by Cyrille on 27/06/17.
 */
// TODO: 02/07/2017 this class will be protocol handler client
// TODO: 02/07/2017 try safe downcast
public class CltProtocolParser implements Runnable {

    private Initializable initializable;
    private Client client;

    public CltProtocolParser() {
        client = ClientRegistry.getInstance().getClient();
    }

    @Override
    public void run() {

        while (!client.getClientSocket().isClosed()) {

            String message = client.receive();

            protocolHandler(message);
        }
    }

    private void protocolHandler(String message) {

        String[] protocol = message.split(" ");


        switch (protocol[ProtocolConfig.PROTOCOL]){

            case ProtocolConfig.SERVER_LOGIN:
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

                break;
        }
    }

    private void userLogin(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (message.equals(ProtocolConfig.LOBBY_VIEW)) {
                    ((CltLoginController) initializable).successfullyAuth(message);
                }
            }
        });
    }

    private void registryMessage(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (message.equals(ProtocolConfig.LOGIN_VIEW)) {
                    ((CltRegisterController) initializable).backScreen();
                }
            }
        });
    }

    public void setInitializable(Initializable initializable) {
        this.initializable = initializable;
    }
}

