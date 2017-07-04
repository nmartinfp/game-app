package org.academiadecodigo.bootcamp.gameapp.client;

/**
 * A/C: Bootcamp8
 * 2nd group project - Game App Platform
 * Authors: Cyrille Feijó, João Fernandes, Hélder Matos, Nelson Pereira, Tiago Santos
 */
public final class ClientRegistry {

    private Client client;
    private CltProtocolParser handler;

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

    public Client getClient() {
        return client;
    }

    public CltProtocolParser getHandler() {
        return handler;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setHandler(CltProtocolParser handler) {
        this.handler = handler;
    }
}
