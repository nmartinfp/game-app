package org.academiadecodigo.bootcamp.gameapp.client;

/**
 * Created by codecadet on 01/07/17.
 */
public class ClientRegistry {

    private Client client;
    private ClientHandler handler;

    private static ClientRegistry instance = null;

    public static ClientRegistry getInstance() {
        if(instance == null){
            synchronized (ClientRegistry.class){
                if(instance == null){
                    instance = new ClientRegistry();
                }
            }
        }
        return instance;
    }

    private ClientRegistry() {
    }

    public Client getClient() {
        return client;
    }

    public ClientHandler getHandler() {
        return handler;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }
}
