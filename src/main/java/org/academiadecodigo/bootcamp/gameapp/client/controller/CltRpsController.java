package org.academiadecodigo.bootcamp.gameapp.client.controller;

import javafx.application.Platform;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.academiadecodigo.bootcamp.gameapp.client.ClientRegistry;
import org.academiadecodigo.bootcamp.gameapp.client.ServerHandler;
import org.academiadecodigo.bootcamp.gameapp.client.Navigation;
import org.academiadecodigo.bootcamp.gameapp.game.Choices;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.UtilsRps;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
public class CltRpsController implements Initializable, Controller {

    private final String NAME = "RPS";

    private Image rock = new Image("images/rps_rock.png");
    private Image paper = new Image("images/rps_paper.png");
    private Image scissors = new Image("images/rps_scissors.png");

    private int totalRounds;
    private int wins;
    private int loses;

    private Timer timer;
    private ServerHandler serverHandler;

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
    private Label lblNumberRound;

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
    private Label lblScore1;

    @FXML
    private Label lblScore2;

    @FXML
    private TextArea receiveChatMsg;

    @FXML
    private Button btnSendChatMsg;

    @FXML
    private TextField sendMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progBar.setStyle("-fx-accent: #00b000");
        image1.setDisable(false);
        image2.setDisable(false);
        image3.setDisable(false);
        imageX.setVisible(false);
        lblMyChoice.setVisible(false);

        totalRounds = 1;
        wins = 0;
        loses = 0;

        lblNumberRound.setText("" + totalRounds);

        serverHandler = ClientRegistry.getInstance().getHandler();
    }

    @FXML
    void onActionExitGame(ActionEvent event) {
        Navigation.getInstance().back();
    }

    @FXML
    void onImageRock(MouseEvent event) {

        image2.setDisable(true);
        image3.setDisable(true);
        stop();
        lblMyChoice.setVisible(true);
        lblMyChoice.setText("You played Rock");
        image1.setImage(image1.getImage());

        serverHandler.sendMessage(ProtocolConfig.CLIENT_GAME + ";" + Choices.ROCK.getHand());
    }

    @FXML
    void onImagePaper(MouseEvent event) {
        image1.setDisable(true);
        image3.setDisable(true);
        stop();
        lblMyChoice.setVisible(true);
        lblMyChoice.setText("You played Paper");
        image2.setDisable(true);
        image1.setImage(image2.getImage());

        serverHandler.sendMessage(ProtocolConfig.CLIENT_GAME + ";" + Choices.PAPER.getHand());
    }

    @FXML
    void onImageScissors(MouseEvent event) {
        image1.setDisable(true);
        image2.setDisable(true);
        stop();
        lblMyChoice.setVisible(true);
        lblMyChoice.setText("You played Scissors");
        image3.setDisable(true);
        image1.setImage(image3.getImage());
        image2.setImage(imageX.getImage());

        serverHandler.sendMessage(ProtocolConfig.CLIENT_GAME + ";" + Choices.SCISSORS.getHand());
    }

    @FXML
    void onKeyPressedSendChatMsg(KeyEvent event) {

        if (!sendMsg.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {

            String sendMessage = ProtocolConfig.CLIENT_CHAT + ";" + sendMsg.getText().replaceAll("\n|\r", "");
            sendMsg.clear();
            serverHandler.sendMessage(sendMessage);
        }
    }

    @FXML
    void sendChatMsg(ActionEvent event) {

        if (!sendMsg.getText().isEmpty()) {

            String sendMessage = ProtocolConfig.CLIENT_CHAT + ";" + sendMsg.getText();
            sendMsg.clear();
            serverHandler.sendMessage(sendMessage);
        }
    }

    @FXML
    void onActiontestbar(ActionEvent event) {
        startTimer(UtilsRps.TIME_STEP);
    }

    public void setWinner(String message) {

        lblResult.setText(message);
        lblResult.setVisible(true);

        receiveChatMsg.appendText(message + "\n");

        if (message.equals("YOU WIN!")) {

            lblResult.setStyle("-fx-text-fill: #2ABB00");
            lblScore1.setText("" + ++wins);

        } else if (message.equals("YOU LOSE!")) {
            lblResult.setStyle("-fx-accent: #FF0000");
            lblScore2.setText("" + ++loses);
        } else {
            lblResult.setStyle("-fx-accent: #FF0000");
        }

        totalRounds++;
    }

    public void receiveChatMsg(String message) {
        receiveChatMsg.appendText(message + "\n");
    }

    public void showOtherHand(String message) {

        if (message.equals(Choices.ROCK.getHand())) {

            image3.setImage(rock);
            image2.setImage(imageX.getImage());
            lblRivalChoice.setText("Rival played Rock");
            lblRivalChoice.setVisible(true);

        } else if (message.equals(Choices.PAPER.getHand())) {

            image3.setImage(paper);
            image2.setImage(imageX.getImage());
            lblRivalChoice.setText("Rival played Paper");
            lblRivalChoice.setVisible(true);

        } else if (message.equals(Choices.SCISSORS.getHand())) {

            image2.setImage(imageX.getImage());
            lblRivalChoice.setText("Rival played Scissors");
            lblRivalChoice.setVisible(true);

        } else {
            image2.setImage(imageX.getImage());
            lblRivalChoice.setText("Rival didn't play");
            lblRivalChoice.setVisible(true);

        }
    }

    public void resetView(String message) {

        image1.setVisible(true);
        image1.setDisable(false);
        image2.setDisable(false);
        image3.setDisable(false);
        imageX.setVisible(false);
        lblMyChoice.setVisible(false);
        lblRivalChoice.setVisible(false);

        receiveChatMsg.appendText(message + "\n");

        image1.setImage(rock);
        image2.setImage(paper);
        image3.setImage(scissors);

        lblResult.setVisible(false);
        lblNumberRound.setText("" + totalRounds);

        startTimer(UtilsRps.TIME_STEP);
    }

    private void startTimer(int timeStep) {
        timer = new Timer();
        timer.schedule(new Prog(), timeStep, timeStep);
        progBar.setVisible(true);
    }

    private void showMsg() {
        image1.setVisible(false);
        image2.setDisable(true);
        image3.setDisable(true);
        lblMyChoice.setVisible(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lblMyChoice.setText("Time over!");
            }
        });
        serverHandler.sendMessage(ProtocolConfig.CLIENT_GAME + ";" + Choices.NOCHOICE.getHand());
    }

    public void gameOverText(String message) {
        lblScore.setText(message);
    }

    private void stop() {
        timer.cancel();
    }

    public String getName() {
        return NAME;
    }

    public void back() {
        Navigation.getInstance().back();
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
    }
}
