package org.academiadecodigo.bootcamp.gameapp.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class Server {

    private final int PORT_NUMBER = 1234;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private LinkedList<Socket> clientList;
    private LinkedList<Socket[]> lobbyList;

    public Server() {
        clientList = new LinkedList<>();
        lobbyList = new LinkedList<>();
    }

    //Preparar o server
    public void Init() {
        try {

            serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("Server up.");
            Start();

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

    //Run server
    public void Start() {
        try {
            ExecutorService cachedPool = Executors.newCachedThreadPool();

            while (true) {
                System.out.println("Waiting for connection...");

                // TODO: 26/06/17 to be updated to support multiple clients with a list
                clientSocket = serverSocket.accept();


                System.out.println("Server connected.");

                clientList.add(clientSocket);
                cachedPool.submit(new ServerWorker(clientSocket, this));
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (clientSocket != null) {

                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void out(String line) {

        try {

            for (Socket socket : clientList) {
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println(line);
                output.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CreateRoom(Socket clientSocket) {
        if (lobbyList.isEmpty() || lobbyList.peekLast()[1] != null) {
            lobbyList.add(new Socket[2]);
        }
        for (Socket[] socket : lobbyList) {

            for (int i = 0; i < socket.length; i++) {
                if (socket[i] == null) {
                    socket[i] = clientSocket;
                    System.out.println("entrei no room position number: " + i);
                    System.out.println("sockets[] existentes: " + lobbyList.size());
                    break;
                }
            }


            for (Socket socket1 : clientList) {
                clientList.remove(socket1);
            }
        }
    }

    public void chatbetween2(Socket clientSocket, String line) {
        Socket[] socket = RoomDesired(clientSocket);

        try {
            for (int i = 0; i < socket.length; i++) {

                PrintWriter output = new PrintWriter(socket[i].getOutputStream(), true);
                output.println(line);
                output.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket[] RoomDesired(Socket clientSocket) {

        Socket[] sockets = null;

        for (Socket[] socket : lobbyList) {

            for (int i = 0; i < socket.length; i++) {
                if (socket[i] == clientSocket) {
                    sockets = socket;
                    return sockets;
                }
            }

        }

        return null;
    }
}