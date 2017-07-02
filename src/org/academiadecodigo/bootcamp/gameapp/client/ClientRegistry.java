package org.academiadecodigo.bootcamp.gameapp.client;

/**
 * Created by codecadet on 01/07/17.
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
