package org.academiadecodigo.bootcamp.gameapp.server;

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

        String protocol = message.split(" ")[0];

        switch (protocol){
            case "@Server":
                server.createRoom(clientSocket);
                break;
            case "@Client":
                server.out(message);
                break;

        }
    }
}
