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

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private LinkedList<Socket> clientList;
    private LinkedList<Socket[]> lobbyList;

    public Server() {
        clientList = new LinkedList<>();
        lobbyList = new LinkedList<>();
    }

    //Preparer server
    public void init() {
        try {

            serverSocket = new ServerSocket(1234);
            System.out.println("Server up.");

            start();

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
    public void start() {
        try {
            ExecutorService cachedPool = Executors.newCachedThreadPool();

            while (true) {
                System.out.println("Waiting for connection...");

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

    // TODO: 01/07/17 this method go to @server controllers 'LOGIN,REGISTER,LOBBY,ROOM'
    public void out(String line) {
        try {

            for (Socket socket : clientList) {
                System.out.println("vou enviar a mensagem");
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println(line);
                output.flush();
                System.out.println("enviei esta mensagem + " + line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: 01/07/17 this method go to @server controller LOBBY
    public void createRoom(Socket clientSocket) {

        if (lobbyList.isEmpty() || lobbyList.peekLast()[1] != null) {
            lobbyList.add(new Socket[2]);
        }

        for (Socket[] socket : lobbyList) {

            for (int i = 0; i < socket.length; i++) {
                if (socket[i] == null) {
                    socket[i] = clientSocket;
                    System.out.println("entrei no room position number: " + i);                 //For testing
                    System.out.println("sockets[] existentes: " + lobbyList.size());            //For Testing
                    break;
                }
            }
            removeClientList(clientSocket);
        }
    }

    private void removeClientList(Socket clientSocket) {
        clientList.remove(clientSocket);
    }

    public void chatbetween2(Socket clientSocket, String line) {
        Socket[] socket = roomDesired(clientSocket);

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

    private Socket[] roomDesired(Socket clientSocket) {
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

    public void closeSocket(Socket clientSocket) {

        try {
            removeClientList(clientSocket);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}