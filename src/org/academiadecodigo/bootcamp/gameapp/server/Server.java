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

    public Server() {
        clientList = new LinkedList<>();
    }

    //Preparar o server
    public void Init(){
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

            for (Socket socket: clientList) {
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println(line);
                output.flush();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}