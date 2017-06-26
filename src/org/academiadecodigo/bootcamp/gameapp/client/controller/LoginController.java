package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by codecadet Helder Matos on 26/06/17.
 */
public class LoginController implements Initializable{

    private PrintWriter output;

    @FXML
    private Button btnLogin;

    @FXML
    void onLogin(ActionEvent event) {
        output.write("Login conttroller estou aqui!!!\n");
        output.flush();
        System.out.println("Login conttroller estou aqui!!!");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setOutput(PrintWriter output) {
        this.output = output;
    }
}
