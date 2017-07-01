package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class RegisterController implements Initializable {

    private Client client;
    private EventHandler<MessageEvent> messageHandler;
    private ClientHandler clientHandler;

    @FXML
    private TextField username;

    @FXML
    private Label infoUser;

    @FXML
    private PasswordField password;

    @FXML
    private Label infoPass;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private Label infoEmail;

    @FXML
    private Button btnRegister;

    @FXML
    public void onRegister(ActionEvent event) {
        String sendMessage = CommProtocol.SERVER.getProtocol() + "\n";
        client.send(sendMessage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setClient(Client client) {

    }

    public Label getInfoUser() {
        return infoUser;
    }

    public Label getInfoPass() {
        return infoPass;
    }

    public Label getInfoEmail() {
        return infoEmail;
    }
}
