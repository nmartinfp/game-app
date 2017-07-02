package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.academiadecodigo.bootcamp.gameapp.client.*;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.Verification;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class CltRegisterController implements Initializable, Controller{

    private final String NAME = "Register";

    private Client client;
    private CltProtocolParser cltProtocolParser;
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
    public void onRegister(ActionEvent event) {

        Verification.cleanErrorMsg(lblUsernameErrorReg, lblPasswordErrorReg, lblFirstNameErrorReg, lblMailErrorReg);

        if (!emptyField()) {
            checkEmail = Verification.checkEmail(email);

            if(!checkEmail){
                lblMailErrorReg.setText("(* Invalid email)");
                lblMailErrorReg.setVisible(true);
                return;
            }

            //if(validPassword()){
                String sendMessage = ProtocolConfig.SERVER_REGISTER + " " + firstName.getText() + " " +
                        username.getText() + " " + password.getText() + "\n";

                System.out.println("send message" + sendMessage);
                client.send(sendMessage);
            //}
        }
    }

    public void backScreen(){
        Navigation.getInstance().back();
    }

    private boolean validPassword() {

        if (!Verification.checkPassword(password)) {

            lblPasswordErrorReg.setText("(* Minimum of 8 characters containing at least\n 1 number," +
                    " 1 lower case and 1 upper case letter)");
            lblPasswordErrorReg.setVisible(true);
            return false;
        }
        return true;
    }

    private boolean emptyField() {
        fieldEmpty = false;
        if (username.getText().length() == 0) {

            lblUsernameErrorReg.setText("(* Required Field)");
            lblUsernameErrorReg.setVisible(true);
            fieldEmpty = true;
        }
        if (password.getText().length() == 0) {

            lblPasswordErrorReg.setText("(* Required Field)");
            lblPasswordErrorReg.setVisible(true);
            fieldEmpty = true;
        }
        if (firstName.getText().length() == 0) {

            lblFirstNameErrorReg.setText("(* Required Field)");
            lblFirstNameErrorReg.setVisible(true);
            fieldEmpty = true;
        }
        if (email.getText().length() == 0) {

            lblMailErrorReg.setText("(* Required Field)");
            lblMailErrorReg.setVisible(true);
            fieldEmpty = true;
        }
        return fieldEmpty;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        client = ClientRegistry.getInstance().getClient();
        cltProtocolParser = ClientRegistry.getInstance().getHandler();
        cltProtocolParser.setInitializable(this);
    }

    public String getName(){
        return NAME;
    }
}
