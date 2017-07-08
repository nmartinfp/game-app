package org.academiadecodigo.bootcamp.gameapp.server.lobby;

import org.academiadecodigo.bootcamp.gameapp.game.GameName;
import org.academiadecodigo.bootcamp.gameapp.server.ClientHandler;
import org.academiadecodigo.bootcamp.gameapp.server.State;
import org.academiadecodigo.bootcamp.gameapp.server.Workable;
import org.academiadecodigo.bootcamp.gameapp.server.room.Room;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * Created by Cyrille on 06/07/17.
 */
public class Lobby implements Runnable, Workable {

    //List of client's on lobby
    private Vector<ClientHandler> clientVector;

    //List of clients in queue to enter on lobby
    private Queue<ClientHandler> queue;
    private Vector<Room> roomVector;

    public Lobby() {
        clientVector = new Vector<>();
        roomVector = new Vector<>();
        queue = new ConcurrentLinkedDeque<>();
    }

    /*
     * Process information from Client to Lobby
     */
    @Override
    public void process(ClientHandler ClientHandler, String message) {

        String[] tokens = ProtocolParser.splitMessage(message);

        if (message.contains(ProtocolConfig.CLIENT_CHAT)){
            sendToAll(tokens[ProtocolConfig.MESSAGE]);
            return;
        }

        if (message.contains(ProtocolConfig.CLIENT_CREATE_ROOM)){

            createRoom(ClientHandler, GameName.RPS);
            return;
        }

        if (message.contains(ProtocolConfig.CLIENT_JOIN_ROOM)){

            addClientToRoom(ClientHandler, message);
        }
    }

    @Override
    public void run() {

        while (true) {
            if (queue.size() > 0) {
                System.out.println("estou aqui no run do lobby");
                clientVector.add(queue.poll());
            }
        }
    }

    //Sending msg to everyone CHAT
    public void sendToAll(String message) {

        String protoMessage = ProtocolConfig.SERVER_CHAT + ";" + message;
        System.out.println(protoMessage);

        for (ClientHandler ClientHandler : clientVector) {
            ClientHandler.sendMessage(protoMessage);
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               ClientMap HANDLING
//----------------------------------------------------------------------------------------------------------------------

    public void closeClientSocket(Socket clientSocket) {
        try {
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQueue(ClientHandler ClientHandler) {
        queue.add(ClientHandler);
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               ROOM HANDLING
//----------------------------------------------------------------------------------------------------------------------

    private void createRoom(ClientHandler ClientHandler, GameName gameName) {

        Room room = new Room(gameName.getMinUsers(), gameName.getMaxUsers(), this);
        room.init(ClientHandler, ClientHandler.getUsername() + "Room", gameName);

        ClientHandler.changeState(room, State.ROOM);

        roomVector.add(room);
        clientVector.remove(ClientHandler);

        new Thread(room);
    }

    /*
     * Returns the Room with the associated id if there is any, doesn't check if the room Vector is empty, not sure if
     * problematic or not
     */
    private Room roomByName(String name) {

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
    private void addClientToRoom(ClientHandler ClientHandler, String name) {

        Room room = roomByName(name);

        if (room != null) {

            room.addServerHandler(ClientHandler);
            ClientHandler.changeState(room, State.ROOM);

            clientVector.remove(ClientHandler);
        }
    }
}
