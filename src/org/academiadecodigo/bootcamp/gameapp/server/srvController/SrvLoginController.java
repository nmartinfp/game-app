package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;

import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class SrvLoginController {

    private Server server;
    private Socket clientSocket;
    private UserService userService;

    public SrvLoginController(Server server, Socket clientSocket, UserService userService) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.userService = userService;
    }

    public void authenticate(String user, String pass) {
        System.out.print("Authenticating: ");

        if (userService.authenticate(user, pass)) {

            System.out.println("authentication true");
            server.sendingProtoMsg(CommProtocol.SERVER_LOGIN.getProtocol() + "lobby", clientSocket);
            return;
        }

        server.sendingProtoMsg(CommProtocol.SERVER_LOGIN.getProtocol() + "AUTH_FAILURE" , clientSocket);
    }
}
