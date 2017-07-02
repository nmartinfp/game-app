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

    public RPSGame(int maxRounds, Player player1, Player player2) {
        this.maxRounds = maxRounds;
        this.player1 = player1;
        this.player2 = player2;

    }

    public void start() {

        while (rounds < maxRounds) {
            playRound();
        }

        if (checkifTie()) {

            RPSGame extra = new RPSGame(1, player1, player2);
            extra.start();
            return;
        }

        displayResults();

    }

    private void playRound() {

        Choices p1Hand = player1.chooseHand();
        Choices p2Hand = player2.chooseHand();

        rounds++;

        //if it's a tie, play another round:
        if (p1Hand == p2Hand) {

            //send a message that it's a tie to the game controller,
            playRound();
            return;
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
        winner.win();

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
        ROCK,
        PAPER,
        SCISSORS
    }

    public class Player {

        private User player;
        private int victories;

        public Player(User player) {

            this.player = player;
        }

        public void win() {
            victories++;
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
