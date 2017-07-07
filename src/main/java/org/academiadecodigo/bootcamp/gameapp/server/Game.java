package org.academiadecodigo.bootcamp.gameapp.server;

/**
 * Created by Cyrille on 06/07/17.
 */
public enum Game {
    RPS(2, 2);

    private int minUsers;
    private int maxUsers;

    Game(int minUsers, int maxUsers) {
        this.minUsers = minUsers;
        this.maxUsers = maxUsers;
    }
}
