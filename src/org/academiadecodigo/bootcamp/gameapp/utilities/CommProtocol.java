package org.academiadecodigo.bootcamp.gameapp.utilities;

/**
 * Created by codecadet on 29/06/17.
 */
public enum CommProtocol {
    SERVER_LOGIN("@SERVER_LOGIN "),
    SERVER_REGISTRY("@SERVER_REGISTER "),
    SERVER_LOBBY("@SERVER_LOBBY "),
    SERVER_GAME("@SERVER_GAME "),
    CLIENT("@CLIENT ");

    private String protocol;

    CommProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}