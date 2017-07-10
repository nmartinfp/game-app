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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class CltLobbyController implements Initializable, Controller {

    private final String NAME = "Lobby";

    private ServerHandler serverHandler;
    private List<Button> btnArray;

    @FXML
    private GridPane gpLobby;

    @FXML
    private Button btnGameRPS;

    @FXML
    private Button btnExitLobby;

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
    private Button btnRoom10;

    @FXML
    private Button btnCreateNewRoom;

    @FXML
    private Button btnExitGameRooms;

    @FXML
    private TextField sendMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverHandler = ClientRegistry.getInstance().getHandler();

        btnArray = new LinkedList<>();
        addingButton();
    }

    @FXML
    public void sendChatMsg(ActionEvent event) {

        if (!sendMsg.getText().isEmpty()) {

            String sendMessage = ProtocolConfig.CLIENT_CHAT + ";" + sendMsg.getText();
            sendMsg.clear();
            serverHandler.sendMessage(sendMessage);
        }
    }

    @FXML
    void onKeyPressedSendChatMsg(KeyEvent event) {

        if (!sendMsg.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {

            //String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendChatMsg.getText() + "\n";
            //String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendChatMsg.getText().replaceAll("\n|\r", "") + "\n";
            String sendMessage = ProtocolConfig.CLIENT_CHAT + ";" + sendMsg.getText().replaceAll("\n|\r", "");
            sendMsg.clear();
            serverHandler.sendMessage(sendMessage);
        }

    }

    public void receiveChatMsg(String message) {
        receiveChatMsg.appendText(message + "\n");
    }

    public void roomCreated(String message) {

        for (Button button : btnArray) {

            if (button.getText().equals("Room")) {
                button.setText(message);
                return;
            }
        }
    }

    public void removingRoom(String roomName) {

        for (Button button : btnArray) {

            if (button.getText().equals(roomName)) {
                button.setText("Room");
            }
        }
    }

    public void roomView(String message) {
        Navigation.getInstance().loadScreen(message);
    }

    @FXML
    public void onActionExitGame(ActionEvent event) {

        gpLobby.setVisible(true);
        gpGameRooms.setVisible(false);
    }

    @FXML
    public void onActionExitLobby(ActionEvent event) {
        serverHandler.sendMessage(ProtocolConfig.CLIENT_LOGIN + ";" + "logout");
        Navigation.getInstance().back();
    }

    @FXML
    public void onActionNewRoom(ActionEvent event) {

        System.out.println(btnRoom1.getText());
        String sendMessage = ProtocolConfig.CLIENT_CREATE_ROOM + ";" + "createRoom";
        serverHandler.sendMessage(sendMessage);
    }

    @FXML
    public void onActionRoom(ActionEvent event){

        Button clickedButton = (Button) event.getSource();
        String sendMessage = ProtocolConfig.CLIENT_JOIN_ROOM + ";" + clickedButton.getText();
        serverHandler.sendMessage(sendMessage);
    }

    @FXML
    public void onActionRPS(ActionEvent event) {

        gpLobby.setVisible(false);
        gpGameRooms.setVisible(true);
    }

    private void addingButton() {

        btnArray.add(btnRoom1);
        btnArray.add(btnRoom2);
        btnArray.add(btnRoom3);
        btnArray.add(btnRoom4);
        btnArray.add(btnRoom5);
        btnArray.add(btnRoom6);
        btnArray.add(btnRoom7);
        btnArray.add(btnRoom8);
        btnArray.add(btnRoom9);
    }

    public String getName() {
        return NAME;
    }
}