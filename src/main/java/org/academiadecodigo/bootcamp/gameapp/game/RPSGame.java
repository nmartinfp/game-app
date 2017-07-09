package org.academiadecodigo.bootcamp.gameapp.game;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public class RPSGame {

    public String playRound(String p1Hand, String p2Hand) {

        if (p1Hand.equals(p2Hand)) {
            return "TIE";
        }


        String winner = p1Hand;

        switch (p1Hand) {

            case "ROCK":
                if (p2Hand.equals(Choices.PAPER.getHand())) {
                    winner = "PAPER";
                }
                break;

            case "PAPER":
                if (p2Hand.equals(Choices.SCISSORS.getHand())) {
                    winner = "SCISSORS";
                }
                break;

            case "SCISSORS":
                if (p2Hand.equals(Choices.ROCK.getHand())) {
                    winner = "ROCK";
                }
                break;

            case "NOCHOICE":
                if (p2Hand != Choices.NOCHOICE.getHand()) {
                    winner = p2Hand;
                }else{
                    winner = "TIE";

                }
                break;

        }

        //for every winner, send the info to the game controller
        return winner;

    }

    private void displayResults() {

        //send info that the game has ended and send the results to the game controller, including winner
        //player1.getVictories() > player2.getVictories() ? player1.getPlayer() : player2.getPlayer();

    }
}
