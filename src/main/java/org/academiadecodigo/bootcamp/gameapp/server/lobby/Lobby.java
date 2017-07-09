package org.academiadecodigo.bootcamp.gameapp.server.lobby;

import org.academiadecodigo.bootcamp.gameapp.game.GameName;
import org.academiadecodigo.bootcamp.gameapp.server.ClientHandler;
import org.academiadecodigo.bootcamp.gameapp.server.State;
import org.academiadecodigo.bootcamp.gameapp.server.Workable;
import org.academiadecodigo.bootcamp.gameapp.server.model.User;
import org.academiadecodigo.bootcamp.gameapp.server.room.Room;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.Logger;
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.PriorityLevel;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
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
    public void process(ClientHandler clientHandler, String message) {

        String[] tokens = ProtocolParser.splitMessage(message);

        if (message.contains(ProtocolConfig.CLIENT_CHAT)) {
            String protoMessage = ProtocolConfig.SERVER_CHAT + ";" + clientHandler.getUsername() + ": " + tokens[ProtocolConfig.MESSAGE];
            System.out.println(protoMessage);
            sendToAll(protoMessage);

            return;
        }

        if (message.contains(ProtocolConfig.CLIENT_CREATE_ROOM)) {
            createRoom(clientHandler, GameName.RPS);

            return;
        }

        if (message.contains(ProtocolConfig.CLIENT_JOIN_ROOM)) {

            addClientToRoom(clientHandler, tokens[ProtocolConfig.MESSAGE]);
        }


    }


    @Override
    public void run() {

        while (true) {
            if (queue.size() > 0) {
                ClientHandler clientHandler = queue.poll();

                clientVector.add(clientHandler);

                if (clientHandler.getState().equals(State.LOBBY)) {
                    updatingRooms();
                }
            }
        }
    }

    public void updatingRooms() {

        for (Room room : roomVector) {
            sendToAll(ProtocolConfig.SERVER_REGISTER_ROOM + ";" + room.getName());
        }
    }


    //Sending msg to everyone CHAT
    private void sendToAll(String message) {

        for (ClientHandler clientHandler : clientVector) {
            clientHandler.sendMessage(message);
        }
    }


//----------------------------------------------------------------------------------------------------------------------
//                                               CLIENTMAP HANDLING
//----------------------------------------------------------------------------------------------------------------------

    public synchronized void addQueue(ClientHandler clientHandler) {
        queue.add(clientHandler);
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        clientVector.remove(clientHandler);
    }
//----------------------------------------------------------------------------------------------------------------------
//                                               ROOM HANDLING
//----------------------------------------------------------------------------------------------------------------------

    private void createRoom(ClientHandler clientHandler, GameName gameName) {

        Room room = new Room(gameName.getMinUsers(), gameName.getMaxUsers(), this);
        String roomName = clientHandler.getUsername() + "'s Room";
        room.init(clientHandler, roomName);

        clientHandler.changeState(room, State.ROOM);

        roomVector.add(room);
        clientVector.remove(clientHandler);

        sendToAll(ProtocolConfig.SERVER_REGISTER_ROOM + ";" + roomName);
        clientHandler.sendMessage(ProtocolConfig.SERVER_CREATE_ROOM + ";" + ProtocolConfig.VIEW_RPS);

        Thread thread = new Thread(room);
        thread.start();
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
    private void addClientToRoom(ClientHandler clientHandler, String name) {

        Room room = roomByName(name);

        if (room != null && room.usersOnRoom() < GameName.RPS.getMaxUsers()) {
            room.addClientHandler(clientHandler);
            clientHandler.changeState(room, State.ROOM);
            clientVector.remove(clientHandler);

            clientHandler.sendMessage(ProtocolConfig.SERVER_JOIN_ROOM + ";" + ProtocolConfig.VIEW_RPS);
        }
    }

    public synchronized void removeRoom(Room roomToRemove) {

        for (Room room : roomVector) {

            if (room.equals(roomToRemove)) {
                roomVector.remove(room);
            }

            sendToAll(ProtocolConfig.SERVER_UNREGISTER_ROOM + ";" + roomToRemove.getName());
        }
    }


    public boolean logedUser(String username) {

        for (ClientHandler clientHandler : clientVector) {

            if (clientHandler.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
