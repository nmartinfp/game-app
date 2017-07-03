package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;

import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class SrvLobbyController {

    private Server server;
    private Socket clientSocket;
    private UserService userService;

    public SrvLobbyController(Server server, Socket clientSocket, UserService userService) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.userService = userService;

    }

    public void incomingMsg(String message) {
        server.sendingProtoMsgAll(message);

    }

    public void RPSRoomRequest() {
        server.createRPSRoom();

    }

}
