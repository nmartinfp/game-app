package org.academiadecodigo.bootcamp.gameapp.utilities;

/**
 * Created by codecadet on 29/06/17.
 */
public enum CommProtocol {
    SERVER("@Server"),
    CLIENT("@Client");

    private String protocol;

    CommProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }
}
