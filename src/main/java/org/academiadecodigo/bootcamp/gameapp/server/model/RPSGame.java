package org.academiadecodigo.bootcamp.gameapp.server.model;

import java.net.Socket;
import java.util.LinkedList;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class RPSGame {

    private LinkedList<Socket[]> clientList;
    private Player player1;
    private Player player2;
    private int maxRounds;
    private int rounds = 0;

    public RPSGame(int maxRounds, User player1, User player2) {
        this.maxRounds = maxRounds;
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);

    }

    public void start() {

        while (rounds < maxRounds) {
            playRound();
        }

        if (checkifTie()) {

            //RPSGame extra = new RPSGame(1, player1, player2);
            //extra.start();
            return;
        }

        displayResults();

    }

    public Player playRound() {

        Choices p1Hand = player1.chooseHand();
        Choices p2Hand = player2.chooseHand();

        rounds++;

        //if it's a tie, play another round:
        if (p1Hand == p2Hand) {

            //send a message that it's a tie to the game controller,

            playRound();
        }

        Player winner = player1;

        switch (p1Hand) {

            case ROCK:
                if (p2Hand == Choices.PAPER) {
                    winner = player2;
                }
                break;

            case PAPER:
                if (p2Hand == Choices.SCISSORS) {
                    winner = player2;
                }
                break;

            case SCISSORS:
                if (p2Hand == Choices.ROCK) {
                    winner = player2;
                }
                break;
        }

        //for every winner, send the info to the game controller
        return winner.win();

    }

    private boolean checkifTie() {

        if (player1.getVictories() == player2.getVictories()) {
            return true;

        }
        return false;
    }

    private void displayResults() {

        //send info that the game has ended and send the results to the game controller, including winner
        //player1.getVictories() > player2.getVictories() ? player1.getPlayer() : player2.getPlayer();

    }

    public enum Choices {
        ROCK("Rock"),
        PAPER("Paper"),
        SCISSORS("Scissors");

        private String symbol;

        Choices(String symbol) {
            this.symbol = symbol;

        }

        public String getSymbol() {
            return this.symbol;
        }
    }

    public class Player {

        private User player;
        private int victories;

        public Player(User player) {

            this.player = player;
        }

        public Player win() {
            victories++;
            return this;
            //send info that this player won to the game controller

        }

        public Choices chooseHand() {

            //waits for game controller to say which hand did the player choose
            Choices hand = null;
            return hand;

        }

        public User getPlayer() {
            return player;

        }

        public int getVictories() {
            return victories;

        }

    }

}
