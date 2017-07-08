package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.academiadecodigo.bootcamp.gameapp.client.ClientRegistry;
import org.academiadecodigo.bootcamp.gameapp.client.ServerHandler;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class CltLobbyController implements Initializable, Controller{

    private final String NAME = "Lobby";

    private ServerHandler serverHandler;

    @FXML
    private GridPane gpLobby;

    @FXML
    private Button btnGameRPS;

    @FXML
    private Button btnExitLobby;

    @FXML
    private TextArea sendChatMsg;

    @FXML
    private TextArea receiveChatMsg;

    @FXML
    private Button btnSendChatMsg;

    @FXML
    private GridPane gpGameRooms;

    @FXML
    private Button btnRoom1;

    @FXML
    private Button btnRoom2;

    @FXML
    private Button btnRoom3;

    @FXML
    private Button btnRoom4;

    @FXML
    private Button btnRoom5;

    @FXML
    private Button btnRoom6;

    @FXML
    private Button btnRoom7;

    @FXML
    private Button btnRoom8;

    @FXML
    private Button btnRoom9;

    @FXML
    private Button btnCreateNewRoom;

    @FXML
    private Button btnExitGameRooms;

    @FXML
    private TextField sendMsg;

    @FXML
    public void sendChatMsg(ActionEvent event) {

        if (!sendChatMsg.getText().isEmpty()) {

            String sendMessage = ProtocolConfig.CLIENT_CHAT + ";" + sendChatMsg.getText() + "\n";
            sendChatMsg.clear();
            serverHandler.sendMessage(sendMessage);
        }
    }

    @FXML
    void onKeyPressedSendChatMsg(KeyEvent event) {

        if (!sendMsg.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {

            //String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendChatMsg.getText() + "\n";
            //String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendChatMsg.getText().replaceAll("\n|\r", "") + "\n";
            String sendMessage = ProtocolConfig.CLIENT_CHAT + ";" + sendMsg.getText().replaceAll("\n|\r", "") + "\n";
            sendChatMsg.clear();
            sendMsg.clear();
            serverHandler.sendMessage(sendMessage);
        }

    }

    public void receiveChatMsg(String message){
        receiveChatMsg.appendText(message + "\n");
    }

    @FXML
    public void onActionExitGame(ActionEvent event) {

        gpLobby.setVisible(true);
        gpGameRooms.setVisible(false);
    }

    @FXML
    public void onActionExitLobby(ActionEvent event) {
        Navigation.getInstance().back();
    }

    @FXML
    public void onActionNewRoom(ActionEvent event) {

        String sendMessage = ProtocolConfig.CLIENT_CREATE_ROOM + ";" + "createRoom\n";
        serverHandler.sendMessage(sendMessage);
        btnCreateNewRoom.setDisable(true);
    }

    @FXML
    public void onActionRPS(ActionEvent event) {

        gpLobby.setVisible(false);
        gpGameRooms.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverHandler = ClientRegistry.getInstance().getHandler();
    }

    public String getName(){
        return NAME;
    }
}