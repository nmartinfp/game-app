package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.academiadecodigo.bootcamp.gameapp.client.*;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
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

public class CltLoginController implements Initializable {

    private Client client;
    private CltProtocolParser cltProtocolParser;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkRegister;

    @FXML
    private Label lblUsernameError;

    @FXML
    private Label lblPasswordError;

    // TODO: 01/07/2017 use isEmpty() to btnLogin.setDisable()
    @FXML
    public void onLogin(ActionEvent event) {
        Verification.cleanErrorMsg(lblUsernameError, lblPasswordError, lblPasswordError, lblPasswordError);

        if (!emptyField()) {
            String sendMessage = ProtocolConfig.SERVER_LOGIN + " " +  username.getText() +
                    " " + password.getText() + "\n";
            client.send(sendMessage);
        }
    }

    @FXML
    void onRegister(ActionEvent event) {
        Navigation.getInstance().loadScreen(ProtocolConfig.REGISTER_VIEW);        //TODO: Testing
    }

    public void successfullyAuth(String message){
        Navigation.getInstance().loadScreen(message);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        client = ClientRegistry.getInstance().getClient();
        cltProtocolParser = new CltProtocolParser();

        cltProtocolParser.setInitializable(this);
        ClientRegistry.getInstance().setHandler(cltProtocolParser);

        ExecutorService newThread = Executors.newSingleThreadExecutor();
        newThread.submit(cltProtocolParser);
    }

    private boolean emptyField() {
        boolean fieldEmpty = false;

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
}
