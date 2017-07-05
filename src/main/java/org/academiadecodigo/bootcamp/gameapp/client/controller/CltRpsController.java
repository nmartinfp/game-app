package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
public class CltRpsController implements Initializable , Controller{

    private final String NAME = "RPS";


    @FXML
    private GridPane gpLobby;

    @FXML
    private Button btnExitLobby;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView imageX;

    @FXML
    private ProgressBar progBar;

    @FXML
    private Label lblResult;

    @FXML
    private Label lblMyChoice;

    @FXML
    private Label lblRivalChoice;

    @FXML
    private Label lblScore;

    @FXML
    private Label lblRound;

    @FXML
    private TextArea receiveChatMsg;

    @FXML
    private TextArea sendChatMsg;

    @FXML
    private Button btnSendChatMsg;

    @FXML
    private TextField sendMsg;

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
    void onActionExitGame(ActionEvent event) {

    }

    @FXML
    void onActionExitLobby(ActionEvent event) {

    }

    @FXML
    void onActionNewRoom(ActionEvent event) {

    }

    @FXML
    void onKeyPressedSendChatMsg(KeyEvent event) {

    }

    @FXML
    void sendChatMsg(ActionEvent event) {

    }




    @Override

    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getName(){
        return NAME;
    }
}
