package org.academiadecodigo.bootcamp.gameapp.game;

/**
 * Created by Cyrille on 08/07/17.
 */
public enum Choices {

    ROCK("ROCK"),
    PAPER("PAPER"),
    SCISSORS("SCISSORS"),
    NOCHOICE("NOCHOICE");

    private String hand;

    Choices(String hand) {
        this.hand = hand;
    }

    public String getHand() {
        return hand;
    }
}
