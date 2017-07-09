package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.academiadecodigo.bootcamp.gameapp.client.*;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.Security.Password;
import org.academiadecodigo.bootcamp.gameapp.utilities.Verification;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class CltRegisterController implements Initializable, Controller {

    private final String NAME = "Register";

    private ServerHandler serverHandler;
    private boolean fieldEmpty;
    private boolean checkEmail;

    @FXML
    private TextField username;

    @FXML
    private Label lblUsernameErrorReg;

    @FXML
    private PasswordField password;

    @FXML
    private Label lblPasswordErrorReg;

    @FXML
    private TextField firstName;

    @FXML
    private Label lblFirstNameErrorReg;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private Label lblMailErrorReg;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnBack;

    @FXML
    public void onRegister(ActionEvent event) {

        Verification.cleanErrorMsg(lblUsernameErrorReg, lblPasswordErrorReg, lblFirstNameErrorReg, lblMailErrorReg);

        if (!emptyField()) {
            checkEmail = Verification.checkEmail(email);

            if (!checkEmail) {
                setText(lblMailErrorReg, "(* Invalid email)");
                return;
            }

            //if(validPassword()){

            String sendMessage = ProtocolConfig.CLIENT_REGISTER + ";" + firstName.getText() + ";" +
                    username.getText() + ";" + password.getText();

            System.out.println("send message" + sendMessage);
            serverHandler.sendMessage(sendMessage);
            //}
        }
    }

    public void backScreen() {
        Navigation.getInstance().back();
    }

    private boolean validPassword() {

        if (!Verification.checkPassword(password)) {

            String message = "(* Minimum of 8 characters containing at least\n 1 number," +
                    " 1 lower case and 1 upper case letter)";
            setText(lblPasswordErrorReg, message);
            return false;
        }
        return true;
    }

    public void registerFailure() {
        lblUsernameErrorReg.setText("Username already exists.");
    }

    private boolean emptyField() {
        fieldEmpty = false;
        if (username.getText().length() == 0) {

            setText(lblUsernameErrorReg, "(* Required Field)");
            fieldEmpty = true;
        }
        if (password.getText().length() == 0) {

            setText(lblPasswordErrorReg, "(* Required Field)");
            fieldEmpty = true;
        }
        if (firstName.getText().length() == 0) {

            setText(lblFirstNameErrorReg, "(* Required Field)");
            fieldEmpty = true;
        }
        if (email.getText().length() == 0) {

            setText(lblMailErrorReg, "(* Required Field)");
            fieldEmpty = true;
        }
        return fieldEmpty;
    }

    private <T extends Labeled> void setText(T type, String message) {
        type.setText(message);
        type.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverHandler = ClientRegistry.getInstance().getHandler();
    }

    public String getName() {
        return NAME;
    }
}
