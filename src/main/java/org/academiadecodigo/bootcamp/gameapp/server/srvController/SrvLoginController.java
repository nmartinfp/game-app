package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

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

        if (userService.authenticate(user, pass) && server.findUser(clientSocket) == null) {
            server.sendingProtoMsg(ProtocolConfig.SERVER_LOGIN + " " + ProtocolConfig.VIEW_LOBBY,
                    clientSocket);
            server.setingMap(userService.findByName(user), clientSocket);
            return;
        }

        server.sendingProtoMsg(ProtocolConfig.SERVER_LOGIN + " AUTH_FAILURE" , clientSocket);
    }
}
