package org.academiadecodigo.bootcamp.gameapp.server;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */

public interface Workable {

    void process(ClientHandler ClientHandler, String message);
}
