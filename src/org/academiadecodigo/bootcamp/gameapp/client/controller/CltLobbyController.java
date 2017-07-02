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
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
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