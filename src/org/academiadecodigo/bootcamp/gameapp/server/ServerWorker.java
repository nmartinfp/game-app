package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.persistence.ConnectionManager;
import org.academiadecodigo.bootcamp.gameapp.server.service.user.JdbcUserService;
import org.academiadecodigo.bootcamp.gameapp.server.srvController.srvLoginController;
import org.academiadecodigo.bootcamp.gameapp.utilities.CommProtocol;

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
    public void forwardComm(String message){

        String[] protocol = message.split(" ");

        switch (protocol[0]){
            case "@Server":
                System.out.println("Message forwarded");
                (new srvLoginController(server, new JdbcUserService(new ConnectionManager().getConnection()))).authenticate(protocol[1], protocol[2]);
                break;
            case "@Client":
                server.out(message);
                break;

        }
    }
}
