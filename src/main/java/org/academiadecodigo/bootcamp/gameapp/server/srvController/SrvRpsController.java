package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.model.RPSGame;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
public class SrvRpsController {

    private Server server;
    private Socket clientSocket;
    private UserService userService;
    private RPSGame rpsGame;
    private int rounds = 0;
    private int maxRounds;

    public SrvRpsController(Server server, Socket clientSocket, UserService userService) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.userService = userService;

        rpsGame = new RPSGame(5, server.findUser(clientSocket), server.findUser(clientSocket));
        rpsGame.start();

        User playerWin = null;

        String name = playerWin.getUsername();
        server.sendingProtoMsg(ProtocolConfig.SERVER_GAME + " " + name + " win", clientSocket);
    }

    public void start() {
        while (rounds < maxRounds) {
            rpsGame.playRound();
        }

    }

    public void tie(String tie) {
        rpsGame.playRound();

    }

    public void checkHand(String hand) {
        server.sendingProtoMsg(ProtocolConfig.SERVER_GAME + " " + hand, clientSocket);

    }

    public void checkWinner(String winner) {
        server.sendingProtoMsg(ProtocolConfig.SERVER_GAME + " " + winner, clientSocket);

    }

    public void checkResults(String result) {
        server.sendingProtoMsg(ProtocolConfig.SERVER_GAME + " " + result, clientSocket);

    }

}
