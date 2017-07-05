package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.service.ServiceRegistry;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.SrvLobbyController;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.SrvLoginController;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.SrvRegisterController;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;

import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class ServerParser {

    private Server server;
    private Socket clientSocket;
    private UserService userService;

    public ServerParser(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;

        userService = ServiceRegistry.getInstance().getService(UserService.class.getSimpleName());
    }

    public void rpsGame(String[] protocol) {
        throw new UnsupportedOperationException();
    }

    public void serverComm(String[] protocol) {
        throw new UnsupportedOperationException();
    }

    public void loginUser(String[] protocol) {

        SrvLoginController loginController = new SrvLoginController(server, clientSocket, userService);
        loginController.authenticate(protocol[1], protocol[2]);
    }

    public void registerUSer(String[] protocol) {

        System.out.println("Message forwarded");        //Testing
        SrvRegisterController registerController = (new SrvRegisterController(server, clientSocket, userService));
        registerController.createUser(protocol[1], protocol[2], protocol[3]);
        System.out.println("fui autenticado");      //testing
    }

    public void clientComm(String message) {

        String name = server.findUser(clientSocket).getUsername();
        String fullMessage = ProtocolConfig.CLIENT_CHAT + " " + name + " : " + message;

        server.sendingProtoMsgAll(fullMessage);
    }

    // TODO: 05/07/17 if we wanna chat with user \n message
    public void clientComm2Lines(String message) {

        String name = server.findUser(clientSocket).getUsername();
        String userName = ProtocolConfig.CLIENT_CHAT + " " + name + ":";
        server.sendingProtoMsgAll(userName);

        String userMessage = ProtocolConfig.CLIENT_CHAT + " " + message;
        server.sendingProtoMsgAll(userMessage);
    }

    public void creatingRoom(String message) {
        System.out.println("ESTOU HANDLER ");
        if (message.contains("createRoom")){
            System.out.println("ENTREI NA CONDIÇAO \n");
            SrvLobbyController lobbyController = new SrvLobbyController(server, clientSocket, userService);
            lobbyController.RPSRoomRequest();
            return;
        }
    }
}
