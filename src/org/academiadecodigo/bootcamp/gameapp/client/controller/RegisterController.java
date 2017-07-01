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
import org.academiadecodigo.bootcamp.gameapp.client.ClientHandler;
import org.academiadecodigo.bootcamp.gameapp.client.ClientRegistry;
import org.academiadecodigo.bootcamp.gameapp.client.MessageEvent;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;
import org.academiadecodigo.bootcamp.gameapp.utilities.Verification;

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
    private boolean fieldEmpty;
    boolean goodPass;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private Button btnRegister;

    @FXML
    private Label lblUsernameErrorReg;

    @FXML
    private Label lblPasswordErrorReg;

    @FXML
    private Label lblMailErrorReg;

    @FXML
    public void onRegister(ActionEvent event) {

        Verification.cleanErrorMsg(lblUsernameErrorReg, lblPasswordErrorReg, lblMailErrorReg);
        fieldEmpty = emptyField();

        if (!fieldEmpty) {
            goodPass = validPassword();
            String sendMessage = CommProtocol.SERVER.getProtocol() + username.getText() + " | " + password.getText() +
                    firstName.getText() + " | " + lastName.getText() + email.getText() + "\n";

            client.send(sendMessage);
        }
    }

    private boolean validPassword() {
        return true;
    }

    private boolean emptyField() {
        fieldEmpty = true;
        if (username.getText().length() == 0) {
            lblUsernameErrorReg.setText("(* Required Field)");
            lblUsernameErrorReg.setVisible(true);
            fieldEmpty = false;
        }
        if (password.getText().length() == 0) {
            lblPasswordErrorReg.setText("(* Required Field)");
            lblPasswordErrorReg.setVisible(true);
            fieldEmpty = false;
        }
        if (email.getText().length() == 0) {
            lblMailErrorReg.setText("(* Required Field)");
            lblMailErrorReg.setVisible(true);
            fieldEmpty = false;
        }
        return fieldEmpty;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        client = ClientRegistry.getInstance().getClient();
    }

}
