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

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
// TODO: 05/07/17 empty user pass when we logout && enable button login
public class CltLoginController implements Initializable, Controller {

    private final String NAME = "Login";

    private ServerHandler serverHandler;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkRegister;

    @FXML
    private Label lblLoginInfo;

    @FXML
    private Label lblUsernameError;

    @FXML
    private Label lblPasswordError;

    // TODO: 01/07/2017 use isEmpty() to btnLogin.setDisable() and when you press setDisable() to
    @FXML
    public void onLogin(ActionEvent event) {
        Verification.cleanErrorMsg(lblUsernameError, lblPasswordError, lblPasswordError, lblPasswordError);

        if (!emptyField()) {
            String sendMessage = ProtocolConfig.CLIENT_LOGIN + ";" +  username.getText() +
                    ";" + password.getText();

            serverHandler.sendMessage(sendMessage);

            btnLogin.setDisable(true);
            lblLoginInfo.setVisible(false);
        }
    }

    public void authFailure(){
        setText(lblLoginInfo, "Authentication Failed");
        btnLogin.setDisable(false);
    }

    @FXML
    void onRegister(ActionEvent event) {
        Navigation.getInstance().loadScreen(ProtocolConfig.VIEW_REGISTER);
    }

    public void successfullyAuth(String message){
        Navigation.getInstance().loadScreen(message);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverHandler = ClientRegistry.getInstance().getHandler();
    }

    private boolean emptyField() {
        boolean fieldEmpty = false;

        if (username.getText().length() == 0) {

            setText(lblUsernameError, "(*Required Field)");
            fieldEmpty = true;
        }
        if (password.getText().length() == 0) {

            setText(lblPasswordError, "(*Required Field)");
            fieldEmpty = true;
        }
        return fieldEmpty;
    }

    private <T extends Labeled> void setText(T type, String message){
        type.setText(message);
        type.setVisible(true);
    }

    public void btnStateChange(){
        btnLogin.setDisable(true);
    }

    public String getName(){
        return NAME;
    }
}
