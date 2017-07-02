package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.JdbcUserService;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.UserService;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.SrvLoginController;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.SrvRegisterController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class ServerWorker implements Runnable {

    private Socket clientSocket;
    private BufferedReader input;
    private Server server;

    public ServerWorker(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {

                String message = input.readLine();

                if (message.equals("null")) {

                    server.closeSocket(clientSocket);
                    return;
                }

                System.out.println("Message received: " + message);
                forwardComm(message);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: 01/07/17 create method @server to forward communications
    private void forwardComm(String message) {

        String[] protocol = message.split(" ");

        // TODO: 02/07/2017 - Do you want this shit? !! Are you shure?!!
        String procotolMessage = null;

        for (int i = 1; i < protocol.length; i++) {

            procotolMessage += protocol[i];
        }

        switch (protocol[0]) {
            case "@Server":
                serverComm(protocol);
                break;
            case "@Client":
                clientComm(message);
                break;

        }
    }

    private void serverComm(String[] protocol) {

        UserService userService;

        userService = new JdbcUserService(new ConnectionManager().getConnection());

        System.out.println("Message forwarded");
        SrvRegisterController srvRegisterController = (new SrvRegisterController(server, userService));
        srvRegisterController.createUser(protocol[1], protocol[2], protocol[3]);
        System.out.println("fui autenticado");

    }

    private void clientComm(String message) {
        server.out(message);
    }
}
