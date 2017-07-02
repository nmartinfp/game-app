package org.academiadecodigo.bootcamp.gameapp.client;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltLoginController;
import org.academiadecodigo.bootcamp.gameapp.client.controller.CltRegisterController;

/**
 * Created by Cyrille on 27/06/17.
 */
// TODO: 02/07/2017 this class will be protocol handler client
// TODO: 02/07/2017 try safe downcast
public class CltProtocolParser implements Runnable {

    private static final int SERVER_PROTOCOL = 0;
    private static final int SERVER_MESSAGE = 1;

    private Initializable initializable;
    private Client client;

    public CltProtocolParser() {
        client = ClientRegistry.getInstance().getClient();
    }

    @Override
    public void run() {

        while (!client.getClientSocket().isClosed()) {
            System.out.println("inicio client handler");
            String message = client.receive();


            protocolHandler(message);

        }
    }

    private void protocolHandler(String message) {

        String[] protocol = message.split(" ");


        switch (protocol[SERVER_PROTOCOL]){

            case "@SERVER_LOGIN":
                userLogin(protocol[SERVER_MESSAGE]);
                break;
            case "@SERVER_REGISTER":
                System.out.println(protocol[SERVER_PROTOCOL] + " mais isto" + protocol[SERVER_MESSAGE]);
                registryMessage(protocol[SERVER_MESSAGE]);
                break;
            case "@SERVER_LOBBY":

                break;
            case "@SERVER_GAME":

                break;
            case "@CLIENT":

                break;
        }
    }

    private void userLogin(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (message.equals("lobby")) {
                    ((CltLoginController) initializable).successfullyAuth(message);
                }
            }
        });
    }

    private void registryMessage(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (message.equals("login")) {
                    ((CltRegisterController) initializable).backScreen();
                }
            }
        });
    }

    public void setInitializable(Initializable initializable) {
        this.initializable = initializable;
    }
}

