package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.academiadecodigo.bootcamp.gameapp.client.Client;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class LoginController implements Initializable {

    private Client client;
    private EventHandler<MessageEvent> messageHandler;
    private ClientHandler clientHandler;

    @FXML
    private Button btnLogin;

    @FXML
    private Label inputLabel;

    @FXML
    public void onLogin(ActionEvent event) {
        String sendMessage = "Login Controller estou aqui.\n";
        client.send(sendMessage);
    }

    @FXML
    public void onText(String message) {
        inputLabel.setText(message);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputLabel.addEventHandler(MessageEvent.type, new EventHandler<MessageEvent>() {
            @Override
            public void handle(MessageEvent event) {

                onText(event.getMessage());
            }
        });
    }

    public void setClient(Client client) {
        this.client = client;
        clientHandler = new ClientHandler(client, this);

        ExecutorService newThread = Executors.newSingleThreadExecutor();
        newThread.submit(clientHandler);
    }

    public Label getInputLabel() {
        return inputLabel;
    }
}
