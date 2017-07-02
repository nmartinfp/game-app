package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.service.ServiceRegistry;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.SrvLoginController;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.SrvRegisterController;

import java.net.Socket;

/**
 * Created by Cyrille on 02/07/17.
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

    // TODO: 01/07/17 create method @server to forward communications
    public void forwardComm(String message) {

        String[] protocol = message.split(" ");

        switch (protocol[0]) {
            case "@SERVER_LOGIN":
                loginUser(protocol);
                break;
            case "@SERVER_REGISTER":
                registerUSer(protocol);
                break;
            case "@SERVER_LOBBY":
                rpsGame(protocol);
                break;
            case "@SERVER_GAME":
                throw new UnsupportedOperationException();
            case "@CLIENT":
                clientComm(message);
                break;
        }
    }

    private void rpsGame(String[] protocol) {
        throw new UnsupportedOperationException();
    }

    private void serverComm(String[] protocol) {
        throw new UnsupportedOperationException();
    }

    private void loginUser(String[] protocol) {

        SrvLoginController loginController = new SrvLoginController(server, clientSocket, userService);
        loginController.authenticate(protocol[1], protocol[2]);
    }

    private void registerUSer(String[] protocol) {

        System.out.println("Message forwarded");        //Testing
        SrvRegisterController registerController = (new SrvRegisterController(server, clientSocket, userService));
        registerController.createUser(protocol[1], protocol[2], protocol[3]);
        System.out.println("fui autenticado");      //testing
    }

    // TODO: 02/07/17 this method is sending message to everyone &&change it
    private void clientComm(String message) {
        server.sendingProtoMsgAll(message);
    }
}
