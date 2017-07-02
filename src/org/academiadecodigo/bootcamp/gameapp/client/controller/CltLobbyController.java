package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.client.ClientRegistry;
import org.academiadecodigo.bootcamp.gameapp.client.CltProtocolParser;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by codecadet Helder Matos on 01/07/17.
 */
public class CltLobbyController implements Initializable {

    private Client client;

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
    void SendChatMsg(ActionEvent event) {

        if (!sendChatMsg.getText().isEmpty()) {

            String sendMessage = CommProtocol.CLIENT.getProtocol() + " " + sendChatMsg.getText();
            sendChatMsg.clear();
            client.send(sendMessage);
        }
    }

    @FXML
    void onActionExitGame(ActionEvent event) {

        // TODO: 02/07/2017 - To decide if returns to login or closes the app.
        Navigation.getInstance().back();

    }

    @FXML
    void onActionExitLobby(ActionEvent event) {

        Navigation.getInstance().back();

    }

    @FXML
    void onActionNewRoom(ActionEvent event) {

        String sendMessage = CommProtocol.SERVER_GAME.getProtocol() + " " + "GAME_RPS";
        client.send(sendMessage);
    }

    @FXML
    void onActionRPS(ActionEvent event) {

        gpLobby.setVisible(false);

        gpGameRooms.setVisible(true);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CltProtocolParser cltProtocolParser;

        client = ClientRegistry.getInstance().getClient();
        cltProtocolParser = ClientRegistry.getInstance().getHandler();
        cltProtocolParser.setInitializable(this);
    }
}