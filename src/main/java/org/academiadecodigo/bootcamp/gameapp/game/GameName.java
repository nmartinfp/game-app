package org.academiadecodigo.bootcamp.gameapp.game;

/**
 * Created by Cyrille on 06/07/17.
 */
public enum GameName {
    RPS( 2, 2);

    private int minUsers;
    private int maxUsers;

    GameName(int minUsers, int maxUsers) {
        this.minUsers = minUsers;
        this.maxUsers = maxUsers;
    }

    public int getMinUsers() {
        return minUsers;
    }

    public int getMaxUsers() {
        return maxUsers;
    }
}
