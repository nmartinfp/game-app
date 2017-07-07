package org.academiadecodigo.bootcamp.gameapp.server.lobby;

import org.academiadecodigo.bootcamp.gameapp.server.Game;
import org.academiadecodigo.bootcamp.gameapp.server.ServerHandler;
import org.academiadecodigo.bootcamp.gameapp.server.Workable;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.room.Room;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Cyrille on 06/07/17.
 */
public class Lobby implements Runnable, Workable {

    private ConcurrentHashMap<User, Socket> clientMap;
    private Vector<Room> roomVector;

    public Lobby() {
        clientMap = new ConcurrentHashMap<>();
        roomVector = new Vector<>();
    }

    /*
     * Process information from Client to Lobby
     */
    @Override
    public void process(ServerHandler serverHandler, String message) {

    }

    @Override
    public void run() {

    }

    //Sending msg to everyone CHAT
    public void sendToAll(String message) {

        for (Socket socket : clientMap.values()) {
            sendToClient(message, socket);
        }
    }

    //Sending msg just for one client
    public void sendToClient(String message, Socket clientSocket) {

        try {

            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println(message);
            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               ClientMap HANDLING
//----------------------------------------------------------------------------------------------------------------------

    private void removeFromClientMap(User user) {
        clientMap.remove(user);
    }

    private void addToClientMap(User user, Socket clientSocket) {
        clientMap.put(user, clientSocket);
    }

    public void closeClientSocket(Socket clienSocket) {
        try {
            clienSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               ROOM HANDLING
//----------------------------------------------------------------------------------------------------------------------

    public void createRoom(ServerHandler serverHandler, Game game) {


    }

    /*
     * Returns the Room with the associated id if there is any, doesn't check if the room Vector is empty, not sure if
     * problematic or not
     */
    public Room roomByName(String name) {

        for (Room room : roomVector) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    /*
    * Adds a user to a Room and removes it from the clientList if the room is not full yet
    * Could return a message for success or lack thereof to trigger secondary behaviours
    */
    public void addUserToRoom(User user, String name) {
        roomByName(name).addUser(user);
        clientMap.remove(user);
    }


}
