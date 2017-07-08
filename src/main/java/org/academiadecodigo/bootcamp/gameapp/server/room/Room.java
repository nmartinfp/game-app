package org.academiadecodigo.bootcamp.gameapp.server.room;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

import org.academiadecodigo.bootcamp.gameapp.game.GameName;
import org.academiadecodigo.bootcamp.gameapp.game.RPSGame;
import org.academiadecodigo.bootcamp.gameapp.server.ClientHandler;
import org.academiadecodigo.bootcamp.gameapp.server.Workable;
import org.academiadecodigo.bootcamp.gameapp.server.lobby.Lobby;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;

import java.util.Vector;

/**
 * Class to handle a gameRoom, creation, adding users, removing users, and whatnot
 */
public class Room implements Runnable,Workable {

    private String name;

    private Lobby lobby;
    private Vector<ClientHandler> clientHandlerVector;
    private GameName gameName;

    private int minSize;
    private int maxSize;

//----CONSTRUCTORS------------------------------------------------------------------------------------------------------

    public Room(int roomSize) {
        minSize = roomSize;
        maxSize = roomSize;
        clientHandlerVector = new Vector<>();
    }

    //To be used in games that require a range of players like Secret Hitler (HYPE)
    public Room(int minSize, int maxSize, Lobby lobby) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.lobby = lobby;
        clientHandlerVector = new Vector<>();
    }

    public void init(ClientHandler ClientHandler, String name, GameName gameName){
        clientHandlerVector.add(ClientHandler);
        this.name = name;
        this.gameName = gameName;
    }

    //Sending msg to everyone CHAT
    public void sendMsgToRoom(String message) {

        for (ClientHandler ClientHandler : clientHandlerVector) {
            ClientHandler.sendMessage(message);
        }
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public void process(ClientHandler ClientHandler, String message) {

        String[] tokens = ProtocolParser.splitMessage(message);

        if (message.contains(ProtocolConfig.CLIENT_CHAT)){
            String protoMessage = ProtocolConfig.SERVER_CHAT_ROOM + ";" + tokens[ProtocolConfig.MESSAGE];
            System.out.println(protoMessage);
            sendMsgToRoom(protoMessage);
            return;
        }
        if (message.contains(ProtocolConfig.CLIENT_GAME)) {



        }
    }

    @Override
    public void run() {

        RPSGame rpsGame = new RPSGame();

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






        //End of the game
        for (ClientHandler ClientHandler : clientHandlerVector) {
            lobby.addQueue(ClientHandler);
        }
    }

    public synchronized void addClientHandler(ClientHandler ClientHandler) {

        clientHandlerVector.add(ClientHandler);
        if (clientHandlerVector.size() == 2) {
            notifyAll();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
