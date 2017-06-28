package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.academiadecodigo.bootcamp.gameapp.client.Client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class LoginController implements Initializable {

    private Client client;

    @FXML
    private Button btnLogin;

    public Label getInputLabel() {
        return inputLabel;
    }

    @FXML
    private Label inputLabel;

    @FXML
    public void onLogin(ActionEvent event) {
        String sendMessage = "Login Controller estou aqui.\n";
        client.send(sendMessage);
        System.out.println("Messagem enviada.");
    }

    @FXML
    public void onText(Event event) {


            System.out.println("Messagem recebida : ");
            inputLabel.setText("ueueueueeueu");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setClient(Client client) {
        this.client = client;
        Executors.newSingleThreadExecutor().submit(new ClientHandler(client, this));

    }
}
