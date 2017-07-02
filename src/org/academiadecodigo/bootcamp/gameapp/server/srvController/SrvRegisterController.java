package org.academiadecodigo.bootcamp.gameapp.server.srvController;

import org.academiadecodigo.bootcamp.gameapp.server.Server;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

import java.net.Socket;

/**
 * Created by codecadet on 01/07/2017.
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

        User user = new User(firstName, username, password);

        userService.addUser(user);

        server.sendingProtoMsg(ProtocolConfig.SERVER_REGISTER + " " + ProtocolConfig.LOGIN_VIEW,
                clientSocket);
    }
}
