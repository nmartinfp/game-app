package org.academiadecodigo.bootcamp.gameapp.server;

import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.utilities.AppConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.Room;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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




    private void removeFromClientList(User user) {
        clientList.remove(user);
    }

    public void setingMap(User user, Socket clientSocket) {
        clientList.put(user, clientSocket);
    }

    public void closeSocketOfUser(Socket socket) {

        try {
            removeFromClientList(findUser(socket));
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               ROOM HANDLING
//----------------------------------------------------------------------------------------------------------------------

    public void createRPSRoom() {
        roomVector.add(new Room(2));
    }

    /*
     * Returns the User associated with a Socket
     */
    private User findUser(Socket clientSocket){

        for (User user: clientList.keySet()) {

            if (clientList.get(user).equals(clientSocket)){
                return user;
            }

        }

        return null;
    }

    /*
     * Returns the Room with the associated id if there is any, doesn't check if the room Vector is empty, not sure if
     * problematic or not
     */
    public Room roomById(String id){
        for (Room room : roomVector) {
            if(room.getId().equals(id)){
                return room;
            }
        }
        System.out.println("No room found with that Id");
        return null;
    }

    /*
     * Returns the Room a User is in or null if he isn't in any
     */
    private Room roomByUser(User user) {

        for (Room room : roomVector) {
            if (room.isUserInRoom(user)){
                return room;
            }
        }
        return null;
    }

    /*
     * Adds a user to a Room and removes it from the clientList if the room is not full yet
     * Could return a message for success or lack thereof to trigger secondary behaviours
     * Set to work on a RPS Room only!!!
     */
    public void addUserToRoom(User user, String id){
        if(roomById(id).getUsers().size() == roomById(id).getMinSize()){
            roomById(id).addUser(user);
            clientList.remove(user);
        }

        System.out.println("Sry, room full");
    }







}