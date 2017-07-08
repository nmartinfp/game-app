package org.academiadecodigo.bootcamp.gameapp.client;

/**
 * A/C: Bootcamp8
 * 2nd group project - GameName App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
public final class ClientRegistry {

    private ServerHandler handler;

    private static ClientRegistry instance = null;

    private ClientRegistry() {
    }

    public static ClientRegistry getInstance() {

        if (instance == null) {

            synchronized (ClientRegistry.class) {

                if (instance == null) {
                    instance = new ClientRegistry();
                }
            }
        }
        return instance;
    }

    public ServerHandler getHandler() {
        return handler;
    }

    public void setHandler(ServerHandler handler) {
        this.handler = handler;
    }
}
