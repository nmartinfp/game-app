package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.academiadecodigo.bootcamp.gameapp.client.*;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;
import org.academiadecodigo.bootcamp.gameapp.utilities.Verification;

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
    private boolean fieldEmpty;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkRegister;

    @FXML
    private Label information;

    @FXML
    private Label lblUsernameError;

    @FXML
    private Label lblPasswordError;

    @FXML
    public void onLogin(ActionEvent event) {
        Verification.cleanErrorMsg(lblUsernameError, lblPasswordError, lblPasswordError);

        System.out.println("Button clicked!");

        if (!emptyField()) {
            String sendMessage = CommProtocol.SERVER.getProtocol() + username.getText() + " " + password.getText() + "\n";
            client.send(sendMessage);
        }
    }

    @FXML
    void onRegister(ActionEvent event) {
        Navigation.getInstance().loadScreen("register");        //Testing
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        client = ClientRegistry.getInstance().getClient();
        clientHandler = new ClientHandler(client, this);

        information.addEventHandler(MessageEvent.type, new EventHandler<MessageEvent>() {
            @Override
            public void handle(MessageEvent event) {
            }
        });

        ExecutorService newThread = Executors.newSingleThreadExecutor();
        newThread.submit(clientHandler);
    }

    private boolean emptyField() {
        fieldEmpty = false;
        if (username.getText().length() == 0) {
            lblUsernameError.setText("(* Required Field)");
            lblUsernameError.setVisible(true);
            fieldEmpty = true;
        }
        if (password.getText().length() == 0) {
            lblPasswordError.setText("(* Required Field)");
            lblPasswordError.setVisible(true);
            fieldEmpty = true;
        }
        return fieldEmpty;
    }

    public Label getInformation() {
        return information;
    }
}
