package org.academiadecodigo.bootcamp.gameapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet Helder Matos on 26/06/17.
 */
public class Server implements Runnable {

    private final int PORT_NUMBER = 1234;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server(){

        try {

            serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server up.");

            ExecutorService cachedPool = Executors.newCachedThreadPool();

            while (true) {
                System.out.println("Waiting for connection...");

                // TODO: 26/06/17 to be updated to support multiple clients with a list
                clientSocket = serverSocket.accept();
                System.out.println("Server connected.");

                cachedPool.submit(new ServerConnection(clientSocket));

            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            if (serverSocket != null) {
                try {
                    serverSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (clientSocket != null) {
                try {
                    clientSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void run() {

    }
}