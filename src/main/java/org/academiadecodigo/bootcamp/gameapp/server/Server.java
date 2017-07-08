package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.lobby.Lobby;
import org.academiadecodigo.bootcamp.gameapp.utilities.AppConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
// TODO: 02/07/17 we aren't closing server socket
public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Lobby lobby;

    public Server() {
    }

    public void init() {

        try {

            serverSocket = new ServerSocket(AppConfig.PORT);
            System.out.println("Server up.");

            lobby = new Lobby();
            Thread thread = new Thread(lobby);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {

        try {
            ExecutorService cachedPool = Executors.newCachedThreadPool();

            while (true) {
                System.out.println("Waiting for connection...");

                clientSocket = serverSocket.accept();
                ClientHandler ClientHandler = new ClientHandler(clientSocket, lobby, State.LOGIN);
                System.out.println("Server connected.");

                cachedPool.submit(ClientHandler);
                lobby.addQueue(ClientHandler);

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
        }
    }
}
