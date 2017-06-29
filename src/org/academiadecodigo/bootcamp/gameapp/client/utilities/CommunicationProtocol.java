package org.academiadecodigo.bootcamp.gameapp.client.utilities;

/**
 * Created by codecadet on 29/06/17.
 */
public enum CommunicationProtocol {

    SERVER("@Server"),
    CLIENT("@Client");

    private String communicationProtocol;

    CommunicationProtocol(String communicationProtocol) {
        this.communicationProtocol = communicationProtocol;

    }

    public String getCommunicationProtocol() {
        return communicationProtocol;

    }
}
