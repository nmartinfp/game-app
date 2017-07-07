package org.academiadecodigo.bootcamp.gameapp.client.controller;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

    // TODO: 06/07/17 Eliminate the text area in the lobby. Chat is working on that instead in the text field

    @FXML
    private GridPane gpLobby;

    @FXML
    private Button btnExitGame;

    @FXML
    private Button btnTestBar;

    @FXML
    private Label lblResult;

    @FXML
    private Label lblRound;

    @FXML
    private ProgressBar progBar;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView imageX;

    @FXML
    private Label lblMyChoice;

    @FXML
    private Label lblRivalChoice;

    @FXML
    private Label lblScore;

    @FXML
    private TextArea receiveChatMsg;

    @FXML
    private Button btnSendChatMsg;

    @FXML
    private TextField sendMsg;



    @FXML
    void onActionExitGame(ActionEvent event) {
        Navigation.getInstance().back();
    }

    @FXML
    void onActionNewRoom(ActionEvent event) {
        // TODO: 07/07/17 After receive the information of the rival choice



    }



    @FXML
    void onImageRock(MouseEvent event) {

        image2.setDisable(true);
        image3.setDisable(true);
        stop();
        lblMyChoice.setVisible(true);
        lblMyChoice.setText("You Played Rock");
        // TODO: 06/07/17 Send message od the gamechoice
        //image1.setDisable(true);
        image1.setImage(image1.getImage());
        image2.setImage(imageX.getImage());
        //image3.setImage(imageXXX.getImage()); // rival choice

    }
    
    @FXML
    void onImagePaper(MouseEvent event) {
        image1.setDisable(true);
        image3.setDisable(true);
        stop();
        lblMyChoice.setVisible(true);
        lblMyChoice.setText("You Played Paper");
        // TODO: 06/07/17 Send message od the gamechoice
        image2.setDisable(true);
        image1.setImage(image2.getImage());
        image2.setImage(imageX.getImage());
        //image3.setImage(imageXXX.getImage()); // rival choice
    }

    @FXML
    void onImageScissors(MouseEvent event) {
        image1.setDisable(true);
        image2.setDisable(true);
        stop();
        lblMyChoice.setVisible(true);
        lblMyChoice.setText("You Played Scissors");
        // TODO: 06/07/17 Send message od the gamechoice
        image3.setDisable(true);
        image1.setImage(image3.getImage());
        image2.setImage(imageX.getImage());
        //image3.setImage(imageXXX.getImage()); // rival choice
    }

    @FXML
    void onKeyPressedSendChatMsg(KeyEvent event) {

    }

    @FXML
    void sendChatMsg(ActionEvent event) {

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
                showMsg();
            }
        }

        private void showMsg() {
            image1.setDisable(true);
            image2.setDisable(true);
            image3.setDisable(true);
            lblMyChoice.setVisible(true);
            // TODO: 06/07/17 If rival played sonmething player This player lose. Or both lose
            lblResult.setVisible(true);
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
        image2.setDisable(false);
        image2.setDisable(false);
        image3.setDisable(false);
        imageX.setVisible(false);
        lblMyChoice.setVisible(false);


    }
}
