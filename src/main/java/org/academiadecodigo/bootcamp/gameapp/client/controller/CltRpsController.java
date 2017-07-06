package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import org.academiadecodigo.bootcamp.gameapp.client.Client;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
public class CltRpsController implements Initializable, Controller {

    private final String NAME = "RPS";
    private Timer timer;
    private Client client;


    @FXML
    private GridPane gpLobby;

    @FXML
    private Button btnExitLobby;

    @FXML
    private Button btnTestBar;

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
    private Button btnCreateNewRoom;

    @FXML
    private Button btnExitGameRooms;

    @FXML
    void onActionExitGame(ActionEvent event) {

    }

    @FXML
    void onActionExitLobby(ActionEvent event) {
        Navigation.getInstance().back();
    }

    @FXML
    void onActionNewRoom(ActionEvent event) {

    }

    @FXML
    void onKeyPressedSendChatMsg(KeyEvent event) {
        if (!sendMsg.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {

            //String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendChatMsg.getText() + "\n";
            //String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendChatMsg.getText().replaceAll("\n|\r", "") + "\n";
            String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendMsg.getText().replaceAll("\n|\r", "") + "\n";
            sendChatMsg.clear();
            sendMsg.clear();
            client.send(sendMessage);
        }

    }

    @FXML
    void sendChatMsg(ActionEvent event) {
        if (!sendChatMsg.getText().isEmpty()) {

            String sendMessage = ProtocolConfig.CLIENT_CHAT + " " + sendChatMsg.getText() + "\n";
            sendChatMsg.clear();
            client.send(sendMessage);
        }

    }

    @FXML
    void onActiontestbar(ActionEvent event) {
        final int timeStep = 500; // miliseconds
        startTimer(timeStep);
    }


    private void startTimer(int timeStep) {
        timer = new Timer();
        timer.schedule(new Prog(), timeStep, timeStep);
    }

    private class Prog extends TimerTask {
        double numbSteps = 11;
        double base = numbSteps - 1;

        @Override
        public void run() {

            numbSteps--;

            double progress = numbSteps / base;

            progBar.setProgress(progress);
            progBar.setStyle(ColorBar.values()[(int) numbSteps].getStyle());

            if (numbSteps == 0) {
                stop();
            }
        }
    }

    private void stop() {
        timer.cancel();
    }

    public String getName() {
        return NAME;
    }

    private enum ColorBar {
        COLOR0("-fx-accent: #FF0000;"),
        COLOR1("-fx-accent: #FF0000;"),
        COLOR2("-fx-accent: #FF3300;"),
        COLOR3("-fx-accent: #FF6600;"),
        COLOR4("-fx-accent: #FF9900;"),
        COLOR5("-fx-accent: #FFCC00;"),
        COLOR6("-fx-accent: #FFFF00;"),
        COLOR7("-fx-accent: #BBFF00;"),
        COLOR8("-fx-accent: #77FF00;"),
        COLOR9("-fx-accent: #2ABB00;"),
        COLOR10("-fx-accent: #00B000;");

        private String style;

        ColorBar(String style) {
            this.style = style;
        }

        public String getStyle() {
            return style;
        }
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        progBar.setStyle("-fx-accent: #00b000");

    }
}
