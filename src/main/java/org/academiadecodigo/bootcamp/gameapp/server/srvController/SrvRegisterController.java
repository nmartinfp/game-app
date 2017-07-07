package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class SrvRegisterController {


    private Server server;
    private Socket clientSocket;
    private UserService userService;

    public SrvRegisterController(Server server, Socket clientSocket, UserService userService) {

        this.server = server;
        this.clientSocket = clientSocket;
        this.userService = userService;
    }

    public void createUser(String firstName, String username, String password) {

        if (userService.findByName(username) == null){

            User user = new User(firstName, username, password);

            userService.addUser(user);

            server.sendingProtoMsg(ProtocolConfig.SERVER_REGISTER + " " + ProtocolConfig.VIEW_LOGIN,
                    clientSocket);
            return;
        }

        server.sendingProtoMsg(ProtocolConfig.SERVER_REGISTER + " AUTH_FAILURE",
                clientSocket);
    }
}
