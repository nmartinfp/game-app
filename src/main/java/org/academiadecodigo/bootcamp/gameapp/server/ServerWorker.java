package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
// TODO: 02/07/17 change name of this class
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

            ServerParser serverParser = new ServerParser(server, clientSocket);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {

                String message = input.readLine();

                if (message.equals("null")) {

                    server.closeSocketOfUser(clientSocket);
                    return;
                }

                System.out.println("Message received: " + message);
                ProtocolParser.serverProtocolHandler(serverParser, message);

            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            System.out.println("execute finally block");
            server.closeSocketOfUser(clientSocket);
        }
    }
}
