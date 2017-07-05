package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.utilities.AppConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.Room;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
// TODO: 02/07/17 we aren't closing server socket
public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ConcurrentHashMap<User, Socket> clientList;
    private Vector<Room> roomVector;  // TODO: 03/07/17 I'm not sure about this one

    public Server() {
        clientList = new ConcurrentHashMap<>();

    }

    public ConcurrentHashMap<User, Socket> getClientList() {
        return clientList;
    }

    //Preparer server
    public void init() {
        try {

            serverSocket = new ServerSocket(AppConfig.PORT);
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

        for (Socket socket : clientList.values()) {
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

    // TODO: 01/07/17 this method go to @server controller LOBBY
    // TODO: 03/07/17 Create Room factory??
    public void createRPSRoom() {
        roomVector.add(new Room(2));
    }



    private void removeFromClientList(User user) {
        clientList.remove(user);
    }


    // TODO: 03/07/17 Refactor to take the ConcurrentHashMap into account
    /*
     * Returns the Room a User is in if he is in any, if not returns null
     */
    private Room roomByUser(User user) {

        for (Room room : roomVector) {
            if (room.isUserInRoom(user)){
                return room;
            }
        }
        return null;
    }

    public void closeSocketOfUser(Socket socket) {

        try {
            removeFromClientList(findUser(socket));
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //add the authenticate user  to a hashmap
    public void setingMap(User user, Socket clientSocket) {
        clientList.put(user, clientSocket);
    }
    
    public User findUser(Socket clientSocket){

        for (User user: clientList.keySet()) {

            if (clientList.get(user).equals(clientSocket)){

                return user;
            }
        }
        return null;
    }
}