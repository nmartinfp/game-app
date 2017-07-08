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
    public void process(ClientHandler clientHandler, String message) {

        String[] tokens = ProtocolParser.splitMessage(message);

        if (message.contains(ProtocolConfig.CLIENT_CHAT)){
            String protoMessage = ProtocolConfig.SERVER_CHAT + ";" + tokens[ProtocolConfig.MESSAGE];
            System.out.println(protoMessage);
            sendToAll(protoMessage);
            return;
        }

        if (message.contains(ProtocolConfig.CLIENT_CREATE_ROOM)){
            createRoom(clientHandler, GameName.RPS);
            return;
        }

        if (message.contains(ProtocolConfig.CLIENT_JOIN_ROOM)){
            addClientToRoom(clientHandler, message);
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

        for (ClientHandler ClientHandler : clientVector) {
            ClientHandler.sendMessage(message);
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

    public void addQueue(ClientHandler clientHandler) {
        queue.add(clientHandler);
    }

//----------------------------------------------------------------------------------------------------------------------
//                                               ROOM HANDLING
//----------------------------------------------------------------------------------------------------------------------

    private void createRoom(ClientHandler clientHandler, GameName gameName) {

        Room room = new Room(gameName.getMinUsers(), gameName.getMaxUsers(), this);
        String roomName = clientHandler.getUsername() + "'s Room";
        room.init(clientHandler, roomName, gameName);

        clientHandler.changeState(room, State.ROOM);

        roomVector.add(room);
        clientVector.remove(clientHandler);

        sendToAll(ProtocolConfig.SERVER_REGISTER_ROOM + ";" + roomName);
        clientHandler.sendMessage(ProtocolConfig.SERVER_CREATE_ROOM + ";" +ProtocolConfig.VIEW_RPS);


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
    private void addClientToRoom(ClientHandler clientHandler, String name) {

        Room room = roomByName(name);

        if (room != null) {

            room.addServerHandler(clientHandler);
            clientHandler.changeState(room, State.ROOM);

            clientVector.remove(clientHandler);

            clientHandler.sendMessage(ProtocolConfig.SERVER_JOIN_ROOM + ";" +ProtocolConfig.VIEW_RPS);
        }
    }
}
