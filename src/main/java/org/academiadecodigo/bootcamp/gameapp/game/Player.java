package org.academiadecodigo.bootcamp.gameapp.game;

import org.academiadecodigo.bootcamp.gameapp.server.model.User;

/**
 * Created by Cyrille on 07/07/17.
 */
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

    public RPSGame.Choices chooseHand() {

        //waits for game controller to say which hand did the player choose
        RPSGame.Choices hand = null;
        return hand;

    }

    public User getPlayer() {
        return player;

    }

    public int getVictories() {
        return victories;

    }


}
