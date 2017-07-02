package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by codecadet Helder Matos on 01/07/17.
 */
public class CltLobbyController implements Initializable, Controller{

    private final String NAME = "Lobby";

    @FXML
    private Button btnGameRPS;

    @FXML
    private TextArea textChatMsgIn;

    @FXML
    private Button btnSendMsg;

    @FXML
    private Label lblChatMsgOut;

    @FXML
    void RPSInit(ActionEvent event) {

    }

    @FXML
    void SendMsg(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getName(){
        return NAME;
    }
}