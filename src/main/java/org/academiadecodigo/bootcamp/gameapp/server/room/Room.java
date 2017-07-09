package org.academiadecodigo.bootcamp.gameapp.server.room;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

import org.academiadecodigo.bootcamp.gameapp.game.RPSGame;
import org.academiadecodigo.bootcamp.gameapp.server.ClientHandler;
import org.academiadecodigo.bootcamp.gameapp.server.State;
import org.academiadecodigo.bootcamp.gameapp.server.Workable;
import org.academiadecodigo.bootcamp.gameapp.server.lobby.Lobby;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolConfig;
import org.academiadecodigo.bootcamp.gameapp.utilities.ProtocolParser;
import org.academiadecodigo.bootcamp.gameapp.utilities.UtilsRps;

import java.util.Vector;

/*
 * Class to handle a gameRoom, creation, adding users, removing users, and whatnot
 */
public class Room implements Runnable, Workable {

    private String name;

    private Lobby lobby;
    private Vector<ClientHandler> clientHandlerVector;

    private String user1;
    private String user2;

    private String p1Hand;
    private String p2Hand;

//----CONSTRUCTORS------------------------------------------------------------------------------------------------------

    public Room(int roomSize) {
        clientHandlerVector = new Vector<>();
    }

    //To be used in games that require a range of players like Secret Hitler (HYPE)
    public Room(int minSize, int maxSize, Lobby lobby) {
        this.lobby = lobby;
        clientHandlerVector = new Vector<>();
    }

    public void init(ClientHandler clientHandler, String name) {
        clientHandlerVector.add(clientHandler);
        user1 = clientHandler.getUsername();
        this.name = name;
    }

    //Sending msg to everyone CHAT
    public void sendMsgToRoom(String message) {

        for (ClientHandler ClientHandler : clientHandlerVector) {
            ClientHandler.sendMessage(message);
        }
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public synchronized void process(ClientHandler clientHandler, String message) {

        String[] tokens = ProtocolParser.splitMessage(message);

        if (message.contains(ProtocolConfig.CLIENT_CHAT)) {

            String protoMessage = ProtocolConfig.SERVER_CHAT_ROOM + ";" + clientHandler.getUsername()
                    + ": " + tokens[ProtocolConfig.MESSAGE];
            System.out.println(protoMessage);

            sendMsgToRoom(protoMessage);
            return;
        }
        if (message.contains(ProtocolConfig.CLIENT_GAME)) {

            if (user1.equals(clientHandler.getUsername())) {

                p1Hand = tokens[ProtocolConfig.MESSAGE];
                System.out.println(p1Hand);

            } else if (user2.equals(clientHandler.getUsername())) {

                p2Hand = tokens[ProtocolConfig.MESSAGE];
                System.out.println(p2Hand);
            }

            if (p1Hand != null && p2Hand != null) {
                System.out.println("2 notify");
                notifyAll();
            }
        }
    }

    @Override
    public synchronized void run() {

        RPSGame rpsGame = new RPSGame();

        // Waiting for 2 player
        try {
            System.out.println("primeiro wait");
            wait();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Playing Round
        playRps(rpsGame);

        gameOver();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //End of the game
        for (ClientHandler clientHandler : clientHandlerVector) {

            clientHandler.setWorkable(lobby);
            clientHandler.setState(State.LOBBY);

            lobby.addQueue(clientHandler);
            sendUsersToLobby(clientHandler);
        }

        lobby.removeRoom(this);
    }

    private void gameOver() {

        for (ClientHandler clientHandler: clientHandlerVector) {
            clientHandler.sendMessage(ProtocolConfig.SERVER_ROOM_EXIT + ";" + "GAME OVER");
        }
    }

    private void sendUsersToLobby(ClientHandler clientHandler) {
            clientHandler.sendMessage(ProtocolConfig.SERVER_ROOM_EXIT + ";" + ProtocolConfig.VIEW_LOBBY);
    }

    private void playRps(RPSGame rpsGame) {

        int roundsUser1 = 0;
        int roundsUser2 = 0;

        while (roundsUser1 < UtilsRps.MAXWINS && roundsUser2 < UtilsRps.MAXWINS) {
            try {
                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String winner;

            winner = rpsGame.playRound(p1Hand, p2Hand);
            showOtherHand();

            if (winner.equals(p1Hand)) {

                ++roundsUser1;
                sendMsgGame(user1);
                System.out.println("Rounds win player 1");

            } else if (winner.equals(p2Hand)) {

                ++roundsUser2;
                sendMsgGame(user2);
                System.out.println("Round win player 2");

            } else {
                sendMsgGame(null);
            }

            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (roundsUser1 < UtilsRps.MAXWINS && roundsUser2 < UtilsRps.MAXWINS) {
                resetRoom();
            }
        }
    }

    private void resetRoom() {

        for (ClientHandler clientHandler : clientHandlerVector) {
            clientHandler.sendMessage(ProtocolConfig.SERVER_RESET_ROOM + ";" + "new Round!!!");
        }

        p1Hand = null;
        p2Hand = null;
    }

    private void showOtherHand() {
        for (ClientHandler clientHandler : clientHandlerVector) {

            if (clientHandler.getUsername().equals(user1)) {
                clientHandler.sendMessage(ProtocolConfig.SERVER_OTHER_HAND + ";" + p2Hand);

            } else {
                clientHandler.sendMessage(ProtocolConfig.SERVER_OTHER_HAND + ";" + p1Hand);
            }
        }
    }

    private void sendMsgGame(String user) {

        for (ClientHandler clientHandler : clientHandlerVector) {

            if (clientHandler.getUsername().equals(user)) {
                clientHandler.sendMessage(ProtocolConfig.SERVER_GAME + ";" + "YOU WIN!");

            } else {
                if (user == null) {
                    clientHandler.sendMessage(ProtocolConfig.SERVER_GAME + ";" + "TIE!");

                } else
                    clientHandler.sendMessage(ProtocolConfig.SERVER_GAME + ";" + "YOU LOSE!");
            }
        }
    }

    public synchronized void addClientHandler(ClientHandler clientHandler) {

        clientHandlerVector.add(clientHandler);
        user2 = clientHandler.getUsername();

        notifyAll();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
