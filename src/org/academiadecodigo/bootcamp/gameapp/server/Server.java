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
// TODO: 02/07/17 we aren't close server sokcet
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
    //Sending mesg to everyone CHAT
    public void sendingProtoMsgAll(String message) {

        for (Socket socket : clientList) {
            sendingProtoMsg(message, socket);
        }
    }

    //Sending msg just for one client
    public void sendingProtoMsg(String message, Socket clientSocket) {

        try {

            System.out.println("Entrei aqui server para enviar a mensagem para o client ");         //todo TESTING
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println(message);
            output.flush();
            System.out.println("sending this msg to client " + message);            //todo Testing

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chatbetweenTwo(Socket clientSocket, String line) {
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

    // TODO: 02/07/17 think in LinkedList.peek()
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