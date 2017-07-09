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
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.Logger;
import org.academiadecodigo.bootcamp.gameapp.utilities.logging.PriorityLevel;

import java.util.Vector;


/**
 * Class to handle a gameRoom, creation, adding users, removing users, and whatnot
 */
public class Room implements Runnable, Workable {

    private String name;

    private Lobby lobby;
    private Vector<ClientHandler> clientHandlerVector;
    private GameName gameName;

    private int minSize;
    private int maxSize;

    private int count = 0;
    private String[] choices;

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


    public void init(ClientHandler ClientHandler, String name, GameName gameName) {
        clientHandlerVector.add(ClientHandler);
        this.name = name;
        this.gameName = gameName;
    }


    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public void process(ClientHandler ClientHandler, String message) {

        String[] tokens = ProtocolParser.splitMessage(message);

        if (message.contains(ProtocolConfig.CLIENT_CHAT)) {

            sendToAll(tokens[ProtocolConfig.MESSAGE]);

            return;
        }

        if (message.contains(ProtocolConfig.CLIENT_GAME)) {

            choices[count] = tokens[ProtocolConfig.MESSAGE];

            count++;
        }
    }


    @Override
    public void run() {

        RPSGame rpsGame = new RPSGame();

        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Logger.getInstance().log(PriorityLevel.HIGH, "Room run waiting InterruptedException: " + e.getMessage());
        }

        if (choices.length == 2) {

            String winner = rpsGame.playRound(choices[0], choices[1]); // TODO: 2017/7/9 - Not used!!!

        }

        //End of the game
        for (ClientHandler ClientHandler : clientHandlerVector) {
            lobby.addQueue(ClientHandler);
        }
    }


    private void sendToAll(String token) { // TODO: 2017/7/9 - Not used!!!
    }


    public synchronized void addServerHandler(ClientHandler ClientHandler) {

        clientHandlerVector.add(ClientHandler);
        notifyAll();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
