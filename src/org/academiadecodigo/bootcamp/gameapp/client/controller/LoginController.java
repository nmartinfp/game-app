package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;

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
    private Hyperlink btnRegistry;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label information;

    @FXML
    public void onLogin(ActionEvent event) {
        String sendMessage = CommProtocol.SERVER.getProtocol() + "\n";
        client.send(sendMessage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        information.addEventHandler(MessageEvent.type, new EventHandler<MessageEvent>() {
            @Override
            public void handle(MessageEvent event) {
            }
        });
    }

    public void setClient(Client client) {
        this.client = client;
        clientHandler = new ClientHandler(client, this);

        ExecutorService newThread = Executors.newSingleThreadExecutor();
        newThread.submit(clientHandler);
    }

    public Label getInformation() {
        return information;
    }
}
